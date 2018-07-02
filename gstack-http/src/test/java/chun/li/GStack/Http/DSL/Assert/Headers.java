package chun.li.GStack.Http.DSL.Assert;

import com.thoughtworks.gauge.Step;

import static chun.li.GStack.Http.DSL.Core.then;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;

public class Headers {


    @Step("CHECK:COOKIE <name> <value>")
    public void CheckCookie(String name, String value) {
        then().cookie(name, equalTo(value));
    }

    @Step("CHECK:COOKIE <name> null")
    public void CheckCookie(String name) {
        org.junit.Assert.assertNull(then().extract().detailedCookie(name));
    }

    @Step("CHECK:HEADER <name> <value>")
    public void CheckHeader(String name, String value) {
        then().header(name, equalTo(value));
    }

    @Step("CHECK:HEADER <name> null")
    public void CheckHeader(String name) {
        then().header(name, nullValue());
    }

    @Step("CHECK:CONTENTTYPE <contentType>")
    public void CheckContentType(String contentType) {
        then().header("content-type", equalTo(contentType));
    }


}
