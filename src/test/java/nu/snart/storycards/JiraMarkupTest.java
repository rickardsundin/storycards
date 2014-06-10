package nu.snart.storycards;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class JiraMarkupTest {

    @Test
    public void toHtmlShouldHandleBoldText() {
        String result = JiraMarkup.toHtml("This is *fat* text.");
        assertThat(result, is("This is <bold>fat</bold> text."));
    }

    @Test
    public void toHtmlShouldHandleQuote() {
        String result = JiraMarkup.toHtml("As \"they\" say");
        assertThat(result, is("As &quot;they&quot; say"));
    }

    @Test
    public void toHtmlShouldEncodeAmpersand() {
        String result = JiraMarkup.toHtml("This & that");
        assertThat(result, is("This &amp; that"));
    }
}
