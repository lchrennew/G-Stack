package chun.li.GStack.UI.Web.DSL;

import com.thoughtworks.gauge.Step;

import static chun.li.GStack.UI.Web.Core.getDriver;

public class Navigate {
    @Step("OPEN <url>")
    public void openUrl(String url) {
        getDriver().get(url);
    }

    @Step("BACK")
    public void goBackward() {
        getDriver().navigate().back();
    }

    @Step("FORWARD")
    public void goForward() {
        getDriver().navigate().forward();
    }

    @Step("REFRESH")
    public void refresh() {
        getDriver().navigate().refresh();
    }

    @Step("CLOSE")
    public void close() {
        getDriver().close();
    }

    @Step("QUIT")
    public void quit() {
        getDriver().quit();
    }

}
