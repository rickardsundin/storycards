package nu.snart.sample;

import nu.snart.storycards.JiraRepository;
import nu.snart.storycards.Story;
import nu.snart.storycards.StoryFactory;
import org.json.JSONObject;

import static nu.snart.storycards.JiraMarkup.toHtml;

/**
 * Example implementation of a story factory using json returned by Jira REST API.
 *
 * You will probably want to write your own using relevant fields for your Jira configuration.
 */
public class ExampleJiraStoryFactory implements StoryFactory {

    private JiraRepository jiraRepository;

    @Override
    public String[] fields() {
        return new String[] {"key", "summary", "description"};
    }

    public Story createStoryFromJson(JSONObject issue) {
        JSONObject fields = issue.getJSONObject("fields");
        String id = issue.getString("key");
        String summary = fields.getString("summary");
        String userStory = fields.getString("description");
        String header = id + System.getProperty("line.separator") + summary;
        return Story.create(id, "Epic", toHtml(header), toHtml(userStory));
    }

    @Override
    public void setJiraRepository(JiraRepository jiraRepository) {
        this.jiraRepository = jiraRepository;
    }

    @Override
    public Story createStory(String issueId) {
        String issue = jiraRepository.issue(issueId);
        JSONObject jsonIssue = new JSONObject(issue);
        return createStoryFromJson(jsonIssue);
    }
}
