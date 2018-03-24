import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Nicholas on 3/19/2018.
 * Modified by Tyson Sisco
 */
public class DealerTable {

    public static void createDealerTable(Connection conn){
        try {
            String query = "CREATE TABLE IF NOT EXISTS dealer("
                    + "DEALER_ID INT PRIMARY KEY,"
                    + "NAME VARCHAR(100),"
                    + "ADDRESS VARCHAR(95),"
                    + "CITY VARCHAR(35),"
                    + "STATE VARCHAR(15),"
                    + "ZIP NUMERIC(12,0)"
                    + ");";
            Statement stmt = conn.createStatement();
            stmt.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertDealer(Connection conn, int id, String name, String address, String city, String state, int zip){

    }

    public static void importFromCSV(Connection conn){

    }

    public static String getDealer(){
        return "";
    }
}
