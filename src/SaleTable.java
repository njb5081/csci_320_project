import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by Nicholas on 3/19/2018.
 * Modified by Chris Baudouin
 */
public class SaleTable {

    public static void createSaleTable(Connection conn){
        try {
            String query = "CREATE TABLE IF NOT EXISTS sale("
                    + "SALE_ID INT PRIMARY KEY NOT NULL,"
                    + "VIN VARCHAR(17) NOT NULL,"
                    + "SALESPERSON_ID INT NOT NULL,"
                    + "CUSTOMER_ID INT NOT NULL,"
                    + "DEALER_ID INT NOT NULL,"
                    + "TOTAL DECIMAL(10,2) NOT NULL,"
                    + "SALE_DATE DATE NOT NULL"
//                    + "FOREIGN KEY(DEALER_ID) REFERENCES Dealer,"
//                    + "FOREIGN KEY(SALESPERSON_ID) REFERENCES SalesPerson"
                    + ");";

            /**
             * Create a query and execute
             */
            Statement stmt = conn.createStatement();
            stmt.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void importFromCSV(Connection conn, String file) throws SQLException{
        String sql = "MERGE INTO sale SELECT * FROM CSVREAD('" + file + "');";
        try {
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("There was a problem populating the sale table\n" +
                    "Please contact your database administrator\n");
        }

    }

    public static String getSale(Connection conn, int sale_id, int salesperson_id, int customer_id, int dealer_id, String date){
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM sale WHERE ");
        ArrayList<String> whereClauses = new ArrayList<>();
        if (sale_id != -1) whereClauses.add(String.format("SALE_ID = %d", sale_id));
        if (salesperson_id != -1) whereClauses.add(String.format("SALESPERSON_ID = %d", salesperson_id));
        if (customer_id != -1) whereClauses.add(String.format("CUSTOMER_ID = %d", customer_id));
        if (dealer_id != -1) whereClauses.add(String.format("DEALER_ID = %d", dealer_id));
        if (!date.equals("")) whereClauses.add("DATE = " + date);
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
                result.append(String.format("Sale: %d %d %d %d %s\n",
                        resultSet.getInt(1),
                        resultSet.getInt(2),
                        resultSet.getInt(3),
                        resultSet.getInt(4),
                        resultSet.getString(5)));
            }
            return result.toString();

        } catch (SQLException e){
            e.printStackTrace();
        }
        return "";
    }

    public static void insertSale(Connection conn, int sale_id, int salesperson_id, int customer_id, int dealer_id, String date){
        String query = String.format("INSERT INTO sale "
                        + "VALUES(%d,%d,%d,%d,\'%s\')",
                        sale_id, salesperson_id, customer_id, dealer_id, date);
        try {
            Statement stmt = conn.createStatement();
            stmt.execute(query);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

}
