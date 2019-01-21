import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.log4j.BasicConfigurator;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.DatabaseSequenceFilter;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.database.search.TablesDependencyHelper;
import org.dbunit.dataset.FilteredDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatDtdDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;


//Run this to get the database as xml file
public class DBExport {

    public static void main(String[] args) throws Exception {
        BasicConfigurator.configure();
        // database connection
        Class driverClass = Class.forName("org.apache.derby.jdbc.ClientDriver");
        Connection jdbcConnection = DriverManager.getConnection(
                "jdbc:derby://localhost:1527/db", "app", "app");
        IDatabaseConnection connection = new DatabaseConnection(jdbcConnection);

        exportSchema(connection);
        exportAllTables(connection);
    }

    public static void exportSchema(IDatabaseConnection connection) throws Exception {
        FlatDtdDataSet.write(connection.createDataSet(), new FileOutputStream("target/db.dtd"));
    }

    public static void exportAllTables(IDatabaseConnection connection) throws Exception {
        IDataSet fullDataSet = new FilteredDataSet(new DatabaseSequenceFilter(connection), connection.createDataSet());
        FlatXmlDataSet.write(fullDataSet, new FileOutputStream("target/dbunit_full.xml"));
    }

    public static void exportPartitialTables(IDatabaseConnection connection) throws Exception {
        QueryDataSet partialDataSet = new QueryDataSet(connection);
        partialDataSet.addTable("FOO", "SELECT * FROM TABLE WHERE COL='VALUE'");
        partialDataSet.addTable("BAR");
        FlatXmlDataSet.write(partialDataSet, new FileOutputStream("target/partial.xml"));
    }

    public static void exportDependantTables(IDatabaseConnection connection) throws Exception {
        String[] depTableNames
                = TablesDependencyHelper.getAllDependentTables(connection, "X");
        IDataSet depDataset = connection.createDataSet(depTableNames);
        FlatXmlDataSet.write(depDataset, new FileOutputStream("target/dependents.xml"));

    }
}
