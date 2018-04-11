import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

/**
 * Created by Nicholas on 3/19/2018.
 * Modified by Jake on 3/24/2018.
 */
public class VehicleTable extends Main {
    /**
     * Create the vehicle table. Leave it empty for now.
     *
     * @param Connection conn
     */
    public static void createVehicleTable(Connection conn){
        try {
            String query = "CREATE TABLE IF NOT EXISTS vehicle("
                    + "VIN VARCHAR(17) PRIMARY KEY,"
                    + "BRAND VARCHAR(50),"
                    + "MODEL VARCHAR(50),"
                    + "COLOR VARCHAR(30),"
                    + "ENGINE INT(2),"
                    + "TRANSMISSION VARCHAR(30),"
                    + "MPG INT(3),"
                    + "DEALER_ID INT(11),"
                    + "DATE_SOLD_TO_DEALER DATE,"
                    + "MANUFACTURER VARCHAR(100)"
                    // + "FOREIGN KEY (DEALER_ID) REFERENCES dealer(DEALER_ID)"
                    + ");";
            Statement stmt = conn.createStatement();
            stmt.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Seed the vehicle table with mock data from a CSV file.
     *
     * @param Connection conn
     * @param String csvFilePath
     */
    public static void importFromCsv(Connection conn, String csvFilePath) {
        String query = "MERGE INTO vehicle SELECT * FROM CSVREAD('" + csvFilePath + "');";

        try {
            Statement stmt = conn.createStatement();
            stmt.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("There was a problem populating the vehicle table\n" +
                    "Please contact your database administrator");
        }
    }

    /**
     * Insert a new vehicle.
     *
     * @param Connection conn
     * @param String VIN
     * @param String brand
     * @param String model
     * @param String color
     * @param int engine
     * @param String transmission
     * @param int mpg
     * @param int dealerID
     * @param String dateSoldToDealer
     * @param String manufacturer
     */
    public static void insertVehicle(Connection conn,
                                     String VIN,
                                     String brand,
                                     String model,
                                     String color,
                                     int engine,
                                     String transmission,
                                     int mpg,
                                     int dealerID,
                                     String dateSoldToDealer,
                                     String manufacturer) {
        String query = String.format("INSERT INTO vehicle "
                        + "VALUES(\'%s\', \'%s\', \'%s\', \'%s\', %d, \'%s\', %d, %d, \'%s\', \'%s\')",
                VIN, brand, model, color, engine, transmission, mpg, dealerID, dateSoldToDealer, manufacturer);

        try {
            Statement stmt = conn.createStatement();
            stmt.execute(query);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
