package chun.li.GStack.Console.api.controllers;

import chun.li.GStack.Console.api.domain.Result;
import chun.li.GStack.Console.api.services.ResultService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class ResultController {

    private final ResultService resultService;

    public ResultController(ResultService resultService) {
        this.resultService = resultService;
    }

    @GetMapping("suites/{title}/logs")
    @ResponseBody
    public Iterable<Result> getResults(@PathVariable String title) {
        Iterable<Result> results = resultService.findBySuiteTitle(title);
        return results;
    }
}
