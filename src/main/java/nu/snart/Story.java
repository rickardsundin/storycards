package nu.snart;

/**
 * Represents the part of a user story that is needed for a card.
 */
public class Story {
    public String id;
    private final String header;
    private final String body;

    private Story(String id, String header, String body) {
        this.id = id;
        this.header = header;
        this.body = body;
    }

    /**
     * Create a story
     * @param id used only to identify this story, will not appear in generated xml
     * @param header the story header
     * @param body the story body
     * @return a story object
     */
    public static Story create(String id, String header, String body) {
        return new Story(id, header, body);
    }

    /**
     * Generate xml for a story.
     */
    String asXml(String encoding) {
        return xmlWrapper(encoding, header, body);
    }

    private String xmlWrapper(String encoding, String header, String body) {
        return "<?xml version=\"1.0\" encoding=\"" + encoding + "\"?>" +
                System.lineSeparator() +
                "<story>" +
                "<header>" + header + "</header>" +
                "<body>" + body + "</body>" +
                "</story>";
    }
}