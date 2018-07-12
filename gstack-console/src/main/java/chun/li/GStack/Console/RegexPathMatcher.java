package chun.li.GStack.Console;


import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import static java.util.regex.Pattern.compile;

public class RegexPathMatcher implements PathMatcher {

    static final AntPathMatcher o = new AntPathMatcher();

    @Override
    public boolean isPattern(String path) {
        return true;
    }

    @Override
    public boolean match(String pattern, String path) {
        try {
            Pattern argPattern = compile(pattern);
            Matcher matcher = argPattern.matcher(path);
            return matcher.matches();
        } catch (PatternSyntaxException ex) {
            // Syntax error in the regular expression
            return o.match(pattern, path) || pattern.contentEquals(path);
        }

    }

    @Override
    public boolean matchStart(String pattern, String path) {
        return false;
    }


    @Override
    public String extractPathWithinPattern(String pattern, String path) {
        try {
            Pattern argPattern = compile(pattern);
            Matcher matcher = argPattern.matcher(path);
            if (matcher.find()) {
                return matcher.group();
            }
        } catch (PatternSyntaxException ignore) {

        }
        return o.extractPathWithinPattern(pattern, path);
    }

    final Pattern group = compile("\\(?<(?<name>[^>]*)>(?<pattern>.*)\\)");

    private Map<String, Pattern> getNamedGroups(String regex) {
        Map<String, Pattern> namedGroups = new HashMap<>();
        Matcher m = group.matcher(regex);
        while (m.find()) {
            try {
                String name = m.group("name");
                Pattern pattern = compile(m.group("pattern"));
                namedGroups.putIfAbsent(name, pattern);
            } catch (PatternSyntaxException ignored) {

            }
        }
        return namedGroups;
    }

    private String[] getGroupNames(String pattern) {
        return getNamedGroups(pattern).keySet().toArray(new String[0]);
    }

    @Override
    public Map<String, String> extractUriTemplateVariables(String pattern, String path) {
        Map<String, String> vars = new HashMap<>();
        try {
            Pattern argPattern = compile(pattern);
            Matcher matcher = argPattern.matcher(path);
            if (matcher.find()) {
                String[] groups = getGroupNames(pattern);
                for (String group : groups) {
                    vars.putIfAbsent(group, matcher.group(group));
                }

            }
        } catch (PatternSyntaxException ex) {
            // Syntax error in the regular expression
        }
        return vars;
    }

    @Override
    public Comparator<String> getPatternComparator(String path) {
        return new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        };
    }

    @Override
    public String combine(String pattern1, String pattern2) {
        return String.join("|", pattern1, pattern2);
    }
}
