package cn.com.autohome.GStack.UI.Web.DSL;

import com.thoughtworks.gauge.Step;
import com.thoughtworks.gauge.Table;
import com.thoughtworks.gauge.datastore.DataStore;
import org.openqa.selenium.WebElement;

import static cn.com.autohome.GStack.Basic.Core.print;
import static cn.com.autohome.GStack.UI.Web.Core.getDriver;
import static com.thoughtworks.gauge.datastore.DataStoreFactory.getScenarioDataStore;

public class Elements {
    private final DataStore dataStore = getScenarioDataStore();

    @Step("DEF <name> <selector>")
    public void defineElement(String name, String selector) {
        name = getKey(name);
        dataStore.put(name, selector);
    }

    @Step("DEF <table>")
    public void defineElements(Table table) {
        table.getTableRows().forEach(
                row ->
                        dataStore.put(
                                getKey(row.getCell("name")),
                                row.getCell("css")
                        )
        );
    }

    @Step("PRINTEL <name>")
    public void printElement(String name) {
        print(String.format("name=%s", name));
        String key = getKey(name);
        print(String.format("key=%s", key));
        String selector = (String) dataStore.get(key);
        print(String.format("selector=%s", selector));
        WebElement element = getDriver().findElementByCssSelector(selector);
        print(String.format("element=%s", element));
    }

    private static String getKey(String name) {
        return String.format("webui:%s", name);
    }

    static WebElement getElement(String name) {
        name = getKey(name);
        String selector = (String) getScenarioDataStore().get(name);
        return getDriver().findElementByCssSelector(selector);
    }
}
