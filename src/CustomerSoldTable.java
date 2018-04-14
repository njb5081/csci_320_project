import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Nicholas on 3/19/2018.
 * Modified by Chris Baudouin
 */
public class CustomerSoldTable {
    public static void createCustomerSoldTable(Connection conn){
        try {
            String query = "CREATE TABLE IF NOT EXISTS customer_sold("
                    + "SALE_ID INT PRIMARY KEY,"
                    + "VIN VARCHAR(17),"
                    + "SALE_DATE DATE"
                    + ");" ;

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
        String sql = "MERGE INTO customer_sold(SALE_ID, VIN, SALE_DATE)"
                + " SELECT * FROM CSVREAD('" + file + "')";
        try {
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
        }catch (SQLException e){
            System.out.println("There was a problem populating the customer sold table\n" +
                    "Please contact your database administrator\n");
        }
    }

    public static String getVehicleSoldRecord(Connection conn, int sale_id){
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM vehicle_sold");
        if (sale_id != -1) sb.append(String.format("WHERE SALE_ID = %d", sale_id));
        try {
            Statement stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery(sb.toString());
            StringBuilder result = new StringBuilder();
            while(resultSet.next()){
                result.append(String.format("VehicleSold: %d %s\n",
                        resultSet.getInt(1),
                        resultSet.getString(2)));
            }
            return result.toString();

        } catch (SQLException e){
            e.printStackTrace();
        }
        return "";
    }

    public static void insertVehicleSoldRecord(Connection conn, int sale_id, String vin){
        String query = String.format("INSERT INTO salesperson VALUES(%d,\'%s\')",
                                                                    sale_id, vin);
        try {
            Statement stmt = conn.createStatement();
            stmt.execute(query);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }


}
