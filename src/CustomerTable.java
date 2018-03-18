import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Nicholas on 3/18/2018.
 */
public class CustomerTable {


    public static void createPersonTable(Connection conn){
        try {
            String query = "CREATE TABLE IF NOT EXISTS customer("
                    + "CUSTOMER_ID INT PRIMARY KEY,"
                    + "NAME VARCHAR(255),"
                    + "ADDRESS VARCHAR(255),"
                    + "PHONE VARCHAR(20),"
                    + "GENDER CHARACTER(1),"
                    + "INCOME INTEGER"
                    //TODO ADD SALESPERSON ID REFERENCE HERE
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



}
