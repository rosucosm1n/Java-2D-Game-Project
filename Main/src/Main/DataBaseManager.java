package Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DataBaseManager {
    static private final String DB = "./database.sqlite";

    public DataBaseManager() {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:" + DB);
            stmt = c.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS Time " +
                    "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " name TEXT, " +
                    " time INT NOT NULL)";
            stmt.execute(sql);
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    // inserts a new entry into the table
    public static void Insert(String name, long time) {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:" + DB);
            c.setAutoCommit(false);
            stmt = c.createStatement();
            String sql = "INSERT INTO Time (name, time) " +
                    "VALUES ('" + name + "'," + time + ");";
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }
}