package cn.com.autohome.GStack.Http.DSL.Act;

import com.thoughtworks.gauge.Step;

import static cn.com.autohome.GStack.Http.DSL.Core.buildRequest;
import static cn.com.autohome.GStack.Http.DSL.Core.then;
import static cn.com.autohome.GStack.Basic.Core.fillArgs;

public class Post {
    @Step("POST")
    public void post() {
        then(buildRequest().post().then());
    }

    @Step("POST <url>")
    public void postUrl(String url) {
        url = fillArgs(url);
        then(
                buildRequest().post(url).then().log().all(true).and()
        );
    }
}
