package nu.snart.storycards;

import java.io.Console;
import java.net.URI;

public class CommandLine {

    private final Console console;

    CommandLine(Console console) {
        this.console = console;
    }

    public URI readJiraUri() {
        return URI.create(console.readLine("Jira URI: "));
    }

    public String readUserName() {
        String defaultValue = System.getProperty("user.name");
        String answer = console.readLine("Username [%s]: ", defaultValue);
        return (answer.isEmpty() ? defaultValue : answer);
    }

    public char[] readPassword() {
        return console.readPassword("Password: ");
    }

    public String readIssueId() {
        return console.readLine("Issue: ");
    }

    public void print(String message) {
        console.printf(message);
    }
}
