package cn.com.autohome.GStack.Basic;

import com.thoughtworks.gauge.execution.parameters.parsers.base.CustomParameterParser;
import gauge.messages.Spec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import static cn.com.autohome.GStack.Basic.StoreUtils.gets;

public class ArgumentedStringParser extends CustomParameterParser<String> {

    @Override
    protected String customParse(Class<?> parameterType, Spec.Parameter parameter) {
        return fillArgs(parameter.getValue());
    }

    @Override
    public boolean canParse(Class<?> parameterType, Spec.Parameter parameter) {
        return !parameter.hasTable() && parameterType == String.class;
    }


    private final static Pattern argPatter = Pattern.compile("@\\{([^{}]+)}", Pattern.DOTALL);


    public static String[] extractArguments(String input) {

        List<String> args = new ArrayList<>();
        try {
            Matcher argMatcher = argPatter.matcher(input);
            while (argMatcher.find()) {
                args.add(fillArgs(argMatcher.group(1)));
            }
        } catch (PatternSyntaxException ex) {
            // Syntax error in the regular expression
        }
        return args.toArray(new String[0]);
    }

    private static String replaceArguments(String input, HashMap<String, String> replecements) {
        Set<String> keys = replecements.keySet();
        Set<String> dynamicArgs = DynamicArguments.getArgNames();
        for (String name : dynamicArgs) {
            input = input.replace(String.format("@{%s}", name), DynamicArguments.getArgValue(name));
        }
        for (String name :
                keys) {
            input = input.replace(String.format("@{%s}", name), replecements.get(name));
        }
        return input;
    }

    public static String fillArgs(String input) {
        if (input == null)
            return "";
        else {
            String[] args = extractArguments(input);
            for (int i = 0; i < 2 && args.length > 0; i++) {
                input = replaceArguments(input, gets(args));
                args = extractArguments(input);
            }
            return input;
        }
    }
}
