package cn.com.autohome.GStack.ApiLog;

import com.google.gson.JsonObject;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.ExtractableResponse;

import java.util.Map;

import static cn.com.autohome.GStack.Basic.JsonUtils.parseJSON;
import static cn.com.autohome.GStack.Basic.JsonUtils.stringify;
import static org.apache.calcite.linq4j.Linq4j.asEnumerable;

public class LoggableResponse {
    int status;
    String statusText;
    Map cookies;
    Map<String, String> headers;
    Content content;

    static LoggableResponse fromSpec(ExtractableResponse spec) {
        LoggableResponse response = new LoggableResponse();
        response.status = spec.statusCode();
        response.statusText = spec.statusLine();
        response.cookies = spec.cookies();
        response.headers = asEnumerable(spec.headers().asList()).toMap(Header::getName, Header::getValue);
        response.content = new Content();
        response.content.mimeType = spec.contentType();

        if (response.content.mimeType.equals(ContentType.JSON)) {
            response.content.object = parseJSON(spec.body().toString(), JsonObject.class);

        } else response.content.text = spec.body().toString();

        return response;
    }

    static class Content {
        String mimeType;
        String text;
        JsonObject object;
    }


    @Override
    public String toString() {
        return stringify(this);
    }
}
