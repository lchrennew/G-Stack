package chun.li.GStack.UI.Web.DSL;

import com.thoughtworks.gauge.Step;

import static chun.li.GStack.UI.Web.DSL.Elements.getElement;

public class Actions {
    @Step("CLICK <element>")
    public void click(String name) {
        getElement(name).click();
    }

    @Step("ENTER <element> <text>")
    public void enterText(String name, String text) {
        getElement(name).sendKeys(text);
    }

    @Step("SUBMIT <element>")
    public void submit(String name) {
        getElement(name).submit();
    }

}
