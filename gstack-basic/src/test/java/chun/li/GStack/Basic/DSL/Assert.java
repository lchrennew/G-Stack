package chun.li.GStack.Basic.DSL;

import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;

import static chun.li.GStack.Basic.DynamicAsserts.asserts;

public class Assert {
    @Step("ASSERT <actual> <match> <expected>")
    public void assertX(String actual, String match, String expected) {
        asserts(actual.toString(), match, expected.toString());
    }



    @Step("ASSERT <table>")
    public void assertsX(Table table) {
        table.getTableRows().forEach(row ->
                asserts(
                        row.getCell("actual"),
                        row.getCell("match"),
                        row.getCell("expected")));
    }
}