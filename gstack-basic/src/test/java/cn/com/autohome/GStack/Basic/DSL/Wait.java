package cn.com.autohome.GStack.Basic.DSL;

import com.thoughtworks.gauge.Step;

import static java.lang.Thread.sleep;

public class Wait {
    @Step("WAIT <seconds>")
    public void wait(Integer seconds) throws InterruptedException {
        sleep(seconds * 1000);
    }
}

