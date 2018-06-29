package cn.com.autohome.GStack.UI.Web.DSL;

import com.thoughtworks.gauge.Step;

import static cn.com.autohome.Basic.Core.fillArgs;

public class Actions {
    @Step("CLICK <element>")
    public void click(String name) {
        name= fillArgs(name);
        Elements.getElement(name).click();
    }

    @Step("ENTER <element> <text>")
    public void enterText(String name, String text) {
        name = fillArgs(name);
        text = fillArgs(text);
        Elements.getElement(name).sendKeys(text);
    }

    @Step("SUBMIT <element>")
    public void submit(String name) {
        name = fillArgs(name);
        Elements.getElement(name).submit();
    }

}
