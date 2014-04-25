package nu.snart;

import org.junit.Test;

import java.net.URI;

public class JiraStoryRepositoryTest {
    @Test
    public void assertIssueIdShouldDoNothingOnValidId() {
        JiraStoryRepository.assertIssueId("ABC-1");
        JiraStoryRepository.assertIssueId("A-12");
        JiraStoryRepository.assertIssueId("Abc-123");
    }

    @Test(expected = NullPointerException.class)
    public void assertIssueShouldThrowNullPointerExceptionOnNullId() {
        JiraStoryRepository.assertIssueId(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void assertIssueShouldThrowIllegalArgumentExceptionOnEmptyString() {
        JiraStoryRepository.assertIssueId("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void assertIssueShouldThrowExceptionOnInvalidId() {
        JiraStoryRepository.assertIssueId("malformed");
    }

    @Test
    public void ensureHttpsShouldDoNothingOnHttpsUri() {
        JiraStoryRepository.ensureHttps(URI.create("https://my.jira.com"));
    }

    @Test (expected = IllegalArgumentException.class)
    public void ensureHttpsShouldThrowExceptionOnHttpUri() {
        JiraStoryRepository.ensureHttps(URI.create("http://my.jira.com"));
    }
}
