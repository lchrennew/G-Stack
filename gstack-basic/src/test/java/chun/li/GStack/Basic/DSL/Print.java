package chun.li.GStack.Basic.DSL;

import com.thoughtworks.gauge.Step;

import static chun.li.GStack.Basic.Core.print;
public class Print {

    @Step("PRINT <content>")
    public void printContent(String content) {
        print(content);
    }
}
