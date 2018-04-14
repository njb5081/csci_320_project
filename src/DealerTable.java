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
                    + "DEALER_NAME VARCHAR(100),"
                    + "ADDRESS VARCHAR(95),"
                    + "CITY VARCHAR(35),"
                    + "STATE VARCHAR(15),"
                    + "ZIP INT"
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
        String sql = "MERGE INTO dealer(DEALER_ID, DEALER_NAME, ADDRESS, CITY, STATE, ZIP)"
                + " SELECT * FROM CSVREAD('" + filename + "')";
        Statement stmt = conn.createStatement();
        stmt.execute(sql);
    }

    public static ResultSet getDealer(Connection conn,
                                   ArrayList<String> cols,
                                   ArrayList<String> whereClauses){
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM dealer");
        if (!whereClauses.isEmpty()){
            query.append(" WHERE");
            for(int i=0; i<whereClauses.size(); i++){
                if(i != whereClauses.size() -1){
                    query.append(" " + cols.get(i) + " = '" + whereClauses.get(i) + "' AND ");
                }
                else{
                    query.append(" " + cols.get(i) + " = '" + whereClauses.get(i) + "'");
                }
            }
        }
        query.append(";");

        try {
            Statement stmt = conn.createStatement();
            return stmt.executeQuery(query.toString());
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
