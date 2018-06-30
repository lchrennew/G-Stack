package cn.com.autohome.GStack.Http.DSL.Arrange;

import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import io.restassured.specification.RequestSpecification;

import java.util.List;

import static cn.com.autohome.GStack.Http.DSL.Core.buildRequest;

public class Body {
    @Step("BODY:CONTENT <body>")
    public void contentBody(String body) {
        buildRequest().body(body);
    }

    @Step("BODY:FORM <table>")
    public void formsBody(Table table) {
        List<String> columns = table.getColumnNames();
        RequestSpecification request = buildRequest();
        table.getTableRows().forEach(
                row -> request.formParam(
                        row.getCell("name"),
                        row.getCell("value")
                ));
    }

    @Step("BODY:FORM <name> <value>")
    public void formBody(String name, String value) {
        buildRequest().formParam(name, value);
    }
}
