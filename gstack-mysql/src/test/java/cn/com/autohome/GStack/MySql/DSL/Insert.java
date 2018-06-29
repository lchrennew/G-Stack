package cn.com.autohome.GStack.MySql.DSL;


import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import com.thoughtworks.gauge.TableCell;
import com.thoughtworks.gauge.TableRow;
import com.thoughtworks.gauge.datastore.DataStore;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static cn.com.autohome.GStack.Basic.StoreUtils.*;
import static cn.com.autohome.GStack.MySql.Core.getConnectionByName;
import static com.thoughtworks.gauge.datastore.DataStoreFactory.getSpecDataStore;
import static java.lang.String.format;
import static java.lang.String.join;
import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static org.apache.calcite.linq4j.Linq4j.asEnumerable;

public class Insert {
    static final String INSERT_SQL = "INSERT INTO %s (%s) VALUES (%s);";

    @Step("SQL:INSERT <connection> <into> <values>")
    public void insert(String connectionStringName, String into, Table values) throws SQLException {
        PreparedStatement statement = buildStatement(connectionStringName, into, values);
        String keyName = getKeyName(values);
        if (keyName != null) {
            executeAllAndGetInsertedKey(statement, values, keyName);
        } else {
            executeAll(statement, values);
        }
        storeValues(values);
    }

    private static void storeValues(Table values) {
        DataStore store = getSpecDataStore();
        storeTable(values, store);
    }

    private static PreparedStatement buildStatement(String connectionStringName, String into, Table values) throws SQLException {
        String sql = format(INSERT_SQL, into, buildColumns(values), buildParams(values));
        Connection connection = getConnectionByName(connectionStringName);
        return connection.prepareStatement(sql, RETURN_GENERATED_KEYS);
    }

    private static void executeAll(PreparedStatement statement, Table values) throws SQLException {
        for (TableRow row : values.getTableRows()) {
            execute(statement, row);
        }
    }

    private static void executeAllAndGetInsertedKey(PreparedStatement statement, Table values, String keyName) throws SQLException {
        DataStore store = getSpecDataStore();
        for (TableRow row : values.getTableRows()) {
            execute(statement, row);
            storeInsertedKey(statement, keyName, store, row.getCell(format("%s%s", keyName, KEY_SUFFIX)));
        }
    }

    private static void storeInsertedKey(PreparedStatement statement, String keyName, DataStore store, String cell) throws SQLException {
        ResultSet resultSet = statement.getGeneratedKeys();
        resultSet.next();
        String value = resultSet.getString(1);
        String key = cell;
        store.put(format("%s.%s.id", keyName, key), value);
    }


    private static void execute(PreparedStatement statement, TableRow row) throws SQLException {
        setParams(statement, row);
        statement.executeUpdate();
    }

    private static void setParams(PreparedStatement statement, TableRow row) throws SQLException {
        List<TableCell> cells = row.getTableCells();
        cells.removeIf(cell -> cell.getColumnName().endsWith(KEY_SUFFIX));
        for (int i = 1; i <= cells.size(); i++) {
            statement.setString(i, cells.get(i - 1).getValue());
        }
    }


    private static String buildParams(Table values) {
        return join(", ",
                asEnumerable(values.getColumnNames()).where(notKey).select(x -> "?"));
    }


    private static String buildColumns(Table values) {
        return join(", ",
                asEnumerable(values.getColumnNames()).where(notKey));
    }

}
