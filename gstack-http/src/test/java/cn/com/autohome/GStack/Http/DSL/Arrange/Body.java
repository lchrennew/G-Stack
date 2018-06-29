package cn.com.autohome.GStack.Http.DSL.Arrange;

import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import io.restassured.specification.RequestSpecification;

import java.util.List;

import static cn.com.autohome.GStack.Basic.Core.fillArgs;
import static cn.com.autohome.GStack.Http.DSL.Core.buildRequest;

public class Body {
    @Step("BODY:CONTENT <body>")
    public void contentBody(String body) {
        body = fillArgs(body);
        buildRequest().body(body);
    }

    @Step("BODY:FORM <table>")
    public void formsBody(Table table) {
        List<String> columns = table.getColumnNames();
        RequestSpecification request = buildRequest();
        table.getTableRows().forEach(
                row -> request.formParam(
                        fillArgs(row.getCell("name")),
                        fillArgs(row.getCell("value"))
                ));
    }

    @Step("BODY:FORM <name> <value>")
    public void formBody(String name, String value) {
        buildRequest().formParam(name, value);
    }
}
