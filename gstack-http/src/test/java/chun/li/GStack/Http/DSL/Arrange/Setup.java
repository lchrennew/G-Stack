package chun.li.GStack.Http.DSL.Arrange;

import com.thoughtworks.gauge.Step;

import static chun.li.GStack.Http.Core.*;

public class Setup {

    @Step("HTTP:SETUP <jsonConfig>")
    public void httpSetup(String jsonConfig) {
        loadJsonConfig(jsonConfig);
    }

    @Step("REQUEST:NEW")
    public void createNewRequest() {
        buildRequest(true);
        setupRestAssuredWithEnvironment();
    }
}
