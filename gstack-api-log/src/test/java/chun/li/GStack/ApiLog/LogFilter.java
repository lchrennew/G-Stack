package chun.li.GStack.ApiLog;

import io.restassured.filter.FilterContext;
import io.restassured.filter.OrderedFilter;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

import static chun.li.GStack.ApiLog.LoggableResponse.fromSpec;
import static chun.li.GStack.Basic.Core.print;

public class LogFilter implements OrderedFilter {

    private static final int precedence = LOWEST_PRECEDENCE;
    private static final String LOGFILTER = "logfilter";

    @Override
    public int getOrder() {
        return precedence;
    }

    @Override
    public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec, FilterContext ctx) {
        if (ctx.getValue(LOGFILTER) == null) {
            ctx.setValue(LOGFILTER, true);
            String req = LoggableRequest.fromSpec(requestSpec).toString();
            print("===========================");
            print("HTTP REQUEST");
            print("===========================");
            print(req);
            Response response = ctx.next(requestSpec, responseSpec);
            String res = fromSpec(response.then().extract()).toString();
            print("===========================");
            print("HTTP RESPONSE");
            print("===========================");
            print(res);
            ctx.setValue(LOGFILTER, null);
            return response;
        } else
            return ctx.next(requestSpec, responseSpec);
    }
}
