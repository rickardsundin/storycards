package nu.snart;

import org.apache.fop.apps.FOPException;

import javax.xml.transform.TransformerException;
import java.io.Console;
import java.io.IOException;

import static org.apache.commons.lang3.StringUtils.isEmpty;

public class CommandLine {
    public static void main(String[] args) throws TransformerException, IOException, FOPException {
        Console console = System.console();
        String jiraUri = args.length == 1 ?
                requestWithDefaultValue(console, "Jira URI", args[0]) :
                console.readLine("Jira URI: ");
        String defaultUserName = System.getProperty("user.name");
        String username = requestWithDefaultValue(console, "Username", defaultUserName);
        char[] password = console.readPassword("Password: ");

        JiraStoryRepository storyRepository = new JiraStoryRepository(jiraUri, username, password, new ExampleJiraStoryFactory());
        StoryCardGenerator cardGenerator = new StoryCardGenerator();
        while (true) {
            String issueKey = console.readLine("Issue: ");
            if (issueKey.isEmpty()) {
                System.out.println("Bye");
                return;
            }
            Story story = storyRepository.findById(issueKey);
            cardGenerator.generatePdf(story);
        }
    }

    private static String requestWithDefaultValue(Console console, String label, String defaultValue) {
        String response = console.readLine(label + " [%s]: ", defaultValue);
        return isEmpty(response) ? defaultValue : response;
    }
}

