package cn.com.autohome.GStack.Http.DSL;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.thoughtworks.gauge.datastore.DataStore;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;

import static cn.com.autohome.Basic.JsonUtils.parseJSON;
import static com.thoughtworks.gauge.datastore.DataStoreFactory.getScenarioDataStore;
import static io.restassured.RestAssured.with;
import static io.restassured.config.EncoderConfig.encoderConfig;
import static io.restassured.config.RestAssuredConfig.config;
import static io.restassured.http.ContentType.TEXT;
import static io.restassured.http.ContentType.URLENC;
import static java.lang.System.getenv;
import static org.apache.calcite.linq4j.Linq4j.asEnumerable;

public class Core {

    private final static String BASE_URI = "restassured_baseUri";
    private final static String CONTENTTYPE = "restassured_contenttype";
    private final static String BASE_PATH = "restassured_basePath";
    private final static String ACCEPT = "restassured_accept";
    private final static String HEADERS = "restassured_headers_";
    private final static Integer HEADERS_LEN = HEADERS.length();
    private final static String COOKIES = "restassured_cookies_";
    private final static Integer COOKIES_LEN = COOKIES.length();
    private final static String QUERY = "restassured_query_";
    private final static Integer QUERY_LEN = QUERY.length();

    private final static String REQSPEC = "reqspec";
    private final static String THEN = "then";


    public static RequestSpecification buildRequest() {
        return buildRequest(false);
    }


    public static RequestSpecification buildRequest(boolean createNew) {
        DataStore store = getScenarioDataStore();
        if (createNew)
            store.remove(REQSPEC);
        RequestSpecification requestSpecification =
                createNew ?
                        with() :
                        (RequestSpecification) store.get(REQSPEC);

        if (requestSpecification == null) {
            store.put(REQSPEC, requestSpecification = with());
        }
        requestSpecification.config(
                config().set().encoderConfig(
                        encoderConfig().defaultContentCharset("utf-8")));
        return requestSpecification;
    }

    public static void then(ValidatableResponse response) {
        DataStore store = getScenarioDataStore();
        store.put(THEN, response);
    }

    public static ValidatableResponse then() {
        DataStore store = getScenarioDataStore();
        return (ValidatableResponse)store.get(THEN);
    }

    public static void setupRestAssuredWithEnvironment() {
        String baseUri = getenv(BASE_URI);
        String contentType = getenv(CONTENTTYPE);
        String basePath = getenv(BASE_PATH);
        String accept = getenv(ACCEPT);
        Map<String, String> headers = new HashMap<>();
        Map<String, String> cookies = new HashMap<>();
        Map<String, String> query = new HashMap<>();
        System.getenv().forEach((key, value) -> {

            if (key.startsWith(HEADERS)) {
                headers.put(key.substring(HEADERS_LEN), value);
            } else if (key.startsWith(COOKIES)) {
                cookies.put(key.substring(COOKIES_LEN), value);
            } else if (key.startsWith(QUERY)) {
                query.put(key.substring(QUERY_LEN), value);
            }
        });
        RequestSpecification requestSpecification = buildRequest();
        if (baseUri != null)
            requestSpecification
                    .baseUri(baseUri);
        if (basePath != null)
            requestSpecification.basePath(basePath);
        if (accept != null)
            requestSpecification.accept(accept);
        if (contentType != null)
            requestSpecification.contentType(contentType);
        requestSpecification
                .headers(headers)
                .cookies(cookies)
                .queryParams(query);
    }

    public static void loadJsonConfig(String jsonConfig) {
        JsonObject config = parseJSON(jsonConfig, JsonObject.class);
        Map<String, String> cookies = new HashMap<>();
        loadHeaders(config);
        loadBaseUri(config);
        loadBasePath(config);
        loadCookies(config);
        loadQueryString(config);
        loadPostData(config);
    }

    private static void loadPostData(JsonObject config) {
        if (config.has("postData")) {
            JsonObject postDataJson = config.getAsJsonObject("postData");
            String contentType = loadContentType(postDataJson);
            if (URLENC.matches(contentType))
                loadForms(postDataJson);
//            else if (JSON.matches(contentType))
//                loadPostJson(postDataJson);
//            else
//                loadPostText(postDataJson);
        }
    }

    private static void loadPostText(JsonObject postDataJson) {
        if (postDataJson.has("text")) {
            buildRequest().body(postDataJson.get("text").getAsString());
        }
    }

    private static void loadPostJson(JsonObject postDataJson) {
        if (postDataJson.has("object")) {
            buildRequest().body(postDataJson.getAsJsonObject("object").toString());
        }
    }

    private static void loadForms(JsonObject postDataJson) {
        if (postDataJson.has("forms")) {
            JsonObject formsJson = postDataJson.getAsJsonObject("forms");
            Map<String, ?> formParams = getMultiValueParams(formsJson);
            buildRequest().formParams(formParams);
        }
    }

    private static String loadContentType(JsonObject postDataJson) {
        String contentType;
        if (postDataJson.has("mimeType")) {
            contentType = postDataJson.get("mimeType").getAsString();
        } else {
            contentType = TEXT.toString();
        }
        buildRequest().contentType(contentType);
        return contentType;
    }

    private static void loadQueryString(JsonObject config) {
        if (config.has("queryString")) {
            Map<String, ?> queryParams = getMultiValueParams(config.getAsJsonObject("queryString"));
            buildRequest().queryParams(queryParams);
        }
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

    private static void loadCookies(JsonObject config) {
        if (config.has("cookies")) {
            JsonObject cookiesJson = config.getAsJsonObject("cookies");
            Map<String, String> cookies = asEnumerable(cookiesJson.keySet()).toMap(
                    key -> key,
                    key -> cookiesJson.get(key).getAsString()
            );
            buildRequest().cookies(cookies);
        }
    }

    private static void loadBasePath(JsonObject config) {
        if (config.has("basePath")) {
            buildRequest().basePath(config.get("basePath").getAsString());
        }
    }

    private static void loadBaseUri(JsonObject config) {
        if (config.has("baseUri")) {
            buildRequest().baseUri(config.get("baseUri").getAsString());
        }
    }

    private static void loadHeaders(JsonObject config) {
        if (config.has("headers")) {
            JsonObject headersJson = config.getAsJsonObject("headers");
            Map<String, String> headers = asEnumerable(headersJson.keySet()).toMap(
                    key -> key,
                    key -> headersJson.get(key).getAsString()
            );
            headersJson.keySet().forEach(key -> headers.put(key, headersJson.get(key).getAsString()));
            buildRequest().headers(headers);
        }
    }
}
