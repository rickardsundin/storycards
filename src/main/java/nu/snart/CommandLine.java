package nu.snart;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.apache.fop.apps.FOPException;

import javax.xml.transform.TransformerException;
import java.io.Console;
import java.io.IOException;
import java.net.URI;

import static org.apache.commons.lang3.StringUtils.isEmpty;

public class CommandLine {

    private final Console console;

    public static void main(String[] args) throws TransformerException, IOException, FOPException {
        Injector injector = Guice.createInjector(new AppInjector());
        CommandLine app = injector.getInstance(CommandLine.class);
        app.run(args);
    }

    public CommandLine() {
        console = System.console();
    }

    private void run(String[] args) throws TransformerException, IOException, FOPException {
        URI jiraUri = getJiraUri(args);
        String defaultUserName = System.getProperty("user.name");
        String username = requestWithDefaultValue(console, "Username", defaultUserName);
        char[] password = console.readPassword("Password: ");

        run(jiraUri, username, password);
    }

    private URI getJiraUri(String[] args) {
        String uriString = args.length == 1 ?
                requestWithDefaultValue(console, "Jira URI", args[0]) :
                console.readLine("Jira URI: ");
        return URI.create(uriString);
    }

    private void run(URI jiraUri, String username, char[] password) throws TransformerException, IOException, FOPException {
        JiraStoryRepository storyRepository = new JiraStoryRepository(jiraUri, username, password);
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

