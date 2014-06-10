package nu.snart.sample;

import nu.snart.storycards.StoryCards;

public class Main {

    public static void main(String[] args) {
        new StoryCards(new ExampleJiraStoryFactory()).run(args);
    }

}
