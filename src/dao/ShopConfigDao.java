package dao;

import Models.ShopConfigEntity;

import java.sql.*;

@Deprecated
public class ShopConfigDao extends AbstractDao {

    public boolean addConfig(ShopConfigEntity config){

        try(Connection connection = super.getConnection()){
            String sql = "INSERT INTO public.shop_config (shop_id,super_admin) VALUES (?,?)";
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1,config.getShopId());
            statement.setLong(2,config.getSuperAdmin());
            statement.execute();
            return true;

        }catch (SQLException e) {
            return false;
        }
    }

    public int selectStyleShop(long idShop) {
        try (Connection connection = getConnection()) {

            PreparedStatement statement = connection.prepareStatement("SELECT style FROM public.shop_config" +
                    " WHERE shop_id = ? LIMIT 1");

            statement.setLong(1, idShop);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                return resultSet.getInt("style");

            }

        } catch (SQLException e) {

        }
        return 0;
    }
}
