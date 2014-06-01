package com.coldenergia.expensetracker.domain;

import org.hibernate.Session;
import org.hibernate.internal.SessionImpl;
import org.hibernate.jdbc.Work;

import javax.persistence.EntityManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;

import static org.junit.Assert.fail;

/**
 * User: coldenergia
 * Date: 5/2/14
 * Time: 2:39 PM
 */
public class JpaAssertions {

    public static void assertTableExists(EntityManager entityManager, final String name) {
        SessionImpl session = (SessionImpl) entityManager.unwrap(Session.class);
        final String[] tableTypes = { "TABLE" };
        final ResultCollector resultCollector = new ResultCollector();
        session.doWork(new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {
                ResultSet tables = connection.getMetaData().getTables(null, null, "%", tableTypes);
                while (tables.next()) {
                    if (tables.getString(3).equalsIgnoreCase(name)) {
                        resultCollector.found = true;
                    }
                }
            }
        });
        if (!resultCollector.found) {
            fail("Table '" + name + "' not found in schema");
        }
    }

    public static void assertTableHasColumn(EntityManager entityManager, final String tableName, final String columnName) {
        SessionImpl session = (SessionImpl) entityManager.unwrap(Session.class);
        final ResultCollector resultCollector = new ResultCollector();
        session.doWork(new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {
                ResultSet columns = connection.getMetaData().getColumns(null, null, tableName.toLowerCase(Locale.ENGLISH), null);
                while (columns.next()) {
                    if (columns.getString(4).equalsIgnoreCase(columnName)) {
                        resultCollector.found = true;
                    }
                }
                if (!resultCollector.found) {
                    // If for some reason it was unable to find the column, perhaps it was due to different naming of tables
                    // MariaDB and HSQLDB do differ in this aspect - the former uses lowercase, and the latter - uppercase
                    columns = connection.getMetaData().getColumns(null, null, tableName.toUpperCase(Locale.ENGLISH), null);
                    while (columns.next()) {
                        if (columns.getString(4).equalsIgnoreCase(columnName)) {
                            resultCollector.found = true;
                        }
                    }
                }
            }
        });
        if (!resultCollector.found) {
            fail("Column '" + columnName + "' not present in '" + tableName + "' table");
        }
    }

    private static class ResultCollector {

        public boolean found = false;

    }

}
