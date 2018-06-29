package cn.com.autohome.GStack.Basic;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonUtils {
    private final static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static <T> String stringify(T src) {
        return gson.toJson(src);
    }

    public static <T> T parseJSON(String json, Class<T> classOfType){
        return gson.fromJson(json, classOfType);
    }

}
