package nu.snart.storycards;

/**
 * Fetches issue data from Jira.
 */
public class JiraRepository {
    private final RestClient restClient;

    /**
     * @param restClient Client used for communication with Jira REST API
     */
    public JiraRepository(RestClient restClient) {
        this.restClient = restClient;
    }

    /**
     * Get story for issue id.
     */
    public String issue(String id) {
        assertIssueId(id);
        return restClient.get("/rest/api/2/issue/", id);
    }

    private static void assertIssueId(String issueId) {
        if (!issueId.matches("[a-zA-Z]+-\\d+")) {
            throw new IllegalArgumentException(issueId + " does not look like a valid Jira issue id");
        }
    }
}

