package chun.li.GStack.Http.DSL.Assert;

import com.thoughtworks.gauge.Step;

import static chun.li.GStack.Http.Core.then;

public class Status {

    @Step("STATUS <statusCode>")
    public void CheckStatus(Integer statusCode) {
        then().statusCode(statusCode);
    }
}
