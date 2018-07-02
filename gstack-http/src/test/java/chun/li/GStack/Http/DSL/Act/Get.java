package chun.li.GStack.Http.DSL.Act;

import com.thoughtworks.gauge.Step;

import static chun.li.GStack.Http.DSL.Core.buildRequest;
import static chun.li.GStack.Http.DSL.Core.then;

public class Get {


    @Step("GET <url>")
    public void getUrl(String url) {
        then(
                buildRequest().get(url).then()
        );
    }
}
