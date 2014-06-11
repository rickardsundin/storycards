package nu.snart.storycards;

/**
 * Implement this interface to make sure that Story objects
 * are being generated from issues the way you want them to.
 */
public interface StoryFactory {

    void setJiraRepository(JiraRepository jiraRepository);

    Story createStory(String issueId);
}
