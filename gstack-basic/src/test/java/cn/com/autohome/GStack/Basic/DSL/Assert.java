package cn.com.autohome.GStack.Basic.DSL;

import cn.com.autohome.Basic.Core;
import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;

import static org.junit.Assert.*;

public class Assert {
    @Step("ASSERT <actual> <match> <expected>")
    public void assertX(String actual, String match, String expected) {
        actual = Core.fillArgs(actual);
        expected = Core.fillArgs(expected);
        switch (match) {
            case "<>":
            case "!=":
                assertNotEquals(expected, actual);
                break;
            case "contains":
                assertTrue(String.format("\"%s\" doesn't contains \"%s\"", actual, expected), actual.contains(expected));
                break;
            case "jsonpath":
                break;
            case "jsonschema":
                break;
            case "=":
            default:
                assertEquals(expected, actual);
                break;
        }
    }

    @Step("ASSERT <table>")
    public void assertsX(Table table) {
        table.getTableRows().forEach(row ->
                assertX(
                        row.getCell("actual"),
                        row.getCell("match"),
                        row.getCell("expected")));
    }
}