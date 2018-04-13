import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by Nicholas on 3/18/2018.
 */
public class CustomerTable {


    public static void createCustomerTable(Connection conn){
        try {
            String query = "CREATE TABLE IF NOT EXISTS customer("
                    + "CUSTOMER_ID INT PRIMARY KEY,"
                    + "FIRST_NAME VARCHAR(50),"
                    + "LAST_NAME VARCHAR(50),"
                    + "ADDRESS VARCHAR(95),"
                    + "CITY VARCHAR(35),"
                    + "STATE VARCHAR(2),"
                    + "ZIP INT CHECK (ZIP BETWEEN 10000 and 99999),"
                    + "PHONE VARCHAR(20),"
                    + "GENDER CHARACTER(1),"
                    + "INCOME INTEGER,"
                    + "SALESPERSON_ID INT"
                  //  + "FOREIGN KEY (SALESPERSON_ID) REFERENCES SALESPERSON(SALESPERSON_ID"
                    + ")" ;

            /**
             * Create a query and execute
             */
            Statement stmt = conn.createStatement();
            stmt.execute(query);
        } catch (SQLException e) {
            System.out.println("Customer Table Creation FAILED!\n Please contact your database administrator");
            System.out.println(e.getMessage());
        }
    }

    public static void populateFromCSV(Connection conn,
                                       String csvFilePath) throws SQLException{

        String insert = "MERGE INTO Customer select * from CSVREAD('" + csvFilePath + "');";
        try{

            Statement stmt = conn.createStatement();
            stmt.execute(insert);
        } catch (SQLException e){
            e.printStackTrace();
            System.out.println("There was a problem populating the Customer table\n" +
                    "Please contact your database administrator");
        }



    }

    public static void deleteAll(Connection conn) {

        try{
            String query = "Delete from customer;";
            Statement stmt = conn.createStatement();
            System.out.println(query);
            stmt.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static ResultSet getCustomer(Connection conn,
                                        ArrayList<String> desiredCols,
                                        ArrayList<String> cols,
                                        ArrayList<String> whereClauses) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");

        for (int i = 0; i < desiredCols.size(); i++) {
            if (i != desiredCols.size() - 1) {
                sb.append(desiredCols.get(i) + ",");
            } else {
                sb.append(desiredCols.get(i));
            }
        }


        sb.append(" FROM customer ");

        if (!whereClauses.isEmpty()) {
            sb.append("WHERE ");

            for (int i = 0; i < whereClauses.size(); i++) {
                if (i != whereClauses.size() - 1) {
                    sb.append(cols.get(i) + "=" + "'" + whereClauses.get(i) + "'" + " AND ");
                } else {
                    sb.append(cols.get(i) + "=" + "'" + whereClauses.get(i) + "'" );
                }
            }
        }

        sb.append(";");

        //Print it out to verify it made it right
        System.out.println("Query: " + sb.toString());
        try {
            /**
             * Execute the query and return the result set
             */
            Statement stmt = conn.createStatement();
            return stmt.executeQuery(sb.toString());
        } catch (SQLException e) {
            System.out.println("Something went wrong with the query");;
        }
        return null;

    }


}
