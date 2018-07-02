package chun.li.GStack.UI.Web.DSL;

import com.thoughtworks.gauge.Step;

public class Actions {
    @Step("CLICK <element>")
    public void click(String name) {
        Elements.getElement(name).click();
    }

    @Step("ENTER <element> <text>")
    public void enterText(String name, String text) {
        Elements.getElement(name).sendKeys(text);
    }

    @Step("SUBMIT <element>")
    public void submit(String name) {
        Elements.getElement(name).submit();
    }

}
