package cn.com.autohome.GStack.Http.DSL.Assert;

import com.thoughtworks.gauge.Step;

import static cn.com.autohome.GStack.Http.DSL.Core.then;

public class Status {

    @Step("STATUS <statusCode>")
    public void CheckStatus(Integer statusCode) {
        then().statusCode(statusCode);
    }
}
