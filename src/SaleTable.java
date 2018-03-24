import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Nicholas on 3/19/2018.
 * Modified by Chris Baudouin
 */
public class SaleTable {

    public static void createSaleTable(Connection conn){
        try {
            String query = "CREATE TABLE IF NOT EXISTS sale("
                    + "SALE_ID INT PRIMARY KEY,"
                    + "SALEPERSON_ID INT FOREIGN KEY,"
                    + "DEALER_ID INT FOREIGN KEY,"
                    + "TOTAL INT,"
                    + "DATE VARCHAR(10),"
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

    public String getSale(int sale_id, int salesperson_id, int customer_id, int dealer_id){
        return "";
    }

    public int insertSale(int sale_id, int salesperson_id, int customer_id, int dealer_id){
        return 0;
    }

    public int updateSale(int sale_id, int salesperson_id, int customer_id, int dealer_id){
        return 0;
    }

}
