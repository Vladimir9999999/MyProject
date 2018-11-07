package dao;

import spring.entity.EntityAccountUsers;
import spring.entity.EntityOrders;
import spring.entity.EntityTaskList;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskListDao extends AbstractDao {



    public void deleteTasck(long orderId) {

        try (Connection connection = getConnection()) {
            String sql = "DELETE FROM task_list WHERE order_id = ?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, orderId);

            statement.execute();


        } catch (SQLException e) {

            e.printStackTrace();

        }
    }

    public List<EntityTaskList> findNewTask() {

        try (Connection connection = getConnection()) {

            String sql = "Select * From task_list WHERE time > ? AND  time < ?";


            PreparedStatement statement = connection.prepareStatement(sql);
            Timestamp thisTime = new Timestamp(new Date().getTime());

            statement.setTimestamp(1, thisTime);
            statement.setTimestamp(2, new Timestamp(thisTime.getTime() + 30*60000));

            ResultSet resultSet = statement.executeQuery();

            return resultToList(resultSet);

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return null;

    }
    private List<EntityTaskList> resultToList(ResultSet resultSet) throws SQLException {

        List<EntityTaskList> taskLists = new ArrayList<>();
        while (resultSet.next()){
            EntityTaskList entityTaskList = new EntityTaskList();

            entityTaskList.setEmployee(resultSet.getLong("employee"));
            entityTaskList.setLength(resultSet.getInt("length"));
            entityTaskList.setServices(resultSet.getString("services"));
            entityTaskList.setTime(resultSet.getTimestamp("time"));
            entityTaskList.setId(resultSet.getLong("id"));

            taskLists.add(entityTaskList);


        }

        return taskLists;

    }
}
