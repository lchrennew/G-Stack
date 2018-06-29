package cn.com.autohome.GStack.Http.DSL.Assert;

import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import com.thoughtworks.gauge.TableRow;
import com.thoughtworks.gauge.datastore.DataStore;

import java.util.List;

import static cn.com.autohome.GStack.Basic.Core.fillArgs;
import static cn.com.autohome.GStack.Basic.StoreUtils.storeTable;
import static cn.com.autohome.GStack.Http.DSL.Core.then;
import static com.thoughtworks.gauge.datastore.DataStoreFactory.getSpecDataStore;

public class Store {
    private final DataStore dataStore = getSpecDataStore();

    @Step("EXTRACT:JSONPATH <jsonPath> <var>")
    public void extractJsonPath(String jsonPath, String var) {
        jsonPath = fillArgs(jsonPath);
        var = fillArgs(var);
        dataStore.put(var, then().extract().jsonPath().getString(jsonPath));
    }

    // TODO: inner join
    @Step("EXTRACT:JSONPATH <jsonPath> JOIN <field> TO <table>")
    public void extractJsonPathJoinToTable(String jsonPath, String field, Table table) {
        jsonPath = fillArgs(jsonPath);
        List<String> list = then().extract().jsonPath().getList(jsonPath, String.class);
        List<String> tableHeaders = table.getColumnNames();
        tableHeaders.add(field);
        Table joined = new Table(tableHeaders);
        List<TableRow> rows = table.getTableRows();
        int size = rows.size();
        for (int i = 0; i < size; i++) {
            List<String> row = rows.get(i).getCellValues();
            row.add(list.get(i));
            joined.addRow(row);
        }
        //var = fillArgs(var);
        DataStore dataStore = getSpecDataStore();
        storeTable(joined, dataStore);
    }

    @Step("EXTRACT:CONTENT <var>")
    public void extractContent(String var) {
        var = fillArgs(var);
        dataStore.put(var, then().extract().body().asString());
    }

    @Step("EXTRACT:HEADER <header> <var>")
    public void extractHeader(String name, String var) {
        name = fillArgs(name);
        var = fillArgs(var);
        dataStore.put(var, then().extract().header(name));
    }

    @Step("EXTRACT:COOKIE <cookie> <var>")
    public void extractCookie(String name, String var) {
        name = fillArgs(name);
        var = fillArgs(var);
        dataStore.put(var, then().extract().cookie(name));
    }
}
