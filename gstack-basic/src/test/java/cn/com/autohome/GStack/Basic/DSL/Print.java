package cn.com.autohome.GStack.Basic.DSL;

import com.thoughtworks.gauge.Step;

import static cn.com.autohome.GStack.Basic.Core.print;
public class Print {

    @Step("PRINT <content>")
    public void printContent(String content) {
        print(content);
    }
}
