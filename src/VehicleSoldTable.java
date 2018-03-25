import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Nicholas on 3/19/2018.
 * Modified by Chris Baudouin
 */
public class VehicleSoldTable {
    public static void createVehicleSoldTable(Connection conn){
        try {
            String query = "CREATE TABLE IF NOT EXISTS vehicle_sold("
                    + "SALE_ID INT PRIMARY KEY,"
                    + "VIN VARCHAR(17) ,"
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
        String sql = "INSERT INTO vehicle_sold(SALE_ID, VIN)"
                + "SELECT * FROM CSVREAD('" + file + "')";
        Statement stmt = conn.createStatement();
        stmt.execute(sql);
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
