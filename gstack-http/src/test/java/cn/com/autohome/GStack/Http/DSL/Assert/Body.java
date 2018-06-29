package cn.com.autohome.GStack.Http.DSL.Assert;

import cn.com.autohome.Http.DSL.Core;
import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matchers;

import java.util.List;

import static cn.com.autohome.Basic.Core.fillArgs;
import static cn.com.autohome.Http.DSL.Core.then;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.hamcrest.Matchers.*;

public class Body {



    @Step("CHECK:BODY <body>")
    public void checkBody(String body) {
        body = fillArgs(body);
        Core.then().assertThat().content(equalTo(body));

    }

    @Step("CHECK:CONTENT <string>")
    public void CheckContent(String string) {
        string = fillArgs(string);
        Core.then().content(Matchers.containsString(string));
    }

    @Step("CHECK:JSONPATH <jsonPath> <value>")
    public void CheckJsonPath(String jsonPath, String value) {
        jsonPath = String.format("%s.toString()", fillArgs(jsonPath));
        value = fillArgs(value);
        Core.then().contentType(ContentType.JSON).body(jsonPath, equalTo(value));
    }

    @Step("CHECK:JSONPATH <table>")
    public void CheckJsonPath(Table table) {
        List<String> columns = table.getColumnNames();
        ValidatableResponse response = Core.then();
        table.getTableRows().forEach(row -> {
            String path = String.format("%s.toString()", row.getCell("path"));
            String value = fillArgs(row.getCell("value"));
            response.body(
                    fillArgs(path),
                    equalTo(fillArgs(value)));
        });
    }

    @Step("CHECK:JSONSCHEMA <schema>")
    public void CheckJsonSchema(String schema) {
        Core.then().body(matchesJsonSchema(schema));
    }

    @Step("FAIL:CONTENT <content>")
    public void CheckConentShouldFail(String content) {
        content = fillArgs(content);
        Core.then().content(not(containsString(content)));
    }

    @Step("FAIL:BODY <body>")
    public void CheckBodyShouldFail(String body) {
        body = fillArgs(body);
        Core.then().content(not(equalTo(body)));
    }

    @Step("FAIL:JSONPATH <jsonPath> <value>")
    public void CheckJsonPathShouldFail(String jsonPath, String value) {
        jsonPath = String.format("%s.toString()", fillArgs(jsonPath));
        value = fillArgs(value);
        Core.then().body(jsonPath, not(equalTo(value)));
    }
}
