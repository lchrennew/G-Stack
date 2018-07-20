package chun.li.GStack.Console.api.controllers;

import chun.li.GStack.Console.api.domain.Suite;
import chun.li.GStack.Console.api.services.SuiteService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Controller
@RequestMapping("/")
public class SuiteController {
    private final SuiteService suiteService;

    public SuiteController(SuiteService suiteService) {
        this.suiteService = suiteService;
    }

    @GetMapping("suites")
    @ResponseBody
    public Collection<Suite> getAll() {
        return suiteService.getAll();
    }

    @PostMapping("suites")
    @ResponseBody
    public Suite add(@RequestBody Suite suite) {
        return suiteService.save(suite);
    }

    @DeleteMapping("suites/{title}")
    public boolean delete(@PathVariable String title) {
        return suiteService.deleteByTitle(title);
    }
}
