package dao;

import Models.OrderEntity;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import spring.entity.EntityAccountUsers;
import spring.entity.EntityOrders;
import spring.entity.EntityTaskList;

import java.sql.*;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class OrderEntityDao extends AbstractDao {


    private String filter="AND plannedtime > '2017-11-05 23:36:32.000000' AND (status = 'new' OR status = 'paid') " ;



    public void setSessionFactory(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    public boolean delete(long idOrder) {
        try(Connection connection  = getConnection()){

            PreparedStatement statement = connection.prepareStatement("UPDATE public.orders SET status='delete'::statuses " +
                    "WHERE id= ?");
            statement.setLong(1,idOrder);
            statement.execute();
            return true;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }


    public List<EntityOrders> selectByShopIdAndUserId(long shopId, long userId, int offset, int limit){

        String sql = "SELECT o.id,o.length, o.shop_id, o.client, o.summ ,o.last_modification, o.favorite , " +
                "o.delete_mark, o.status , o.prepay , " +
                " t.id as tid , t.services, t.time, t.employee , t.length as tlength , " +
                " a.id as aid  , a.login" +
                " FROM  orders o LEFT JOIN task_list t on o.id= t.order_id " +
                "left join account_users a on o.client = a.id " +
                "WHERE o.client=a.id AND o.shop_id = ? AND o.client = ? order by t.time offset ? limit ?";

        try(Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1,shopId);
            statement.setLong(2,userId);
            statement.setInt(3,offset);
            statement.setInt(4,limit);
            ResultSet resultSet = statement.executeQuery();
            return resultToList(resultSet);
        }catch(SQLException e) {
            e.printStackTrace();

            return null;
        }

    }

    public List<EntityOrders> findNewByStatus(Timestamp time,int status, int offset,int limit){
        String sql = "SELECT o.id,o.length, o.shop_id, o.client, o.summ ,o.last_modification, o.favorite , " +
                "o.delete_mark, o.status , o.prepay , " +
                " t.id as tid , t.services, t.time, t.employee , t.length as tlength , " +
                " a.id as aid  , a.login" +
                " FROM  orders o LEFT JOIN task_list t on o.id= t.order_id " +
                "left join account_users a on o.client = a.id " +
                "WHERE t.time > ? AND o.status = ? order by t.time offset ? limit ?";

        return findNewAndOldByStatus(sql,time,status,offset,limit);

    }

    public List<EntityOrders> findOldByStatus(Timestamp time,int status, int offset,int limit){
        String sql = "SELECT o.id,o.length, o.shop_id, o.client, o.summ ,o.last_modification, o.favorite , " +
                "o.delete_mark, o.status , o.prepay , " +
                " t.id as tid , t.services, t.time, t.employee , t.length as tlength , " +
                " a.id as aid  , a.login" +
                " FROM  orders o LEFT JOIN task_list t on o.id= t.order_id " +
                "left join account_users a on o.client = a.id " +
                "WHERE t.time < ? AND o.status = ? order by t.time offset ? limit ?";

        return findNewAndOldByStatus(sql,time,status,offset,limit);
    }
    public List<EntityOrders> findByStatus(int status, int offset, int limit){
        String sql = "SELECT o.id,o.length, o.shop_id, o.client, o.summ ,o.last_modification, o.favorite , " +
                "o.delete_mark, o.status , o.prepay , " +
                " t.id as tid , t.services, t.time, t.employee , t.length as tlength , " +
                " a.id as aid  , a.login" +
                " FROM  orders o LEFT JOIN task_list t on o.id= t.order_id " +
                "left join account_users a on o.client = a.id " +
                "WHERE o.status = ? order by t.time offset ? limit ?";

        try(Connection connection = getConnection()) {

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1,status);
            statement.setInt(2,offset);
            statement.setInt(3,limit);

            ResultSet resultSet = statement.executeQuery();

            return resultToList(resultSet);

        }catch(SQLException e) {
            e.printStackTrace();

            return null;
        }
    }

    private List<EntityOrders> findNewAndOldByStatus(String sql, Timestamp time, int status, int offset, int limit){

        try(Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setTimestamp(1,time);
            statement.setInt(2,status);
            statement.setInt(3,offset);
            statement.setInt(4,limit);

            ResultSet resultSet = statement.executeQuery();

            return resultToList(resultSet);

        }catch(SQLException e) {
            e.printStackTrace();

            return null;
        }
    }

    public boolean update(OrderEntity order) {
        try (Connection connection = getConnection()) {

            StringBuilder builder = new StringBuilder();

            if(order.getServices() != null) {

                builder.append(",services ='" + order.getServices()+"'");

            }

            if(order.getEmployeeId()!=0) {

                builder.append(",employee ="+order.getEmployeeId());

            }
            if(order.getPlannedtime() != null) {
                builder.append(",plannedtime = '" + order.getPlannedtime()+"'");
            }
            if(order.getLendth() != null) {
                builder.append(",lendth ="+order.getLendth());
            }
            if(order.getSumm() != null) {
                builder.append(",summ = "+order.getSumm());
            }
            if(order.getPrepay() != null) {
                builder.append(",prepay = "+order.getPrepay());
            }
            if(order.getStatus() != null){
                builder.append(",status = '").append(order.getStatus()).append("'::statuses");
            }
            GregorianCalendar calendar = new GregorianCalendar();

            builder.append(",last_modification = '").append(String.format("%1$tF %1$tT",calendar)).append("'");

            String parameters  = new String(builder);

            String sql = "UPDATE public.orders SET " +
                    "id = "+order.getId() + parameters+"WHERE id ="+order.getId();

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.execute();

            ResultSet resultSet = statement.getGeneratedKeys();
            if(resultSet.next()){
                order.setId(resultSet.getLong(1));
            }
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    private List<EntityOrders> resultToList(ResultSet resultSet) throws SQLException {

        List<EntityOrders> orders = new ArrayList<>();
            while (resultSet.next()){
                EntityOrders entityOrders = new EntityOrders();
                entityOrders.setId(resultSet.getLong("id"));

                EntityAccountUsers accountUsers = new EntityAccountUsers();
                accountUsers.setId(resultSet.getLong("aid"));
                accountUsers.setLogin(resultSet.getString("login"));

                entityOrders.setAccountUsers(accountUsers);
                entityOrders.setDeleteMark(resultSet.getLong("delete_mark"));
                entityOrders.setFavorite(resultSet.getBoolean("favorite"));
                entityOrders.setLastModification(resultSet.getTimestamp("last_modification"));
                entityOrders.setLength(resultSet.getInt("length"));
                entityOrders.setPrepay(resultSet.getDouble("prepay"));
                entityOrders.setStatus(resultSet.getInt("status"));
                entityOrders.setSumm(resultSet.getDouble("summ"));

                EntityTaskList entityTaskList = new EntityTaskList();

                entityTaskList.setEmployee(resultSet.getLong("employee"));
                entityTaskList.setLength(resultSet.getInt("tlength"));
                entityTaskList.setOrders(entityOrders);
                entityTaskList.setServices(resultSet.getString("services"));
                entityTaskList.setTime(resultSet.getTimestamp("time"));
                entityTaskList.setId(resultSet.getLong("tid"));
                List<EntityTaskList> taskLists = new ArrayList<>();
                taskLists.add(entityTaskList);

                entityOrders.setTaskListsById(taskLists);
                orders.add(entityOrders);
            }
            return orders;

    }
}
