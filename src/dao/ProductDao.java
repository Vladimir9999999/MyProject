package dao;

import spring.entity.EntityOrders;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDao extends AbstractDao {

    public List<String> selectNamesByFragment(String fragment){
        fragment = fragment.replace(" ","&");
        fragment +=":*";
        String sql = "SELECT name FROM product where to_tsvector(name) @@ to_tsquery(?) limit 10";

        try(Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,fragment);
            ResultSet resultSet = statement.executeQuery();
            return resultToList(resultSet);
        }catch(SQLException e) {
            e.printStackTrace();

            return null;
        }

    }
    private List<String> resultToList(ResultSet resultSet) throws SQLException {

            List<String> names = new ArrayList<>();
            while (resultSet.next()){
                names.add(resultSet.getString("name"));
            }
            return names;
    }

}
