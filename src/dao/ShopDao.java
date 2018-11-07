package dao;

import Models.ShopEntity;
import utils.ServerAdresation;

import javax.persistence.Column;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@Deprecated
public class ShopDao extends AbstractDao {

    private final String DEFOLT_NAME_SHOP = "Мой Магазин";

    public boolean selectShop(ShopEntity entity) {
        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM public.shop" +
                    " WHERE id = ? LIMIT 1");
            statement.setLong(1, entity.getId());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                entity.setNameShop(resultSet.getString("name_shop"));
                entity.setServerIp(resultSet.getString("server_ip"));
            }

            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean updateShopTheme(ShopEntity shopEntity){
        return update(shopEntity,"theme_id");
    }

    public boolean updateShopName(ShopEntity shopEntity){
        return update(shopEntity,"name_shop");
    }

    private boolean update(ShopEntity shopEntity, String column){

        try(Connection connection = getConnection()){

            Method[] methods = shopEntity.getClass().getMethods();//.getMethod("myMethod" );
            Method getter = null;
            for (Method method:methods){

                Column col  = method.getAnnotation(Column.class);

                if(col!=null&& col.name().equals(column)){
                    getter = method;
                }

            }

            String sql = "UPDATE public.shop SET "+column+" = ? WHERE id= ?";

            PreparedStatement statement = connection.prepareStatement(sql);

            assert getter != null;

            statement.setObject(1,getter.invoke(shopEntity));
            statement.setLong(2,shopEntity.getId());

            statement.executeUpdate();
            return true;


        }catch (SQLException | IllegalAccessException | InvocationTargetException e){
            e.printStackTrace();
            return false;
        }

    }

    public boolean addShop(ShopEntity shopEntity){
        try(Connection connection = super.getConnection()){

            String ip = ServerAdresation.getIp(shopEntity.getId());
            String sql = "INSERT INTO public.shop (name_shop,server_ip) VALUES (?,?)";
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1 , DEFOLT_NAME_SHOP);
            statement.setString(2 , ip);
            statement.execute();

            ResultSet resultSet = statement.getGeneratedKeys();

            if(resultSet.next()){
                shopEntity.setId(resultSet.getInt("id"));
                shopEntity.setServerIp(ip);
            }

            return true;
        }catch (SQLException e) {
            return false;
        }
    }

    public List<ShopEntity> selectUserShop(long[] idShops){


        List <ShopEntity> shops = null;

        try(Connection connection = getConnection()) {

            StringBuilder bilder = new StringBuilder();

            bilder.append("SELECT * FROM public.shop WHERE id IN (");


            for (long idShop:idShops){

                if(idShop==idShops[idShops.length-1]){
                    bilder.append(idShop);
                    continue;
                }
                bilder.append(idShop).append(",");

            }

            bilder.append(")");

            PreparedStatement statement = connection.prepareStatement(new String(bilder));

//            statement.setString(1, new String(bilder));

            ResultSet resultSet = statement.executeQuery();

            shops = resultToList(resultSet);

        }catch (SQLException ex){

            ex.printStackTrace();

        }


        return shops;

    }

    private List<ShopEntity> resultToList(ResultSet resultSet) throws SQLException {

        List<ShopEntity> orders = new ArrayList<>();

        while (resultSet.next()){

            ShopEntity shopsEntity  = new ShopEntity();

            shopsEntity.setId(resultSet.getInt("id"));
            shopsEntity.setNameShop(resultSet.getString("name_shop"));
            shopsEntity.setServerIp(resultSet.getString("server_ip"));

            orders.add(shopsEntity);

        }

        return orders;

    }

}
