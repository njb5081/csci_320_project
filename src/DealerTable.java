import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
        String query = String.format("INSERT INTO dealer "
                        + "VALUES(%d,\'%s\',\'%s\',\'%s\',\'%s\',%d)",
                id, name, address, city, state, zip);
        try {
            Statement stmt = conn.createStatement();
            stmt.execute(query);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void importFromCSV(Connection conn, String filename) throws SQLException{
        String sql = "INSERT INTO dealer(SALESPERSON_ID, FIRST_NAME, LAST_NAME, PHONE, EMAIL, DEALER_ID)"
                + "SELECT * FROM CSVREAD('" + filename + "')";
        Statement stmt = conn.createStatement();
        stmt.execute(sql);
    }

    public static String getDealer(Connection conn, int id, String name, String address, String city, String state, int zip){
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM dealer WHERE ");
        ArrayList<String> whereClauses = new ArrayList<>();
        if (id != -1) whereClauses.add(String.format("DEALER_ID = %d", id));
        if (!name.equals("")) whereClauses.add("NAME = " + name);
        if (!address.equals("")) whereClauses.add("ADDRESS = " + address);
        if (!city.equals("")) whereClauses.add("CITY = " + city);
        if (!state.equals("")) whereClauses.add("STATE = " + state);
        if (zip != -1) whereClauses.add(String.format("ZIP = %d", zip));
        for(int i = 0; i < whereClauses.size(); i++){
            query.append(whereClauses.get(i));
            if (i != whereClauses.size() - 1) {
                query.append(" AND ");
            } else {
                query.append(";");
            }
        }

        try {
            Statement stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery(query.toString());
            StringBuilder result = new StringBuilder();
            while(resultSet.next()){
                result.append(String.format("Dealer: %d %s %s %s %s %d\n",
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getInt(6)));
            }
            return result.toString();

        } catch (SQLException e){
            e.printStackTrace();
        }
        return "";
    }
}
