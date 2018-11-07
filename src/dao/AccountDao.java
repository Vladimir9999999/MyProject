package dao;

import Models.AccountEntity;
import org.json.JSONObject;

import java.sql.*;
@Deprecated
public class AccountDao extends AbstractDao {

    private String dbName = "public.account_shop";

    public static final  int BD_SHOP=0;
    public static final  int BD_USER=1;

    public AccountDao(int type){
        if(type==BD_SHOP||type==BD_USER) {
            setTable(type);
        }

    }
    public boolean saveAccount(AccountEntity account){
        try (Connection connection = getConnection()) {
            String sql = "INSERT INTO "+dbName+" " +
                    "(\"login\",\"shop_id\",\"employee_id\") VALUES " +
                    "(?,?,?)";

            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1,account.getLogin());
            statement.setLong(2,account.getShop_id());
            statement.setLong(3,account.getEmployeeId());

            statement.execute();
            ResultSet resultSet = statement.getGeneratedKeys();
            if(resultSet.next()){
                account.setId(resultSet.getInt(1));
                return true;
            }
            return false;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean auth(AccountEntity user) {

        try(Connection connection = getConnection()){
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM "+dbName +
                    " WHERE login = ?  LIMIT 1");
            statement.setString(1, user.getLogin());
            ResultSet resultSet  = statement.executeQuery();

            if (resultSet.next()) {
                user.setId(resultSet.getInt("id"));
                user.setEmployeeId(resultSet.getLong("employee_id"));
                return true;
            }
            return false;

        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
        //return false;
    }
    public boolean verificaion(String login){

        try(Connection connection = getConnection()){
            PreparedStatement statement = connection.prepareStatement("SELECT login FROM "+dbName +
                    " WHERE login = ? ");

            statement.setString(1, login);

            ResultSet resultSet  = statement.executeQuery();
            if(resultSet.next()) {
                return false;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return true;
    }

    public boolean udate(AccountEntity user){

        String sql = "UPDATE "+dbName+" SET " +
                "password = '"+user.getPassword() +"' WHERE login = '"+user.getLogin()+"'";
        Connection connection;

        try {

            connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.execute();

            ResultSet resultSet = statement.getGeneratedKeys();
            if(resultSet.next()){
                user.setId(resultSet.getLong(1));
            }

            return true;

        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void setTable(int table){

        switch (table){
            case BD_SHOP:
                dbName= "account_shop";
                break;
            case BD_USER:
                dbName = "account_users";
                break;
        }

    }

    public void deleteByShopId(long shopId){

        String sql = "DELETE  FROM "+dbName+" WHERE shop_id = '"+shopId+"'";
        Connection connection;

        try {

            connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.execute();

//            statement.executeQuery();

        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
