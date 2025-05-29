package utilities;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class JDBCTest2 {

    public static void main(String[] args) throws SQLException {

        JDBCUtils.connectToDatabase(
                "jdbc:postgresql://localhost:5432/HR",
                "postgres",
                "Admin123"
        );

        System.out.println(JDBCUtils.executeQuery("select first_name, employee_id from employees " +
                "where salary <= 5000"));

        System.out.println("======================");

        // Get first_name, salary, department_name of employees who has salary more than 10000
        List<Map<String, Object>> data=JDBCUtils.executeQuery("select first_name, salary, department_name " +
                "from employees e join departments d " +
                "on e.department_id=d.department_id " +
                "where salary>10000");

        JDBCUtils.closeDBConnection();

        System.out.println(data);

        for(int i=0; i<data.size(); i++){
            System.out.println(data.get(i).get("first_name"));
        }

        // Write a code to get all data from list of maps that has name more than 6 characters.

        for(int i=0; i< data.size(); i++){
            if(data.get(i).get("first_name").toString().length()>6){
                System.out.println(data.get(i));
            }
        }

        // Print salary with first_name of employees who works in Accounting

        for(int i=0; i<data.size(); i++){
            if(data.get(i).get("department_name").toString().equals("Accounting")){
                System.out.println(data.get(i).get("first_name")+" | "+data.get(i).get("salary"));
            }
        }


    }
}
