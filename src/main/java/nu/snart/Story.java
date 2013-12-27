package nu.snart;

/**
 * Represents the part of a user story that is needed for a card.
 */
public class Story {
    final String id;
    final String title;
    final String userStory;
    public int points;

    private Story(String id, String title, String userStory, int points) {
        this.id = id;
        this.title = title;
        this.userStory = userStory;
        this.points = points;
    }

    public static Story create(String id, String title, String userStory, int points) {
        return new Story(id, title, userStory, points);
    }
}