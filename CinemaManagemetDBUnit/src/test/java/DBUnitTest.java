
import java.io.File;
import java.io.FileNotFoundException;

import org.dbunit.Assertion;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import java.io.FileInputStream;
import org.dbunit.DatabaseUnitException;

import java.sql.SQLException;
import org.dbunit.operation.DatabaseOperation;
import org.dbunit.dataset.IDataSet;
import org.dbunit.database.IDatabaseConnection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DBUnitTest {

    @Before
    public void setUp() throws SQLException, DatabaseUnitException, FileNotFoundException {
        IDataSet dataSet = new FlatXmlDataSetBuilder().build(new FileInputStream("src/test/resources/sampleData.xml"));
        try {
            DatabaseOperation.CLEAN_INSERT.execute(getConnection(), dataSet);
        } catch (DatabaseUnitException due) {
            throw due;
        } finally {}
    }

    @After
    public void tearDown() {
    }

    @Test
    public void test0_IsDBDataCorrect() throws Exception {
        IDataSet databaseDataSet = getConnection().createDataSet();
        ITable actualTable = databaseDataSet.getTable("Cinema");

        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(new File("src/test/resources/expectedData0.xml"));
        ITable expectedTable = expectedDataSet.getTable("Cinema");
        Assertion.assertEquals(expectedTable, actualTable);

    }

    @Test
    public void test1_InsertCinema() throws Exception {
        getConnection().getConnection().prepareStatement("INSERT INTO Cinema "
                + "(NAME, ADDRESS, FOUNDED) VALUES ('Megaplex','Sonnsteinstraße 40, 2080 Musterstadt', {ts '2014-05-02 00:00:00.0'})").execute();

        IDataSet databaseDataSet = getConnection().createDataSet();
        ITable actualTable = databaseDataSet.getTable("Cinema");
        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(new File("src/test/resources/expectedData1.xml"));
        ITable expectedTable = expectedDataSet.getTable("Cinema");
        Assertion.assertEquals(expectedTable, actualTable);
    }

    @Test
    public void test2_DeleteCinema() throws Exception {
        getConnection().getConnection().prepareStatement("DELETE FROM CINEMA "
                + "WHERE name = 'Megaplex'").execute();

        IDataSet databaseDataSet = getConnection().createDataSet();
        ITable actualTable = databaseDataSet.getTable("Cinema");

        IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(new File("src/test/resources/expectedData2.xml"));
        ITable expectedTable = expectedDataSet.getTable("Cinema");

        Assertion.assertEquals(expectedTable, actualTable);
    }

    public IDatabaseConnection getConnection(){
        return DBConnection.getConnection();
    }
}