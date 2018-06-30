package cn.com.autohome.GStack.Http.DSL.Act;

import com.thoughtworks.gauge.Step;

import static cn.com.autohome.GStack.Http.DSL.Core.buildRequest;
import static cn.com.autohome.GStack.Http.DSL.Core.then;

public class Post {
    @Step("POST")
    public void post() {
        then(buildRequest().post().then());
    }

    @Step("POST <url>")
    public void postUrl(String url) {
        then(
                buildRequest().post(url).then()
        );
    }
}
