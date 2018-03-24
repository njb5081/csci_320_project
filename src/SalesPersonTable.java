import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Nicholas on 3/19/2018.
 * Modified by Tyson Sisco
 */
public class SalesPersonTable {

    /**
     * Creates the Salesperson table, if it does not already exist.
     */
    public static void createSalesPersonTable(Connection conn){
        try {
            String query = "CREATE TABLE IF NOT EXISTS salesperson("
                    + "SALESPERSON_ID INT PRIMARY KEY,"
                    + "FIRST_NAME VARCHAR(25),"
                    + "LAST_NAME VARCHAR(25),"
                    + "PHONE VARCHAR(20),"
                    + "EMAIL VARCHAR(50),"
                    + "DEALER_ID INT,"
                    + "FOREIGN KEY (DEALER_ID) REFERENCES dealer(DEALER_ID)"
                    + ");";
            Statement stmt = conn.createStatement();
            stmt.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Inserts a salesperson with the given attributes into the database
     */
    public static void insertSalesPerson(Connection conn,
                                         int id,
                                         String first,
                                         String last,
                                         String phone,
                                         String email,
                                         int dealer) {
        String query = String.format("INSERT INTO salesperson "
                        + "VALUES(%d,\'%s\',\'%s\',\'%s\',\'%s\',%d)",
                id, first, last, phone, email, dealer);
        try {
            Statement stmt = conn.createStatement();
            stmt.execute(query);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Imports SalesPerson data to the database from a given CSV file
     * @param conn      The connection to the database
     * @throws SQLException
     */
    public static void importFromCSV(Connection conn, String filename)
            throws SQLException {
        String sql = "INSERT INTO salesperson(SALESPERSON_ID, FIRST_NAME, LAST_NAME, PHONE, EMAIL, DEALER_ID)"
                        + "SELECT * FROM CSVREAD('" + filename + "')";
        Statement stmt = conn.createStatement();
        stmt.execute(sql);

    }

    /**
     * Not entirely sure how to handle this yet
     * @param conn      The connection to the database
     * @param id        A specific salesperson ID, -1 otherwise
     * @param first     A first name to query for, empty string otherwise
     * @param last      A last name to query for, empty string otherwise
     * @param phone     A phone number to query for, empty string otherwise
     * @param email     An email to query for, empty string otherwise
     * @param dealer    A specific dealership ID, -1 otherwise
     * @return          A string representing the result of the query.
     */
    public static String getSalesPerson(Connection conn, int id, String first, String last, String phone, String email, int dealer){
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM salesperson WHERE ");
        ArrayList<String> whereClauses = new ArrayList<>();
        if (id != -1) whereClauses.add(String.format("SALESPERSON_ID = %d", id));
        if (!first.equals("")) whereClauses.add("FIRST_NAME = " + first);
        if (!last.equals("")) whereClauses.add("LAST_NAME = " + last);
        if (!phone.equals("")) whereClauses.add("PHONE = " + phone);
        if (!email.equals("")) whereClauses.add("EMAIL = " + email);
        if (dealer != -1) whereClauses.add(String.format("DEALER_ID = %d", dealer));
        for(int i = 0; i < whereClauses.size(); i++){
            query.append(whereClauses.get(i));
            if (i != whereClauses.size() - 1) {
                query.append(" AND ");
            } else {
                query.append(";");
            }
        }

        try {
            Statement stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery(query.toString());
            StringBuilder result = new StringBuilder();
            while(resultSet.next()){
                result.append(String.format("SalesPerson: %d %s %s %s %s %d\n",
                                                resultSet.getInt(1),
                                                resultSet.getString(2),
                                                resultSet.getString(3),
                                                resultSet.getString(4),
                                                resultSet.getString(5),
                                                resultSet.getInt(6)));
            }
            return result.toString();

        } catch (SQLException e){
            e.printStackTrace();
        }
        return "";
    }
}
