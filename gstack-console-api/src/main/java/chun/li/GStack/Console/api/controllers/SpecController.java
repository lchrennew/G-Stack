package chun.li.GStack.Console.api.controllers;

import chun.li.GStack.Console.api.ExecuteOptions;
import chun.li.GStack.Console.api.domain.Result;
import chun.li.GStack.Console.api.domain.Suite;
import chun.li.GStack.Console.api.services.ResultService;
import chun.li.GStack.Console.api.services.SuiteService;
import chun.li.GStack.SuiteParser.SpecFile;
import chun.li.GStack.SuiteParser.SpecIndexer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Runtime.getRuntime;
import static java.lang.String.join;
import static java.util.Arrays.asList;
import static java.util.UUID.randomUUID;
import static org.apache.calcite.linq4j.Linq4j.asEnumerable;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping(value = "/")
public class SpecController {

    final static Pattern reportDate = Pattern.compile("\\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01]) ([01]\\d|2[0-4])\\.[0-6]\\d\\.[0-6]\\d");
    private final SuiteService suiteService;
    private final ResultService resultService;
    private final SimpMessagingTemplate messagingTemplate;

    @Value("${gstack.workspace}")
    private String workspace;

    @Value("${gstack.shell.charset}")
    private String shellCharset;

    @Value("${gstack.workspace.specs}")
    private String specs;

    public SpecController(SuiteService suiteService,
                          ResultService resultService, SimpMessagingTemplate messagingTemplate) {
        this.suiteService = suiteService;
        this.resultService = resultService;
        this.messagingTemplate = messagingTemplate;
    }

    @RequestMapping(path = "suites/{suite}/index", method = GET)
    @ResponseBody
    List<SpecFile> getIndex(@PathVariable String suite) throws FileNotFoundException {
        // TODO: 抽象suite路径算法
        return SpecIndexer.buildIndex(Paths.get(workspace, specs).toString(), suite);
    }


    @RequestMapping(path = "suites/{suite}/execute", method = POST)
    @ResponseStatus(code = HttpStatus.OK)
    @ResponseBody
    UUID execute(
            @PathVariable String suite,
            @RequestBody ExecuteOptions options) {
        return execute(suite, options.files, options.tags);
    }


    @RequestMapping(path = "suites/{suite}/execute", method = GET)
    @ResponseStatus(code = HttpStatus.OK)
    @ResponseBody
    private UUID execute(
            @PathVariable String suite,
            @RequestParam(value = "file", required = false) String[] files,
            @RequestParam(value = "tags", required = false) String tags) {
        UUID uuid = randomUUID();
        files = asEnumerable(files)
                .select(
                        file -> this.normalizeFile(suite, file))
                .toList()
                .toArray(new String[0]);
        executeShellAsync(suite, getShell(files, tags), uuid);
        return uuid;
    }

    private static final Pattern fileWithLn = Pattern.compile("(?<file>.*):(?<ln>\\d+)$");

    private String normalizeFile(String suite, String file) {
        Matcher matcher = fileWithLn.matcher(file);
        if (matcher.find()) {
            return String.join(
                    ":",
                    Paths.get(specs, suite, matcher.group("file")).toString(),
                    matcher.group("ln"));
        } else {
            return Paths
                    .get(specs, suite, file)
                    .toString();
        }
    }

    private void executeShellAsync(String suite, String shell, UUID uuid) {
        new Thread(
                () -> executeShell(
                        suite,
                        shell,
                        uuid,
                        this::pushShellOutput,
                        this::pushShellEnd))
                .start();
    }

    private void executeShell(String suite, String shell, UUID uuid, OnShellOutput onShellOutput, OnShellEnd onShellEnd) {
        Runtime runtime = getRuntime();
        try {
            String[] shellArgs = new String[]{"cmd", "/C", shell};
            Process process = runtime.exec(shellArgs, null, new File(workspace));
            System.out.println(String.join(" ", shellArgs));
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream(), shellCharset));
            StringBuilder output = new StringBuilder();
            String line;
            String report = null;
            while ((line = br.readLine()) != null) {
                onShellOutput.run(uuid, line);
                if (line.startsWith("Successfully generated html-report to =>")) {
                    Matcher matcher = reportDate.matcher(line);
                    if (matcher.find()) {
                        report = matcher.group();
                    }
                }
                output.append(line).append("\n");
            }

            Suite suite1 = suiteService.findByTitle(suite);
            Boolean succeeded = process.exitValue() == 0;
            resultService.save(
                    new Result(
                            suite1,
                            shell,
                            report,
                            succeeded,
                            output.toString()));


            onShellEnd.run(uuid, process.exitValue());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getShell(String[] files, String tags) {
        List<String> cmds = new ArrayList<>();
        cmds.add("gauge run");
        cmds.addAll(asList(files));
        if (tags != null) {
            cmds.add("--tags");
            cmds.add(String.format("\"%s\"", tags));
        }
        return join(" ", cmds.toArray(new String[0]));
    }

    private void pushShellOutput(UUID uuid, String message) {
        messagingTemplate
                .convertAndSend("/specs/output/" + uuid, message);
    }

    private void pushShellEnd(UUID uuid, int exitValue) {
        // Map<String, Object> closeHeader = Map.of("Connection", "Close");
        Map<String, Object> closeHeader = new HashMap<>();
        closeHeader.put("Connection", "Close");
        messagingTemplate
                .convertAndSend("/specs/output/" + uuid, exitValue, closeHeader);
    }


    @FunctionalInterface
    public interface OnShellOutput {
        public abstract void run(UUID uuid, String output);

    }

    @FunctionalInterface
    public interface OnShellEnd {
        public abstract void run(UUID uuid, int existValue);
    }

}
