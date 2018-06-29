package cn.com.autohome.GStack.UI.Web.DSL;

import com.thoughtworks.gauge.Step;

import static cn.com.autohome.GStack.UI.Web.Core.start;
import static cn.com.autohome.GStack.UI.Web.Core.stop;

public class Scope {
    @Step("UI:WEB BEGIN")
    public void startWebUI() {
        start();
    }

    @Step("UI:WEB END")
    public void stopWebUI() {
        stop();
    }
}
