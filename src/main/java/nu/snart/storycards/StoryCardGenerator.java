package nu.snart.storycards;

import org.apache.fop.apps.FOPException;

import javax.xml.transform.TransformerException;
import java.io.IOException;

public class StoryCardGenerator {
    private final StoryFactory storyFactory;

    public StoryCardGenerator(StoryFactory storyFactory) {
        this.storyFactory = storyFactory;
    }

    public void generateCard(String issueId) {
        Story story = storyFactory.createStory(issueId);
        try {
            new PdfGenerator().generatePdf(story);
        } catch (TransformerException | IOException | FOPException e) {
            e.printStackTrace();
        }
    }

}
