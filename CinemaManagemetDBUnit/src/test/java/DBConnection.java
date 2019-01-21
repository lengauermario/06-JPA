import java.sql.*;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;

public class DBConnection {

    private static Connection connJDBC = null;
    private static IDatabaseConnection connection;
    final static String URL = "jdbc:derby://localhost:1527/db";
    final static String username = "app";
    final static String password = "app";
    final static String driverClassName = "org.apache.derby.jdbc.ClientDriver";

    @SuppressWarnings("unchecked")
    private static void connect() {
        try {
            Class driver = Class.forName(driverClassName);
            connJDBC =
                    DriverManager.getConnection(URL, username, password);
        } catch (Exception e) {
            System.err.println("Exception: keine Verbindung zur Datenbank!");
            System.err.println(e.getMessage());
        }
    }

    public static Connection getConnJDBC() {
        if (connJDBC == null) {
            connect();
        }
        return connJDBC;
    }

    public static IDatabaseConnection getConnection() {
        if (connection == null) {
            try {
                connection = new DatabaseConnection(getConnJDBC(), null, true);
            } catch (DatabaseUnitException ex) {
            }
        }
        return connection;
    }

    public void jdbcClose() {
        try {
            connJDBC.close();
        } catch (Exception e) {
        }
        connJDBC = null;
    }

    public void dbUnitConnectionClose() {
        try {
            connection.close();
        } catch (Exception e) {
        }
        connection = null;
    }

    protected void finalize() {
        dbUnitConnectionClose();
        jdbcClose();
    }
}
