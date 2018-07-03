package chun.li.GStack.Http.DSL.Arrange;

import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import org.apache.calcite.linq4j.ExtendedEnumerable;
import org.apache.calcite.linq4j.Grouping;

import static chun.li.GStack.Http.Core.buildRequest;
import static org.apache.calcite.linq4j.Linq4j.asEnumerable;

public class Params {
    @Step("PARAM <name> <value>")
    public void setParam(String name, String value) {
        buildRequest().param(name, value);
    }

    @Step("PARAM <table>")
    public void setParams(Table table){
        buildRequest().params(
                asEnumerable(table.getTableRows())
                        .groupBy(
                                row->row.getCell("name"),
                                r->r.getCell("value"))
                        .toMap(Grouping::getKey, ExtendedEnumerable::toList)
        );
    }

}
