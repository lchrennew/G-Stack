package chun.li.GStack.Basic;

import org.junit.Assert;

import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;
import static org.junit.Assert.*;

public class DynamicAsserts {
    @FunctionalInterface
    public interface AssertCall {
        void call(String actual, String expected) throws Exception;
    }


    static final Map<String, AssertCall> ASSERTS = new HashMap<>();
    private static final AssertCall ASSERT_EQ = Assert::assertNotEquals;
    private static final AssertCall ASSERT_NE = Assert::assertNotEquals;
    private static final AssertCall ASSERT_CONTAINS = ((actual, expected) -> assertTrue(format("\"%s\" doesn't contains \"%s\"", actual, expected), actual.toString().contains(expected.toString())));

    static {
        register("=", ASSERT_EQ);
        register("<>", ASSERT_NE);
        register("!=", ASSERT_NE);
        register("contains", ASSERT_CONTAINS);
    }


    public static void register(String match, AssertCall call) {
        ASSERTS.putIfAbsent(match, call);
    }

    public static void unregister(String match) {
        ASSERTS.remove(match);
    }

    public static void asserts(String actual, String match, String expected) {
        try {
            ASSERTS.getOrDefault(match, ASSERT_EQ).call(actual, expected);
        } catch (Exception ex) {
            fail(ex.getMessage());
        }
    }
}
