package utilities;

import java.sql.*;
import java.util.*;

public class JDBCUtils {

    /*
    .connectToDatabase(URL, Username, Password); -> our connect will be established
    .executeQuery(query); -> returns data as List of Maps
    .closeConnection(); -> closes database connection
     */

    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;

    public static void connectToDatabase(String URL, String username, String password) throws SQLException {
        connection = DriverManager.getConnection(URL, username, password);
        statement = connection.createStatement();
    }

    public static List<Map<String, Object>> executeQuery(String query) throws SQLException {
        resultSet = statement.executeQuery(query);
        List<Map<String, Object>> data=new ArrayList<>();
        ResultSetMetaData metaData=resultSet.getMetaData();

        while(resultSet.next()) {
            Map<String, Object> map=new HashMap<>();
            for(int columnOrder=1 ; columnOrder <= metaData.getColumnCount(); columnOrder++ ){
                map.put(metaData.getColumnName(columnOrder), resultSet.getString(metaData.getColumnName(columnOrder)));
            }
            data.add(map);
        }
        return data;
    }

    public static void closeDBConnection() throws SQLException{
        if(connection != null) connection.close();
        if(statement != null) statement.close();
        if(resultSet !=null) resultSet.close();
    }


}
