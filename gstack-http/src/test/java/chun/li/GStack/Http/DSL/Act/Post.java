package chun.li.GStack.Http.DSL.Act;

import com.thoughtworks.gauge.Step;

import static chun.li.GStack.Http.Core.buildRequest;
import static chun.li.GStack.Http.Core.then;

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
