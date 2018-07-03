package chun.li.GStack.Http.DSL.Arrange;

import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import org.apache.calcite.linq4j.ExtendedEnumerable;
import org.apache.calcite.linq4j.Grouping;

import static chun.li.GStack.Http.Core.buildRequest;
import static org.apache.calcite.linq4j.Linq4j.asEnumerable;

public class Body {
    @Step("BODY:CONTENT <body>")
    public void contentBody(String body) {
        buildRequest().body(body);
    }

    @Step("BODY:FORM <table>")
    public void formsBody(Table table) {
        buildRequest().formParams(
                asEnumerable(table.getTableRows())
                        .groupBy(
                                row->row.getCell("name"),
                                r->r.getCell("value"))
                        .toMap(Grouping::getKey, ExtendedEnumerable::toList)
        );
    }

    @Step("BODY:FORM <name> <value>")
    public void formBody(String name, String value) {
        buildRequest().formParam(name, value);
    }
}
