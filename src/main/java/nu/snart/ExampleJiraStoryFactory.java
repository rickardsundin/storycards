package nu.snart;

import org.json.JSONObject;

/**
 * Example implementation of a story factory using json returned by Jira REST API.
 *
 * You will probably want to write your own using relevant fields for your Jira configuration.
 */
public class ExampleJiraStoryFactory implements StoryFactory {

    @Override
    public String[] fields() {
        return new String[] {"key", "summary", "description"};
    }

    @Override
    public Story createStoryFromJson(JSONObject issue) {
        JSONObject fields = issue.getJSONObject("fields");
        String id = issue.getString("key");
        String summary = fields.getString("summary");
        String userStory = fields.getString("description");
        String header = id + System.lineSeparator() + summary;
        return Story.create(id, header, JiraMarkup.toHtml(userStory));
    }
}
