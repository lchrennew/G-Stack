package cn.com.autohome.GStack.Basic.DSL;

import cn.com.autohome.GStack.Basic.Core;
import com.thoughtworks.gauge.Step;

public class Print {

    @Step("PRINT <content>")
    public void printContent(String content) {
        content = Core.fillArgs(content);
        Core.print(content);
    }
}
