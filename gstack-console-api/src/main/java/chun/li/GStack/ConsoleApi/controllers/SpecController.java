package chun.li.GStack.ConsoleApi.controllers;

import chun.li.GStack.SuiteParser.SpecFile;
import chun.li.GStack.SuiteParser.SpecIndexer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.lang.Runtime.getRuntime;
import static java.lang.String.join;
import static java.util.Arrays.asList;
import static java.util.UUID.randomUUID;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping(value = "/specs")
public class SpecController {
    @RequestMapping(path = "/index", method = GET)
    @ResponseBody
    List<SpecFile> getIndex() throws FileNotFoundException {
        return SpecIndexer.buildIndex(".");
    }

    @Value("${gstack.workspace}")
    String workspace;

    @Value("${gstack.shell.charset}")
    String shellCharset;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;


    @RequestMapping(path = "/execute", method = GET)
    @ResponseStatus(code = HttpStatus.OK)
    @ResponseBody
    UUID execute(@RequestParam(value = "file", required = false) String[] files,
                 @RequestParam(value = "tags", required = false) String tags)
            throws IOException, InterruptedException {

        String shell = getShell(files, tags);
        UUID uuid = randomUUID();
        new Thread(() -> {
            Runtime runtime = getRuntime();
            try {
                Process process = runtime.exec(new String[]{"cmd", "/C", shell}, null, new File(workspace));
                BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream(), shellCharset));
                String sCurrentLine;
                while ((sCurrentLine = br.readLine()) != null) {
                    pushShellOutput(uuid, sCurrentLine);
                }
                pushShellEnd(uuid);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        return uuid;
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

    void pushShellOutput(UUID uuid, String message) {
        messagingTemplate.convertAndSend("/specs/output/" + uuid, message);
    }

    void pushShellEnd(UUID uuid) {
        messagingTemplate.convertAndSend("/specs/output/" + uuid, "!!!!!!");
    }


}
