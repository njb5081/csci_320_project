/**
 * Created by Nicholas on 3/18/2018.
 */

import com.sun.org.apache.xpath.internal.SourceTree;

import java.sql.*;
import java.util.*;


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

        HashMap<Integer, String> vehicle_locator = new HashMap<>();
        vehicle_locator.put(1, "VIN");
        vehicle_locator.put(2, "BRAND");
        vehicle_locator.put(3, "MODEL");
        vehicle_locator.put(4, "COLOR");
        vehicle_locator.put(5, "ENGINE");
        vehicle_locator.put(6, "TRANSMISSION");
        vehicle_locator.put(7, "MANUFACTURER");
        vehicle_locator.put(8, "ZIPCODE");
        vehicle_locator.put(9, "NAME");
        vehicle_locator.put(10, "CITY");



        //location of the database
        String loc = "~/csci_320";
        String user = "user";
        String password = "1234";

        main.createConnection(loc, user, password);
        Connection conn = main.getConnection();
        init(conn);

        Scanner scanner = new Scanner(System.in);
        System.out.print("Welcome to the automobile database management system" +
                "\nPress 1 if you are a database admin" +
                "\nPress 2 if you are a dealership" +
                "\nPress 3 if you are a customer" +
                "\nEnter your selection here: ");
        int role = scanner.nextInt();

        if (role == 1){
            System.out.println("Welcome to the admin panel, please select on of the options below");
            System.out.print("Press 1 to query the database directly with your own SQL statements" +
                    "\nPress 2 to use the vehicle locator function" +
                    "\nPress 3 to locate a dealership" +
                    "\nEnter your selection here: ");
            int system = scanner.nextInt();
            scanner.nextLine();

            if (system == 1){
                adminSQL(conn, scanner);
            }
            else if (system == 2){
                vehicleLocatorFunction(conn, vehicle_locator, scanner);



            }
            else if (system == 3){

            }
            else{
                System.out.println("Sorry we do not recognise your choice. Please try again");
            }

        }
        else if (role == 2){
            System.out.println("Welcome to the dealer panel, please select one of the options below");
            System.out.print("Press 1 to locate a vehicle" +
                    "\nPress 2 to search for a sales records" +
                    "\nEnter your selection here: ");
            int system = scanner.nextInt();

        }
        else if (role == 3){
            System.out.println("Hello Customer! Please select one of the options below");
            System.out.print("Press 1 to search for dealerships" +
                    "\nPress 2 to search for a vehicle" +
                    "\nEnter your selection here");
            int system = scanner.nextInt();

        }
        else{
            System.out.println("Sorry, we do not recognize your choice, please select a role from above");
        }

    }

    public static void vehicleLocatorFunction(Connection conn, HashMap<Integer, String> searchParams, Scanner scanner) {

        System.out.println("\n\nWelcome to the Vehicle locator");
        for (Map.Entry<Integer, String> entry : searchParams.entrySet()) {
            System.out.println("Press " + Integer.toString(entry.getKey()) +
                    " to search using a " + entry.getValue());
        }
        ArrayList<String> tableCols = new ArrayList<>();
        ArrayList<String> whereClauses = new ArrayList<>();
        Boolean continueSelection = Boolean.TRUE;

        while (continueSelection){
            System.out.print("Enter your selection here or press ` (grave marker) to begin the search: ");
            String userChoice = scanner.nextLine();
            if (userChoice.equals("`")){
                continueSelection = false;
                System.out.println(tableCols);
                System.out.println(whereClauses);
                break;
                //TODO call vehicle search function in vehicle table here
            }
            else{
                userChoice = searchParams.get(Integer.parseInt(userChoice));
            }
            tableCols.add(userChoice);
            System.out.print("Enter your parameter for " + userChoice + " here: ");
            String whereParam = scanner.nextLine();
            whereClauses.add(whereParam);
        }
    }

    public static void init(Connection conn) {
        try {
            // TODO This is where we create the tables
            CustomerTable.createCustomerTable(conn);
            VehicleTable.createVehicleTable(conn);
            // TODO This is where we can seed the tables
            CustomerTable.populateFromCSV(conn, "data/customer.csv");
            VehicleTable.importFromCsv(conn, "data/Vehicle.csv");

            // TODO This is probably where we should scan
        } catch (Exception e) {
            //TODO fix the catch all exception case
            e.printStackTrace();
        }

        /**
        // Delete entire DB on program exit ~ to start fresh next time
        // Not sure if this is needed or not
         //Do think we need this yet
        String deleteQuery = "DROP ALL OBJECTS;";

        try {
            Statement stmt = conn.createStatement();
            stmt.execute(deleteQuery);
            Main main = new Main();
            main.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
         **/

    }
    public static ResultSet adminSQL(Connection conn, Scanner scanner){
        System.out.print("Enter your SQL query: ");
        String query = scanner.nextLine();
        try{
            Statement stmt = conn.createStatement();
            System.out.println(query);
            ResultSet result = stmt.executeQuery(query);
            System.out.println(result.toString());
            while(result.next()){
                System.out.printf("%s", result.getString(2));
            }

            return result;
        } catch (SQLException e) {
            System.out.println("Whoops! It looks like there was an error in your SQL, please check it and try again" +
                    "\n here's what we saw:" + e.getMessage());
        }
        return null;
    }
}
