package nu.snart;

import org.apache.commons.lang3.StringUtils;
import org.glassfish.jersey.client.filter.HttpBasicAuthFilter;
import org.json.JSONObject;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Fetches stories from Jira.
 */
public class JiraStoryRepository {
    private final String jiraUri;
    private final String username;
    private final String password;
    private final StoryFactory storyFactory;

    /**
     *
     * @param jiraUri Uri to your Jira installation, make sure to use https. (E.g. "https://jira.mycompany.com/")
     * @param username Your Jira username
     * @param password Your Jira password
     * @param storyFactory Your story factory implementation
     */
    public JiraStoryRepository(String jiraUri, String username, String password, StoryFactory storyFactory) {
        ensureHttps(jiraUri);
        this.jiraUri = jiraUri;
        this.username = username;
        this.password = password;
        this.storyFactory = storyFactory;
    }

    /**
     * Get stories for issue ids.
     */
    public Story[] findByIds(String... issues) {
        List<Story> stories = new ArrayList<Story>();
        for (String issue : issues) {
            stories.add(findById(issue));
        }
        return stories.toArray(new Story[issues.length]);
    }

    /**
     * Get story for issue id.
     */
    public Story findById(String id) {
        JSONObject issueAsJson = getJsonFrom(uriForIssue(id), storyFactory.fields());
        return storyFactory.createStoryFromJson(issueAsJson);
    }

    private URI uriForIssue(String issueId) {
        assertIssueId(issueId);
        try {
            return new URI(jiraUri).resolve("/rest/api/2/issue/").resolve(issueId);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(issueId + " does not look like a valid Jira issue id");
        }
    }

    static void ensureHttps(String uri) {
        if (!uri.startsWith("https")) {
            throw new IllegalArgumentException("Make sure that your Jira URI starts with 'https'. This is important since HTTP Basic authentication is used.");
        }
    }

    static void assertIssueId(String issueId) {
        if (!issueId.matches("[a-zA-Z]+-\\d+")) {
            throw new IllegalArgumentException(issueId + " does not look like a valid Jira issue id");
        }
    }

    /**
     * @param issueUri uri to issue
     * @param fields request only these fields (provide no fields to request all)
     * @return Jira issue as json
     */
    private JSONObject getJsonFrom(URI issueUri, String... fields) {
        Client client = ClientBuilder.newClient();
        client.register(new HttpBasicAuthFilter(username, password));
        WebTarget target = client.target(issueUri);
        if (fields != null && fields.length > 0) {
            target.queryParam("fields", StringUtils.join(fields, ","));
        }
        Response response = target.request().get();
        String responseBody = response.readEntity(String.class);
        return new JSONObject(responseBody);
    }
}
