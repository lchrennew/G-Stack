package chun.li.GStack.Basic;

import com.thoughtworks.gauge.Table;
import com.thoughtworks.gauge.datastore.DataStore;
import org.apache.calcite.linq4j.function.Predicate1;

import java.util.Arrays;
import java.util.HashMap;

import static com.thoughtworks.gauge.datastore.DataStoreFactory.*;
import static com.thoughtworks.gauge.datastore.DataStoreFactory.getScenarioDataStore;
import static org.apache.calcite.linq4j.Linq4j.asEnumerable;

public class StoreUtils {
    public static final String KEY_SUFFIX = "#";
    public static final Predicate1<String> isKey = c -> c.endsWith(KEY_SUFFIX);
    public static final Predicate1<String> notKey = c -> !c.endsWith(KEY_SUFFIX);

    public static HashMap<String, String> gets(String[] args) {
        HashMap<String, String> configs = new HashMap<>();
        Arrays.asList(args).forEach(arg -> {
            String value = get(arg);
            configs.put(arg, value);
        });
        return configs;
    }


    private static String get(String name) {
        String value = (String) getScenarioDataStore().get(name);
        if (value == null) {
            value = (String) getSpecDataStore().get(name);
            if (value == null) {
                value = (String) getSuiteDataStore().get(name);
                if (value == null)
                    value = "";
            }
        }
        return value;
    }

    public static void storeTable(Table table, DataStore dataStore) {
        if (isDynamicTable(table))
            storeDynamicTable(table, dataStore);
        else {
            table.getTableRows().forEach(row -> dataStore.put(
                    row.getCell("name"),
                    row.getCell("value")));
        }

    }

    private static boolean isDynamicTable(Table table) {
        return !table.getColumnName(0).equals("name") ||
                !table.getColumnName(1).equals("value");
    }

    private static void storeDynamicTable(Table table, DataStore dataStore) {
        final String keyColumn = getKeyColumn(table);
        String[] keys = table.getColumnValues(keyColumn).toArray(new String[0]);
        Integer rowCount = keys.length;
        final String keyName = getKeyName(keyColumn);
        asEnumerable(table.getColumnNames())
                .where(notKey)
                .forEach(valueColumn -> {
                    String[] values = table.getColumnValues(valueColumn).toArray(new String[0]);
                    for (int i = 0; i < rowCount; i++) {
                        dataStore.put(
                                String.format(
                                        "%s.%s.%s",
                                        keyName,
                                        keys[i],
                                        valueColumn),
                                values[i]
                        );
                    }
                });
    }

    private static String getKeyName(String keyColumn) {
        return keyColumn.replaceAll(String.format("(?sm)%s\\z", KEY_SUFFIX), "");
    }

    public static String getKeyName(Table table) {
        return getKeyName(getKeyColumn(table));
    }

    public static String getKeyColumn(Table table) {
        String keyColumn = asEnumerable(table.getColumnNames()).firstOrDefault(isKey);
        if (keyColumn == null)
            keyColumn = table.getColumnName(0);
        return keyColumn;
    }
}
