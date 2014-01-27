package nu.snart;

import org.apache.commons.lang3.StringUtils;

import java.io.Console;

import static org.apache.commons.lang3.StringUtils.isEmpty;

public class CommandLine {
    public static void main(String[] args) {
        Console console = System.console();
        String jiraUri = args.length == 1 ?
                console.readLine("Jira URI [%s]: ", args[0]) :
                console.readLine("Jira URI: ");
        String defaultUserName = System.getProperty("user.name");
        String usernameInput = console.readLine("Username [%s]: ", defaultUserName);
        String username = isEmpty(usernameInput) ? defaultUserName : usernameInput;
        char[] password = console.readPassword("Password: ");
        String pwdString = String.valueOf(password);// Temporary
        System.out.println("jiraUri = " + jiraUri);
        System.out.println("username = " + username);
        System.out.println("pwdString = " + pwdString);

        new JiraStoryRepository(jiraUri, username, password, new ExampleJiraStoryFactory());
    }
}
