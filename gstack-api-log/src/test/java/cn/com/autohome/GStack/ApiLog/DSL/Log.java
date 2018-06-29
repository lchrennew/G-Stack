package cn.com.autohome.GStack.ApiLog.DSL;

import cn.com.autohome.GStack.ApiLog.LogFilter;
import com.thoughtworks.gauge.Step;

import static cn.com.autohome.GStack.Http.DSL.Core.buildRequest;

public class Log {
    @Step("HTTP:LOG")
    public void logHttp() {
        buildRequest().filter(new LogFilter());
    }
}
