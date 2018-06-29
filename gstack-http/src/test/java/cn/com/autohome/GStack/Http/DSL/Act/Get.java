package cn.com.autohome.GStack.Http.DSL.Act;

import com.thoughtworks.gauge.Step;

import static cn.com.autohome.Basic.Core.fillArgs;
import static cn.com.autohome.Http.DSL.Core.buildRequest;
import static cn.com.autohome.Http.DSL.Core.then;

public class Get {


    @Step("GET <url>")
    public void getUrl(String url) {
        url = fillArgs(url);
        then(
                buildRequest().get(url).then()
        );
    }
}
