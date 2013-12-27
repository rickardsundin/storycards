package nu.snart;

import org.apache.fop.apps.FOPException;
import org.junit.Before;
import org.junit.Test;

import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;

import static nu.snart.Story.create;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class StoryCardGeneratorTest {

    private Story story;
    public static final String EXPECTED_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<story><header>ISSUE-123 = 42 SPs\nImplement a story card generator</header>" +
            "<body>As a Scrum Master,\n" +
            "I would like to generate printable story cards from user stories,\n" +
            "so that I can print stories for the team whiteboard quicker</body></story>";

    private static final String EXPECTED_XSL_FO = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
            "<fo:root xmlns:fo=\"http://www.w3.org/1999/XSL/Format\">" +
            "<fo:layout-master-set>" +
            "<fo:simple-page-master margin-right=\"2.5cm\" margin-left=\"2.5cm\" margin-bottom=\"2cm\" margin-top=\"1cm\" page-width=\"29.7cm\" page-height=\"21cm\" master-name=\"first\">" +
            "<fo:region-body margin-top=\"1cm\"/>" +
            "<fo:region-before extent=\"1cm\"/>" +
            "<fo:region-after extent=\"1.5cm\"/>" +
            "</fo:simple-page-master>" +
            "</fo:layout-master-set><fo:page-sequence master-reference=\"first\">" +
            "<fo:flow flow-name=\"xsl-region-body\">" +
            "<fo:block linefeed-treatment=\"preserve\" space-after=\"5mm\" space-before=\"5mm\" padding=\"5mm\" background-color=\"#4f81bd\" color=\"white\" font-family=\"sans-serif\" font-size=\"44pt\">ISSUE-123 = 42 SPs\nImplement a story card generator</fo:block>" +
            "<fo:block linefeed-treatment=\"preserve\" font-size=\"36pt\" font-family=\"verdana\">As a Scrum Master,\nI would like to generate printable story cards from user stories,\nso that I can print stories for the team whiteboard quicker</fo:block></fo:flow></fo:page-sequence></fo:root>";


    @Before
    public void setUp() {
        // Setup data for a user story
        String storyId = "ISSUE-123";
        String storyTitle = "Implement a story card generator";
        String userStory = "As a Scrum Master,\n" +
                "I would like to generate printable story cards from user stories,\n" +
                "so that I can print stories for the team whiteboard quicker";
        int storyPoints = 42;
        story = create(storyId, storyTitle, userStory, storyPoints);
    }

    @Test
    public void generateXmlFromStoryShouldReturnExpectedXml() {
        String generatedXml = new StoryCardGenerator().generateXml(story, "UTF-8");
        assertThat(generatedXml, is(EXPECTED_XML));
    }

    @Test
    public void generateFoFromXmlShouldReturnExpectedXslFo() throws IOException, TransformerException {
        String xslFo = new StoryCardGenerator().generateFoFromXml(EXPECTED_XML);
        assertThat(xslFo, is(EXPECTED_XSL_FO));
    }

    @Test
    public void generatePdfShouldGenerateFileOfExpectedSize() throws TransformerException, IOException, FOPException {
        File file = new StoryCardGenerator().generatePdf(story);
        assertThat(file.exists(), is(true));
        assertThat("Size of generated PDF", file.length(), is(5586L));
    }
}
