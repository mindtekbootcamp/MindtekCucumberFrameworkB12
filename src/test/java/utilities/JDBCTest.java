package utilities;

import java.sql.*;

public class JDBCTest {

    public static void main(String[] args) throws SQLException {

        /*
        3 interfaces to connect to DB
        1. Connection
        2. Statement
        3. ResultSet
         */

        Connection connection= DriverManager.getConnection(
                // URL, USERNAME, PASSWORD
                "jdbc:postgresql://localhost:5432/HR",
                "postgres",
                "Admin123"
        );
        Statement statement=connection.createStatement();
        ResultSet resultSet=statement.executeQuery("select * from employees");

        resultSet.next();

        System.out.println(resultSet.getString("FIRST_NAME"));

        // .next(); goes to next row and returns TRUE if there is data in next row, otherwise FALSE

//        while(resultSet.next()){
//            System.out.println(resultSet.getString("FIRST_NAME"));
//            System.out.println(resultSet.getString("SALARY"));
//            System.out.println(resultSet.getString(1));
//        }

        // ResultSetMetaData -> return table information like column names, timestamp...
        ResultSetMetaData resultSetMetaData=resultSet.getMetaData();

        System.out.println(resultSetMetaData.getColumnCount());
        System.out.println(resultSetMetaData.getColumnName(1));

        // Write a code that will get all columns printed

        for(int columnOrder=1; columnOrder <= resultSetMetaData.getColumnCount(); columnOrder++ ) {
            System.out.println( resultSetMetaData.getColumnName(columnOrder) );
        }

        // Write a code will print all data from resultset

        //  While loop looping through each row
            while(resultSet.next()){
                // For loop looping through each column
                for(int columnOrder=1; columnOrder <= resultSetMetaData.getColumnCount(); columnOrder++ ){
                    String columnName = resultSetMetaData.getColumnName(columnOrder);
                    System.out.println(resultSet.getString(columnName));
                }
            }


    }

}
