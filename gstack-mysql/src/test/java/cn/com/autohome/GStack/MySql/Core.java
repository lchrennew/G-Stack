package cn.com.autohome.GStack.MySql;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static java.lang.System.getenv;
import static java.sql.DriverManager.getConnection;

public class Core {
    public static void executeSql(String sql, Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        statement.execute(sql);
    }

    public static void executeSqlScript(String sql, Connection connection) throws UnsupportedEncodingException {
        InputStream inputStream = new ByteArrayInputStream(sql.getBytes("UTF-8"));
        Resource resource = new InputStreamResource(inputStream);
        EncodedResource encodedResource = new EncodedResource(resource, "UTF-8");
        ScriptUtils.executeSqlScript(connection, resource);
    }

    public static Connection getConnectionByName(String name) throws SQLException {
        String connectionString = getConnectionString(name);
        return getConnection(connectionString);
    }

    public static boolean executeSqlCheck(String sql, Connection connection) throws SQLException {

        Statement statement = connection.createStatement();
        statement.executeQuery(sql);
        ResultSet resultSet = statement.getResultSet();
        resultSet.first();
        return resultSet.getBoolean(1);

    }

    static String getConnectionString(String name) {
        return getenv(String.format("mysql_connection_string_%s", name));
    }
}
