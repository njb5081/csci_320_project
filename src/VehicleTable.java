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
        String query = "Merge INTO Vehicle(VIN, brand, model, color, engine, transmission, mpg, dealerID, " +
                "dateSoldToDealer,manufacturer) SELECT * FROM CSVREAD('" + csvFilePath + "');";

        try {
            Statement stmt = conn.createStatement();
            stmt.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Insert a new Vehicle.
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
        String query = String.format("INSERT INTO Vehicle "
                        + "VALUES(\'%s\',\'%s\',\'%s\',\'%s\',\'%d\',\'%s\',\'%d\',\'%d\',\'%s\',\'%s\')",
                VIN, brand, model, color, engine, transmission, mpg, dealerID, dateSoldToDealer, manufacturer);

        try {
            Statement stmt = conn.createStatement();
            stmt.execute(query);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
