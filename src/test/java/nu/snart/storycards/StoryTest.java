package nu.snart.storycards;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StoryTest {
    private static final String EXPECTED_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
            "<story><header>ISSUE-123\nImplement a story card generator</header>" +
            "<body>As a Scrum Master,\n" +
            "I want to generate printable story cards from user stories,\n" +
            "so that I can print stories for the team whiteboard quicker</body></story>";

    private static final String USER_STORY = "As a Scrum Master,\n" +
            "I want to generate printable story cards from user stories,\n" +
            "so that I can print stories for the team whiteboard quicker";

    @Test
    public void generateXmlFromStoryShouldReturnExpectedXml() {
        Story story = Story.create("ISSUE-123", "ISSUE-123\nImplement a story card generator", USER_STORY);

        String generatedXml = story.asXml("UTF-8");

        assertThat(generatedXml).isXmlEqualTo(EXPECTED_XML);
    }
}
