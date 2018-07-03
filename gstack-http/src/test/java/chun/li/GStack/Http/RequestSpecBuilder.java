package chun.li.GStack.Http;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

import static io.restassured.RestAssured.with;
import static io.restassured.http.ContentType.*;
import static org.apache.calcite.linq4j.Linq4j.asEnumerable;

public class RequestSpecBuilder {

    private final JsonObject config;

    private final RequestSpecification request;

    RequestSpecBuilder(JsonObject config, RequestSpecification request) {
        this.config = config;
        if (request == null) {
            this.request = with();
        } else this.request = request;
    }


    RequestSpecBuilder loadHeaders() {
        if (config.has("headers")) {
            JsonObject headersJson = config.getAsJsonObject("headers");
            Map<String, String> headers = asEnumerable(headersJson.keySet()).toMap(
                    key -> key,
                    key -> headersJson.get(key).getAsString()
            );
            headersJson.keySet().forEach(key -> headers.put(key, headersJson.get(key).getAsString()));
            request.headers(headers);
        }
        return this;
    }

    RequestSpecBuilder loadBaseUri() {
        if (config.has("baseUri")) {
            request.baseUri(config.get("baseUri").getAsString());
        }
        return this;
    }

    RequestSpecBuilder loadBasePath() {
        if (config.has("basePath")) {
            request.basePath(config.get("basePath").getAsString());
        }
        return this;
    }

    RequestSpecBuilder loadCookies() {
        if (config.has("cookies")) {
            JsonObject cookiesJson = config.getAsJsonObject("cookies");
            Map<String, String> cookies = asEnumerable(cookiesJson.keySet()).toMap(
                    key -> key,
                    key -> cookiesJson.get(key).getAsString()
            );
            request.cookies(cookies);
        }
        return this;
    }

    RequestSpecBuilder loadQueryString() {
        if (config.has("queryString")) {
            Map<String, ?> queryParams = getMultiValueParams(config.getAsJsonObject("queryString"));
            request.queryParams(queryParams);
        }
        return this;
    }

    RequestSpecBuilder loadParams() {
        if (config.has("params")) {
            Map<String, ?> params = getMultiValueParams(config.getAsJsonObject("params"));
            request.params(params);
        }
        return this;
    }

    RequestSpecBuilder loadPostData() {
        if (config.has("postData")) {
            JsonObject postDataJson = config.getAsJsonObject("postData");
            String contentType = loadContentType(postDataJson);
            if (URLENC.matches(contentType))
                loadForms(postDataJson);
            else if (JSON.matches(contentType))
                loadPostJson(postDataJson);
            else
                loadPostText(postDataJson);
        }
        return this;
    }

    private static Map<String, ?> getMultiValueParams(JsonObject paramsJson) {
        return asEnumerable(paramsJson.keySet()).toMap(
                key -> key,
                key -> {
                    JsonElement el = paramsJson.get(key);
                    if (el.isJsonArray())
                        return asEnumerable(el.getAsJsonArray()).select(JsonElement::getAsString).toList();
                    else
                        return el.getAsString();
                }
        );
    }

    private String loadContentType(JsonObject postDataJson) {
        String contentType;
        if (postDataJson.has("mimeType")) {
            contentType = postDataJson.get("mimeType").getAsString();
        } else {
            contentType = TEXT.toString();
        }
        request.contentType(contentType);
        return contentType;
    }


    private void loadPostText(JsonObject postDataJson) {
        if (postDataJson.has("text")) {
            request.body(postDataJson.get("text").getAsString());
        }
    }

    private void loadPostJson(JsonObject postDataJson) {
        if (postDataJson.has("object")) {
            request.body(postDataJson.getAsJsonObject("object").toString());
        }
    }

    private void loadForms(JsonObject postDataJson) {
        if (postDataJson.has("forms")) {
            JsonObject formsJson = postDataJson.getAsJsonObject("forms");
            Map<String, ?> formParams = getMultiValueParams(formsJson);
            request.formParams(formParams);
        }
    }
}
