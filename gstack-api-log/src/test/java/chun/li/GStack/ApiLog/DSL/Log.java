package chun.li.GStack.ApiLog.DSL;

import chun.li.GStack.ApiLog.LogFilter;
import com.thoughtworks.gauge.Step;

import static chun.li.GStack.Http.Core.buildRequest;

public class Log {
    @Step("HTTP:LOG")
    public void logHttp() {
        buildRequest().filter(new LogFilter());
    }
}
