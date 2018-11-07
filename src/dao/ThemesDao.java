package dao;

import Models.EntityThemes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Deprecated
public class ThemesDao extends AbstractDao {

    public boolean selectById(EntityThemes themes){

        try (Connection connection = getConnection()) {

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM public.themes" +
                    " WHERE theme_id = ? LIMIT 1");

            statement.setLong(1, themes.getThemeId());
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

               themes.setParameters(resultSet.getString("parameters"));
               themes.setShopId(resultSet.getLong("shop_id"));
               themes.setThemeName("theme_name");

               return true;

            }

        } catch (SQLException e) {

        }
        return false;

    }



    public List<EntityThemes> selectAll(long shop_id) {

        List<EntityThemes> themes;

        try (Connection connection = getConnection()) {
            String sql = "SELECT *  FROM themes WHERE shop_id=? OR shop_id=0";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1,shop_id);

            ResultSet resultSet = statement.executeQuery();

            themes = resultToList(resultSet);
            return themes;

        } catch (SQLException e) {

            e.printStackTrace();

        }
        return null;
    }


    public boolean save(EntityThemes themes){
        try(Connection connection = getConnection()) {
            String sql = "INSERT INTO public.themes (theme_name, shop_id, parameters) " +
                    "VALUES (?,?,?::json)";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,themes.getThemeName());
            statement.setLong(2,themes.getShopId());
            statement.setString(3,themes.getParameters());

            statement.execute();
            ResultSet resultSet = statement.getGeneratedKeys();

            if(resultSet.next()){

                themes.setThemeId(resultSet.getInt(1));

            }else {

                return true;

            }

        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
        return false;
    }
    private List<EntityThemes> resultToList(ResultSet resultSet) throws SQLException {
        List<EntityThemes> themes = new ArrayList<>();

        while (resultSet.next()){

            EntityThemes theme  = new EntityThemes();

            theme.setThemeId(resultSet.getInt("theme_id"));
            theme.setParameters(resultSet.getString("parameters"));
            theme.setShopId(resultSet.getLong("shop_id"));
            theme.setThemeName(resultSet.getString("theme_name"));
            themes.add(theme);

        }

        return themes;
    }
}
