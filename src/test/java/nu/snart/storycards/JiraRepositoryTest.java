package nu.snart.storycards;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class JiraRepositoryTest {

    @Mock RestClient restClient;
    @InjectMocks JiraRepository repository;

    @Test
    public void shouldMakeRestCallAndReturnJson() {
        when(restClient.get("/rest/api/2/issue/", "ISSUE-123")).thenReturn("SOME JSON");
        assertThat(repository.issue("ISSUE-123"), is("SOME JSON"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotAcceptInvalidIssueId() {
        new JiraRepository(null).issue("INVALID");
    }
}
