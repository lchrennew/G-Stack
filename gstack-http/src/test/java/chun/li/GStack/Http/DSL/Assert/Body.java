package chun.li.GStack.Http.DSL.Assert;

import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matchers;

import java.util.List;

import static chun.li.GStack.Http.DSL.Core.then;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static java.lang.String.format;
import static org.hamcrest.Matchers.*;

public class Body {



    @Step("CHECK:BODY <body>")
    public void checkBody(String body) {
        then().assertThat().content(equalTo(body));

    }

    @Step("CHECK:CONTENT <string>")
    public void CheckContent(String string) {
        then().content(Matchers.containsString(string));
    }

    @Step("CHECK:JSONPATH <jsonPath> <value>")
    public void CheckJsonPath(String jsonPath, String value) {
        jsonPath = format("%s.toString()", jsonPath);
        then().contentType(ContentType.JSON).body(jsonPath, equalTo(value));
    }

    @Step("CHECK:JSONPATH <table>")
    public void CheckJsonPath(Table table) {
        List<String> columns = table.getColumnNames();
        ValidatableResponse response = then();
        table.getTableRows().forEach(row -> {
            String path = format("%s.toString()", row.getCell("path"));
            String value = row.getCell("value");
            response.body(path,
                    equalTo(value));
        });
    }

    @Step("CHECK:JSONSCHEMA <schema>")
    public void CheckJsonSchema(String schema) {
        then().body(matchesJsonSchema(schema));
    }

    @Step("FAIL:CONTENT <content>")
    public void CheckConentShouldFail(String content) {
        then().content(not(containsString(content)));
    }

    @Step("FAIL:BODY <body>")
    public void CheckBodyShouldFail(String body) {
        then().content(not(equalTo(body)));
    }

    @Step("FAIL:JSONPATH <jsonPath> <value>")
    public void CheckJsonPathShouldFail(String jsonPath, String value) {
        jsonPath = format("%s.toString()", jsonPath);
        then().body(jsonPath, not(equalTo(value)));
    }
}
