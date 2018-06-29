package cn.com.autohome.GStack.Http.DSL.Arrange;

import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import io.restassured.specification.RequestSpecification;

import java.util.List;

import static cn.com.autohome.GStack.Basic.Core.fillArgs;
import static cn.com.autohome.GStack.Http.DSL.Core.buildRequest;

public class Uri {
    @Step("BASE <baseUri>")
    public void baseURI(String baseUri) {
        baseUri = fillArgs(baseUri);
        buildRequest().baseUri(baseUri);
    }

    @Step("PATH <basePath>")
    public void basePath(String basePath){
        basePath = fillArgs(basePath);
        buildRequest().basePath(basePath);
    }

    @Step("QUERY <table>")
    public void queries(Table table) {
        List<String> columns = table.getColumnNames();
        RequestSpecification request = buildRequest();
        table.getTableRows().forEach(
                row ->
                        request.queryParam(
                                fillArgs(row.getCell("name")),
                                fillArgs(row.getCell("value"))
                        )
        );
    }

    @Step("QUERY <name> <value>")
    public void query(String name, String value) {
        buildRequest()
                .queryParam(
                        fillArgs(name),
                        fillArgs(value));
    }
}
