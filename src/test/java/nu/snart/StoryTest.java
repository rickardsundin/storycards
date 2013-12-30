package nu.snart;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class StoryTest {
    public static final String EXPECTED_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<story><header>ISSUE-123\nImplement a story card generator</header>" +
            "<body>As a Scrum Master,\n" +
            "I want to generate printable story cards from user stories,\n" +
            "so that I can print stories for the team whiteboard quicker</body></story>";

    @Test
    public void generateXmlFromStoryShouldReturnExpectedXml() {
        String userStory = "As a Scrum Master,\n" +
                "I want to generate printable story cards from user stories,\n" +
                "so that I can print stories for the team whiteboard quicker";
        Story story = Story.create("ISSUE-123", "ISSUE-123\nImplement a story card generator", userStory);

        String generatedXml = story.asXml("UTF-8");

        assertThat(generatedXml, is(EXPECTED_XML));
    }
}
