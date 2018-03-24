/**
 * Created by Nicholas on 3/18/2018.
 */

import java.sql.*;







public class Main {

    //The connection to the database
    private Connection conn;

    /**
     * Create a database connection with the given params
     * @param location: path of where to place the database
     * @param user: user name for the owner of the database
     * @param password: password of the database owner
     */
    public void createConnection(String location,
                                 String user,
                                 String password){
        try {

            //This needs to be on the front of your location
            String url = "jdbc:h2:" + location;

            //This tells it to use the h2 driver
            Class.forName("org.h2.Driver");

            //creates the connection
            conn = DriverManager.getConnection(url,
                    user,
                    password);
        } catch (SQLException | ClassNotFoundException e) {
            //You should handle this better
            //TODO handle this better
            e.printStackTrace();
        }
    }

    /**
     * just returns the connection
     * @return: returns class level connection
     */
    public Connection getConnection(){
        return conn;
    }


    /**
     * When your database program exits
     * you should close the connection
     */
    public void closeConnection(){
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Main main = new Main();

        //location of the database
        String loc = "~/csci_320";
        String user = "user";
        String password = "1234";

        main.createConnection(loc, user, password);

        try{
            System.out.println("Test");
            // TODO This is where we create the tables
            CustomerTable.createPersonTable(main.getConnection());
            VehicleTable.createVehicleTable(main.getConnection());
            // TODO This is where we can seed the tables
            VehicleTable.importFromCsv(main.getConnection(), "data/Vehicle.csv");
            // TODO This is probably where we should scan
        }
        catch (Exception e){
            //TODO fix the catch all exception case
            e.printStackTrace();
        }
    }
}
