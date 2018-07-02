package chun.li.GStack.MySql.DSL;

import com.thoughtworks.gauge.Step;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

import static chun.li.GStack.MySql.Core.*;
import static org.junit.Assert.assertTrue;

public class Execute {

    @Step("SQL:STATEMENT <connection> <sql>")
    public void execute(String connectionStringName, String sql) throws SQLException {
        executeSql(sql, getConnectionByName(connectionStringName));
    }

    @Step("SQL:ASSERT <connection> <sql>")
    public void executeAssert(String connectionStringName, String sql) throws SQLException {
        boolean checkResult = executeSqlCheck(sql, getConnectionByName(connectionStringName));
        assertTrue(checkResult);
    }

    @Step("SQL:SCRIPT <connection> <sql>")
    public void executeScript(String connectionStringName, String sql) throws SQLException, UnsupportedEncodingException {
        executeSqlScript(sql, getConnectionByName(connectionStringName));
    }
}
