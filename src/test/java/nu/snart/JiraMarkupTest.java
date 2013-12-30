package nu.snart;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class JiraMarkupTest {

    @Test
    public void toHtmlShouldHandleBoldText() {
        String result = JiraMarkup.toHtml("This is *fat* text.");
        assertThat(result, is("This is <bold>fat</bold> text."));
    }
}
