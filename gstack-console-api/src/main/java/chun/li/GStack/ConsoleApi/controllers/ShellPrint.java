package chun.li.GStack.ConsoleApi.controllers;

public class ShellPrint {
    private String content;

    public ShellPrint(String message) {
        this.content = message;
    }

    public String getContent() {
        return content;
    }
}
