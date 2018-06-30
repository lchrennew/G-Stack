package cn.com.autohome.GStack.Http.DSL.Arrange;

import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import io.restassured.http.ContentType;

import java.util.HashMap;
import java.util.Map;

import static cn.com.autohome.GStack.Http.DSL.Core.buildRequest;

public class Headers {
    @Step("ACCEPT <mimeType>")
    public void accept(String mimeType) {
        buildRequest().header("Accept", mimeType);

    }


    @Step("COOKIE <table>")
    public void cookies(Table table) {
        Map<String, String> map = new HashMap<>();
        table.getTableRows().forEach(row -> map.put(
                row.getCell("name"),
                row.getCell("value")));
        buildRequest().cookies(map);
    }

    @Step("COOKIE <name> <value>")
    public void cookie(String name, String value) {
        buildRequest().cookie(name, value);
    }

    @Step("HEADER <name> <value>")
    public void header(String name, String value) {
        buildRequest().header(name, value);
    }

    @Step("CONTENTTYPE <contentType>")
    public void contentType(String contentType) {
        buildRequest().contentType(contentType);
    }

    @Step("CONTENTTYPE <contentType> <charSet>")
    public void contentTypeWithCharSet(String contentType, String charSet) {
        buildRequest().contentType(ContentType.fromContentType(contentType).withCharset(charSet));
    }
}
