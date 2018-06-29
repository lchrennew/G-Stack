package cn.com.autohome.GStack.Http.DSL.Assert;

import cn.com.autohome.Http.DSL.Core;
import com.thoughtworks.gauge.Step;

public class Status {

    @Step("STATUS <statusCode>")
    public void CheckStatus(Integer statusCode) {
        Core.then().statusCode(statusCode);
    }
}
