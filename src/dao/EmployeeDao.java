package dao;

import Models.EmploeeEntity;
import org.hibernate.SessionFactory;

import javax.annotation.Resource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@Deprecated
public class EmployeeDao extends AbstractDao {


    @Resource(name = "sessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    public boolean exit(EmploeeEntity user) {
        return false;
    }

    public boolean registartion(EmploeeEntity user) {
        try (Connection connection = getConnection()) {
            String sql = "INSERT INTO public.employee " +
                    "(name, shop_id,function,schedule_id) " +
                    "VALUES (?,?,?::json,?)";

            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1,user.getName());
            statement.setLong(2,user.getShopId());
            statement.setString(3,user.getFunction());
            statement.setInt(4,user.getSchedule());
            statement.execute();

            ResultSet resultSet = statement.getGeneratedKeys();

            if(resultSet.next()){

                user.setId(resultSet.getInt("id"));

            }

            return true;

        } catch (SQLException e) {

            e.printStackTrace();
            return false;

        }
    }

    public boolean selectEmployee(EmploeeEntity emploeeEntity) {
        try (Connection connection = getConnection()) {

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM public.employee" +
                    " WHERE id = ? LIMIT 1");

            statement.setLong(1, emploeeEntity.getId());
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                emploeeEntity.setShopId(resultSet.getLong("shop_id"));
                emploeeEntity.setName(resultSet.getString("name"));
                emploeeEntity.setFunction(resultSet.getString("function"));
                emploeeEntity.setPrivilege(resultSet.getShort("privilege"));

            }

            return true;
        } catch (SQLException e) {
            return false;
        }
    }
    public List<EmploeeEntity> selectEmployees(long shopId){
        try(Connection connection = getConnection()){

            String sql = "SELECT * FROM employee WHERE shop_id  = ?";

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setLong(1 ,shopId);

            ResultSet resultSet = statement.executeQuery();

            return resultToList(resultSet);


        }catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    private List<EmploeeEntity> resultToList(ResultSet resultSet) throws SQLException {

        List<EmploeeEntity> orders = new ArrayList<>();

        while (resultSet.next()){

            EmploeeEntity order  = new EmploeeEntity();

            order.setId(resultSet.getLong("id"));
            order.setShopId(resultSet.getLong("shop_id"));
            order.setName(resultSet.getString("name"));
            order.setFunction(resultSet.getString("function"));
            order.setSchedule(resultSet.getInt("schedule_id"));

            orders.add(order);
        }
        return orders;
    }

}
