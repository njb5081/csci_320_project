import java.sql.Connection;
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

    public void importFromCSV(String file){

    }

    public String getVehicleSoldRecord(int sale_id){
        return "";
    }

    public int insertVehicleSoldRecord(String vin){
        return 0;
    }

    public int updateVehicleSoldRecord(int sale_id){
        return 0;
    }

}
