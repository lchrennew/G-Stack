package chun.li.GStack.Http;

import com.google.gson.JsonObject;
import com.thoughtworks.gauge.datastore.DataStore;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;

import static chun.li.GStack.Basic.ConfigUtils.env;
import static chun.li.GStack.Basic.JsonUtils.parseJSON;
import static com.thoughtworks.gauge.datastore.DataStoreFactory.getScenarioDataStore;
import static io.restassured.RestAssured.*;
import static io.restassured.config.EncoderConfig.encoderConfig;
import static io.restassured.config.RestAssuredConfig.config;
import static io.restassured.http.ContentType.TEXT;
import static io.restassured.http.ContentType.fromContentType;

public class Core {

    private final static String BASE_URI = env("restassured_baseUri", DEFAULT_URI);
    private final static ContentType CONTENTTYPE = fromContentType(env("restassured_accept", TEXT.toString()));
    private final static String CHARSET = env("restassured_charset", "utf-8");
    private final static String BASE_PATH = env("restassured_basePath", DEFAULT_PATH);
    private final static String ACCEPT = CONTENTTYPE.getAcceptHeader();
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
                        encoderConfig().defaultContentCharset(CHARSET)));
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
        String contentType = CONTENTTYPE.withCharset(CHARSET);
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
            requestSpecification
                    .baseUri(BASE_URI);
            requestSpecification.basePath(BASE_PATH);
            requestSpecification.accept(ACCEPT);
            requestSpecification.contentType(contentType);
        requestSpecification
                .headers(headers)
                .cookies(cookies)
                .queryParams(query);
    }


    public static void loadJsonConfig(String jsonConfig) {
        JsonObject config = parseJSON(jsonConfig, JsonObject.class);
        new RequestSpecBuilder(config, buildRequest())
                .loadHeaders()
                .loadBasePath()
                .loadCookies()
                .loadParams()
                .loadQueryString()
                .loadPostData();
    }










}

