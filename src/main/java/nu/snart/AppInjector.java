package nu.snart;

import com.google.inject.AbstractModule;

public class AppInjector extends AbstractModule {
    @Override
    protected void configure() {
        bind(StoryFactory.class).to(ExampleJiraStoryFactory.class);
    }
}
