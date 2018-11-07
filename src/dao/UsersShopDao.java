package dao;

import Models.UserShopsEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Deprecated
public class UsersShopDao extends AbstractDao {

    public boolean insertShop(UserShopsEntity userShopsEntity){

        try(Connection connection = getConnection()){

            String sql = "INSERT INTO public.user_shops (user_id,shop,favorite) VALUES (?,?,?)";

            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.setLong(1,userShopsEntity.getUserId());
            statement.setLong(2,userShopsEntity.getShop());
            statement.setBoolean(3,userShopsEntity.getFavorite());

            statement.execute();
            return true;

        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }

    }
    public List<UserShopsEntity> selectUserShop(long idUser, int ofset, int limit) {
        List <UserShopsEntity> shopsEntities;

        try (Connection connection  = getConnection()) {

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM user_shops" +
                    " WHERE user_id = "+idUser+" LIMIT "+limit+" OFFSET "+ofset);
            ResultSet resultSet  =statement.executeQuery();

            shopsEntities = resultToList(resultSet);

            return shopsEntities;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    private List<UserShopsEntity> resultToList(ResultSet resultSet) throws SQLException {
        List<UserShopsEntity> orders = new ArrayList<>();
        while (resultSet.next()){

            UserShopsEntity shopsEntity  = new UserShopsEntity();

            shopsEntity.setUserId(resultSet.getLong("user_id"));
            shopsEntity.setFavorite(resultSet.getBoolean("favorite"));
            shopsEntity.setShop(resultSet.getLong("shop"));

            orders.add(shopsEntity);

        }
        return orders;
    }

}
