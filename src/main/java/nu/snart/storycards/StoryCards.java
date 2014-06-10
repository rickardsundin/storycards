package nu.snart.storycards;

import java.net.URI;

public class StoryCards {
    private final StoryFactory storyFactory;

    public StoryCards(StoryFactory storyFactory) {
        this.storyFactory = storyFactory;
    }

    public void run(String[] args) {
        CommandLine commandLine = new CommandLine(System.console());

        URI jiraUri = (args.length == 1) ? URI.create(args[0]) : commandLine.readJiraUri();
        String user = commandLine.readUserName();
        char[] password = commandLine.readPassword();

        JiraRepository jiraRepository = authenticate(jiraUri, user, password);
        storyFactory.setJiraRepository(jiraRepository);
        StoryCardGenerator generator = new StoryCardGenerator(storyFactory);

        while (true) {
            String issueId = commandLine.readIssueId();
            if (issueId == null || issueId.isEmpty()) {
                commandLine.print("Bye!");
                return;
            }

            generator.generateCard(issueId);
        }
    }

    private JiraRepository authenticate(URI jiraUri, String user, char[] password) {
        HttpBasicAuthenticationRestClient restClient = new HttpBasicAuthenticationRestClient(jiraUri, user, password);
        return new JiraRepository(restClient);
    }
}
