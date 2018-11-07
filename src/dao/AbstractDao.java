package dao;

import org.hibernate.SessionFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class AbstractDao {
    protected SessionFactory sessionFactory;

    private static String dbName = "ice";

    public static void setDbName(String dbName) {
        AbstractDao.dbName = dbName;
    }

    public SessionFactory getSessionFactory(){
        return sessionFactory;
    }

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected Connection getConnection() throws SQLException {

        String ipDatabase = System.getProperty("ps.database.ip");

        if(ipDatabase == null){
            ipDatabase = "localhost";
        }

        return DriverManager.getConnection("jdbc:postgresql://"+ipDatabase+":5432/"+dbName,"root","j7h12Q22");


    }

    protected void closeConnection(Connection connection){
        if(connection == null){

            return;

        }

        try {

            connection.close();

        }catch (SQLException e){

            e.printStackTrace();

        }
    }
}
