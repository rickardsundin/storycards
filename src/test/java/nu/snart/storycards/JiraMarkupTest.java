package nu.snart.storycards;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JiraMarkupTest {

    @Test
    public void toHtmlShouldHandleBoldText() {
        String result = JiraMarkup.toHtml("This is *fat* text.");
        assertThat(result).isEqualTo("This is <bold>fat</bold> text.");
    }

    @Test
    public void toHtmlShouldHandleQuote() {
        String result = JiraMarkup.toHtml("As \"they\" say");
        assertThat(result).isEqualTo("As &quot;they&quot; say");
    }

    @Test
    public void toHtmlShouldEncodeAmpersand() {
        String result = JiraMarkup.toHtml("This & that");
        assertThat(result).isEqualTo("This &amp; that");
    }
}
