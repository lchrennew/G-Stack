package cn.com.autohome.GStack.ApiLog;

import com.google.gson.JsonObject;
import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import io.restassured.http.Header;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.ProxySpecification;

import java.util.Map;

import static cn.com.autohome.GStack.Basic.JsonUtils.stringify;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.http.ContentType.URLENC;
import static org.apache.calcite.linq4j.Linq4j.asEnumerable;

public class LoggableRequest {

    static LoggableRequest fromSpec(FilterableRequestSpecification spec) {
        LoggableRequest request = new LoggableRequest();
        request.URI = spec.getURI();
        request.method = spec.getMethod();
        request.baseURI = spec.getBaseUri();
        request.basePath = spec.getBasePath();
        request.cookies =
                asEnumerable(spec.getCookies().asList()).toMap(
                        Cookie::getName,
                        Cookie::toString
                );

        request.headers =
                asEnumerable(spec.getHeaders().asList()).toMap(
                        Header::getName,
                        Header::getValue
                );

        request.queryString = spec.getQueryParams();

        request.postData = new PostData();
        request.postData.mimeType = spec.getContentType();

        if (request.postData.mimeType == null) {
            request.postData.mimeType = ContentType.TEXT.toString();
        }
        if (JSON.matches( request.postData.mimeType)) {
            request.postData.object = spec.getBody();
        } else if (URLENC.matches(request.postData.mimeType)) {
            request.postData.forms = spec.getFormParams();
        } else
            request.postData.text = spec.getBody();

        ProxySpecification proxy = spec.getProxySpecification();
        if (proxy != null)
            request.proxy = proxy.toString();
        return request;
    }

    String proxy;
    String URI;
    String baseURI;
    String method;
    String basePath;

    Map<String, String> headers;


    Map<String, String> cookies;
    PostData postData;
    Map<String, String> queryString;

    static class PostData {
        String mimeType;
        String text;
        JsonObject object;
        Map<String, String> forms;
    }


    @Override
    public String toString() {
        return stringify(this);
    }
}
