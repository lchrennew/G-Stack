package chun.li.GStack.Basic;

import com.thoughtworks.gauge.Table;
import com.thoughtworks.gauge.execution.parameters.parsers.base.CustomParameterParser;
import gauge.messages.Spec;

import static org.apache.calcite.linq4j.Linq4j.asEnumerable;

public class ArgumentedTableParser extends CustomParameterParser<Table> {
    @Override
    protected Table customParse(Class<?> parameterType, Spec.Parameter parameter) {
        Spec.ProtoTable protoTable = parameter.getTable();
        Table table = new Table(protoTable.getHeaders().getCellsList());
        protoTable.getRowsList().forEach(row ->
                table.addRow(
                        asEnumerable(row.getCellsList()).select(ArgumentedStringParser::fillArgs).toList())
        );
        return table;
    }

    @Override
    public boolean canParse(Class<?> parameterType, Spec.Parameter parameter) {
        return parameter.hasTable();
    }
}
