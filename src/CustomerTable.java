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
            query += "as SELECT * FROM CSVREAD('src/customer.csv')";
            Statement stmt = conn.createStatement();
            System.out.println(query);
            stmt.execute(query);
        } catch (SQLException e) {
            System.out.println("Customer Table Creation FAILED!\n Please contact your database administrator");
            System.out.println(e.getMessage());
        }
    }

    public static void populateFromCSV(Connection conn,
                                       String fileName) throws SQLException{

        ArrayList<String[]> lines = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;
            br.readLine();   // need to get rid of the headers

            while ((line = br.readLine()) != null){
                String[] split = line.split(",");
                lines.add(split);
            }
            br.close();
        } catch (IOException e){
            System.out.println("Warning! There was a problem reading in the CSV " +
                    "for populating the Customer table, ensure it is correct and try again");
        }

        String sql = "INSERT INTO Customer VALUES";

        //for (String[] row : lines){
        for (int i=1; i<2; i++){
            String[] row = lines.get(i);
            String append = String.format("(%d, \'%s\', \'%s\', \'%s\', \'%s\',\'%s\', %d, \'%s\',\'%s\',%d, %d),",
                    Integer.parseInt(row[0]), row[1], row[2], row[3], row[4], row[5], Integer.parseInt(row[6]),
                    row[7], row[8], Integer.parseInt(row[9]), Integer.parseInt(row[10]));
            sql += append;
        }

        int last_comma = sql.lastIndexOf(",");
        StringBuilder insert = new StringBuilder(sql);

        insert.setCharAt(last_comma, ';');


        System.out.println(insert.toString());

        try{
            sql = insert.toString();
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
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
                                        ArrayList<String> cols,
                                        ArrayList<String> whereClauses) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");

        if (cols.isEmpty()){
            sb.append("* ");
        }
        else{
            for(int i=0; i < cols.size(); i++){
                if(i != cols.size() - 1){
                    sb.append(cols.get(i) + ", " );
                }
                else {
                    sb.append(cols.get(i) + " ");
                }
            }
        }
        sb.append("FROM customer");

        if (!whereClauses.isEmpty()){
            sb.append("WHERE");
            for(int i=0; i<whereClauses.size(); i++){
                if(i != whereClauses.size() -1){
                    sb.append(whereClauses.get(i) + " AND ");
                }
                else{
                    sb.append(whereClauses.get(i));
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
