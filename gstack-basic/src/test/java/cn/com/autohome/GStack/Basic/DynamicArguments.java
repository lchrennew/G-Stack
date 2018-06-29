package cn.com.autohome.GStack.Basic;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

import static java.lang.System.currentTimeMillis;

public class DynamicArguments {
    private final static Map<String, Supplier<String>> args = new HashMap<>();

    static {
        args.put("timestamp", () -> Long.toString(currentTimeMillis()));
    }

    static Set<String> getArgNames() {
        return args.keySet();
    }

    static String getArgValue(String key) {
        return args.get(key).get();
    }

    public static void register(String name, Supplier<String> supplier) {
        if (!args.containsKey(name))
            args.put(name, supplier);
    }

    public static void unregister(String name) {
        args.remove(name);
    }
}
