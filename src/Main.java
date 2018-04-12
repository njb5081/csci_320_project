/**
 * Created by Nicholas on 3/18/2018.
 */

import com.sun.org.apache.xpath.internal.SourceTree;
import com.sun.xml.internal.org.jvnet.fastinfoset.sax.FastInfosetReader;
import sun.security.krb5.SCDynamicStoreConfig;

import java.sql.*;
import java.util.*;


public class Main {

    //The connection to the database
    private Connection conn;

    private static ArrayList<String> tableCols = new ArrayList<>();
    private static ArrayList<String> whereClauses = new ArrayList<>();

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

        HashMap<Integer, String> vehicleLocator = new HashMap<>();
        vehicleLocator.put(1, "VIN");
        vehicleLocator.put(2, "BRAND");
        vehicleLocator.put(3, "MODEL");
        vehicleLocator.put(4, "COLOR");
        vehicleLocator.put(5, "ENGINE");
        vehicleLocator.put(6, "TRANSMISSION");
        vehicleLocator.put(7, "MANUFACTURER");
        vehicleLocator.put(8, "ZIPCODE");
        vehicleLocator.put(9, "NAME");
        vehicleLocator.put(10, "CITY");

        HashMap<Integer, String> dealerAttributes = new HashMap<>();
        dealerAttributes.put(1, "DEALER_ID");
        dealerAttributes.put(2, "NAME");
        dealerAttributes.put(3, "ADDRESS");
        dealerAttributes.put(4, "CITY");
        dealerAttributes.put(5, "STATE");
        dealerAttributes.put(6, "ZIP");

        HashMap<Integer, String> salesReportAttributes = new HashMap<>();
        salesReportAttributes.put(1, "BRAND");
        salesReportAttributes.put(2, "MODEL");
        salesReportAttributes.put(3, "COLOR");
        salesReportAttributes.put(4, "ENGINE");
        salesReportAttributes.put(5, "TRANSMISSION");
        salesReportAttributes.put(6, "MANUFACTURER");
        salesReportAttributes.put(7, "NAME");
        salesReportAttributes.put(8, "ADDRESS");
        salesReportAttributes.put(9, "CITY");
        salesReportAttributes.put(10, "STATE");
        salesReportAttributes.put(11, "ZIP");
        salesReportAttributes.put(12, "STARTDATE"); //TODO RENAME TO ACTUAL COLUMN NAME
        salesReportAttributes.put(13, "ENDDATE"); //TODO RENAME TO ACTUAL COLUMN NAME


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
                "\n Press 4 to add entries to the database" +
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
                vehicleLocatorFunction(conn, vehicleLocator, scanner);
            }
            else if (system == 3){
                dealerLocator(conn, dealerAttributes, scanner);
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
            scanner.nextLine();
            if (system == 1){
                vehicleLocatorFunction(conn, vehicleLocator, scanner);
            }
            else if (system == 2){
                salesReportLocator(conn, salesReportAttributes, scanner);
            }
            else {
                System.out.println("Sorry we do not recognize your choice, please try again");
            }

        }
        else if (role == 3){
            System.out.println("Hello Customer! Please select one of the options below");
            System.out.print("Press 1 to search for dealerships" +
                    "\nPress 2 to search for a vehicle" +
                    "\nEnter your selection here: ");
            int system = scanner.nextInt();
            scanner.nextLine();
            if (system == 1){
                dealerLocator(conn, dealerAttributes, scanner);
            }
            else if (system == 2){
                vehicleLocatorFunction(conn, vehicleLocator, scanner);
            }
            else {
                System.out.println("Sorry we don't recognize that selection, please try again");
            }
        }

        else if (role == 4){
            System.out.println("Press 1 to add a new customer" +
                    "\nPress 2 to add a new vehicle" +
                    "\nPress 3 to add a new dealership" +
                    "\nPress 4 to add a new salesperson" +
                    "\nPress 5 to add a new sale" +
                    "\nPress 6 to add a new sales record");
            int selection = scanner.nextInt();
            scanner.nextLine();
            if (selection == 1){
                System.out.println("Enter first name");
                String fname = scanner.nextLine();
                System.out.println("Enter last name");
                String lname = scanner.nextLine();
                System.out.println("Enter street address");
                String street = scanner.nextLine();
                System.out.println("Enter city");
                String city = scanner.nextLine();
                System.out.println("Enter State (ex: NY, PA)");
                String state = scanner.nextLine();
                System.out.println("Enter zip code");
                Integer zip = scanner.nextInt();
                scanner.nextLine();
                System.out.println("Enter phone");
                String phone = scanner.nextLine();
                System.out.println("Enter gender (M/F)");
                String gender = scanner.nextLine();
                System.out.println("Enter income (Round to nearest dollar with no commas");
                Integer income = scanner.nextInt();
                scanner.nextLine();
                System.out.println("Enter Id of assigned salesperson");
                Integer salespersonId = scanner.nextInt();
                scanner.nextLine();
                //Todo need to call create customer here


            }
            else if (selection == 2){
                System.out.println("Enter VIN");
                String vin = scanner.nextLine();
                System.out.println("Enter brand");
                String brand = scanner.nextLine();
                System.out.println("Enter model");
                String model = scanner.nextLine();
                System.out.println("Enter color");
                String color = scanner.nextLine();
                System.out.println("Enter engine");
                String engine = scanner.nextLine();
                System.out.println("Enter transmission");
                String trans = scanner.nextLine();
                System.out.println("Enter mpg");
                Integer mpg = scanner.nextInt();
                scanner.nextLine();
                System.out.println("Enter current dealership ID where vehicle is located or leave blank if already bought");
                Integer dealerID = scanner.nextInt();
                scanner.nextLine();
                //Todo check date format
                System.out.println("Enter date sold to dealer of assigned salesperson");
                Integer date = scanner.nextInt();
                scanner.nextLine();
                System.out.println("Enter manufacturer");
                String manufact = scanner.nextLine();

            }
            else if (selection == 3){
                System.out.println("Enter dealer name");
                String name = scanner.nextLine();
                System.out.println("Enter street address");
                String street = scanner.nextLine();
                System.out.println("Enter city");
                String city = scanner.nextLine();
                System.out.println("Enter State (ex: NY, PA)");
                String state = scanner.nextLine();
                System.out.println("Enter zip code");
                Integer zip = scanner.nextInt();
                scanner.nextLine();

            }
            else if (selection == 4){
                System.out.println("Enter first name");
                String fname = scanner.nextLine();
                System.out.println("Enter last name");
                String lname = scanner.nextLine();
                System.out.println("Enter phone");
                String phone = scanner.nextLine();
                System.out.println("Enter email");
                String email = scanner.nextLine();
                System.out.println("Enter Id of assigned dealer");
                Integer dealerId = scanner.nextInt();
                scanner.nextLine();

            }
            else if (selection == 5){
                System.out.println("Enter Id of assigned salesperson");
                Integer salespersonId = scanner.nextInt();
                scanner.nextLine();
                System.out.println("Enter Id of customer the vehicle was sold to");
                Integer customerId = scanner.nextInt();
                scanner.nextLine();
                System.out.println("Enter Id of dealer");
                Integer dealerId = scanner.nextInt();
                scanner.nextLine();
                System.out.println("Enter total sale price");
                Integer total = scanner.nextInt();
                scanner.nextLine();
                System.out.println("Enter date of sale");
                Integer date = scanner.nextInt();
                scanner.nextLine();



            }
            else if (selection == 6){
                System.out.println("Enter Id of sale");
                Integer saleId = scanner.nextInt();
                scanner.nextLine();
                System.out.println("Enter vin");
                String vin = scanner.nextLine();

            }
        }
        else{
            System.out.println("Sorry, we do not recognize your choice, please select a role from above");
        }
    }


    public static void vehicleLocatorFunction(Connection conn, HashMap<Integer, String> searchParams, Scanner scanner) {

        System.out.println("\n\nWelcome to the Vehicle locator");
        showSelection(conn, searchParams, scanner);

        ResultSet results = VehicleTable.getVehicle(conn, tableCols, whereClauses);

        if (results != null) {
            try {
                ResultSetMetaData rsmd = results.getMetaData();
                int columnCount = rsmd.getColumnCount();
                while (results.next()) {
                    for (int i = 1; i <= columnCount; i++) {
                        if (i > 1) System.out.print(",  ");
                        String columnValue = results.getString(i);
                        System.out.print(columnValue + " " + rsmd.getColumnName(i));
                    }
                    System.out.println("");
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void dealerLocator(Connection conn, HashMap<Integer, String> dealerAttributes, Scanner scanner){
        System.out.println("Welcome to the Dealer Locator Service");
        showSelection(conn, dealerAttributes, scanner);

        ResultSet results = DealerTable.getDealer(conn, tableCols, whereClauses);

        if (results != null) {
            try {
                ResultSetMetaData rsmd = results.getMetaData();
                int columnCount = rsmd.getColumnCount();
                while (results.next()) {
                    for (int i = 1; i <= columnCount; i++) {
                        if (i > 1) System.out.print(",  ");
                        String columnValue = results.getString(i);
                        System.out.print(columnValue + " " + rsmd.getColumnName(i));
                    }
                    System.out.println("");
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void salesReportLocator(Connection conn, HashMap<Integer, String> reportAttibutes, Scanner scanner){
        System.out.println("Welcome to the sales history search");
        showSelection(conn, reportAttibutes, scanner);
    }

    public static void init(Connection conn) {
        try {
            // TODO This is where we create the tables
            CustomerTable.createCustomerTable(conn);
            VehicleTable.createVehicleTable(conn);
            VehicleSoldTable.createVehicleSoldTable(conn);
            SaleTable.createSaleTable(conn);
            // TODO This is where we can seed the tables
            CustomerTable.populateFromCSV(conn, "data/customer.csv");
            VehicleTable.importFromCsv(conn, "data/vehicle.csv");

            // TODO This is probably where we should scan
        } catch (Exception e) {
            //TODO fix the catch all exception case
            e.printStackTrace();
        }
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

    public static void showSelection(Connection conn, HashMap<Integer, String> paramDict, Scanner scanner){
        for (Map.Entry<Integer, String> entry : paramDict.entrySet()) {
            System.out.println("Press " + Integer.toString(entry.getKey()) +
                    " to search using a " + entry.getValue());
        }
        tableCols = new ArrayList<>();
        whereClauses = new ArrayList<>();
        Boolean continueSelection = true;

        while (continueSelection) {
            System.out.print("Enter your selection here or press ` (grave marker) to begin the search: ");
            String userChoice = scanner.nextLine();
            if (userChoice.equals("`")) {
                continueSelection = false;
                System.out.println(tableCols);
                System.out.println(whereClauses);
                break;
            } else {
                userChoice = paramDict.get(Integer.parseInt(userChoice));
            }
            tableCols.add(userChoice);
            System.out.print("Enter your parameter for " + userChoice + " here: ");
            String whereParam = scanner.nextLine();
            whereClauses.add(whereParam);
        }
    }
}
