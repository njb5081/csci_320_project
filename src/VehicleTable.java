import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Nicholas on 3/19/2018.
 * Modified by Jake on 3/24/2018.
 */
public class VehicleTable extends Main {
    /**
     * Create the Vehicle table. Leave it empty for now.
     *
     * @param Connection conn
     */
    public static void createVehicleTable(Connection conn){
        try {
            String query = "CREATE TABLE IF NOT EXISTS Vehicle("
                    + "VIN VARCHAR(17) PRIMARY KEY,"
                    + "brand VARCHAR(50),"
                    + "model VARCHAR(50),"
                    + "color VARCHAR(30),"
                    + "engine INT(2),"
                    + "transmission VARCHAR(30),"
                    + "mpg INT(3),"
                    + "dealerID INT(11),"
                    + "dateSoldToDealer DATE,"
                    + "manufacturer VARCHAR(100)"
                    //TODO ADD FOREIGN KEY TO DEALERSHIP TABLE (createDealerTable method must be written first)
                    + ");";
            Statement stmt = conn.createStatement();
            stmt.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Seed the Vehicle table with mock data from a CSV file.
     *
     * @param Connection conn
     * @param String csvFilePath
     */
    public static void importFromCsv(Connection conn, String csvFilePath) {
        // TODO write this method
    }
}
