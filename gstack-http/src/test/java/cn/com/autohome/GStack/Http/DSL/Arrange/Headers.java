package cn.com.autohome.GStack.Http.DSL.Arrange;

import cn.com.autohome.Http.DSL.Core;
import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;

import java.util.HashMap;
import java.util.Map;

import static cn.com.autohome.Basic.Core.fillArgs;
import static cn.com.autohome.Http.DSL.Core.buildRequest;

public class Headers {
    @Step("ACCEPT <mimeType>")
    public void accept(String mimeType) {
        mimeType = fillArgs(mimeType);
        Core.buildRequest().header("Accept", mimeType);

    }


    @Step("COOKIE <table>")
    public void cookies(Table table) {
        Map<String, String> map = new HashMap<>();
        table.getTableRows().forEach(row -> {
            String name = fillArgs(row.getCell("name"));
            String value = fillArgs(row.getCell("value"));
            map.put(name, value);
        });
        Core.buildRequest().cookies(map);
    }

    @Step("COOKIE <name> <value>")
    public void cookie(String name, String value) {
        name = fillArgs(name);
        value = fillArgs(value);
        Core.buildRequest().cookie(name, value);
    }

    @Step("HEADER <name> <value>")
    public void header(String name, String value) {
        name = fillArgs(name);
        value = fillArgs(value);
        Core.buildRequest().header(name, value);
    }

    @Step("CONTENTTYPE <contentType>")
    public void contentType(String contentType) {
        Core.buildRequest().contentType(contentType);
    }

}
