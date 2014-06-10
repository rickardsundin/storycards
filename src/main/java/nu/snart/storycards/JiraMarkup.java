package nu.snart.storycards;

import org.apache.commons.lang3.StringEscapeUtils;

public class JiraMarkup {
    /**
     * Convert some Jira markup to HTML.
     */
    public static String toHtml(String input) {
        String result =  StringEscapeUtils.escapeXml(input);
        result = handleQuote(result);
        result = handleBold(result);
    return result;
    }

    private static String handleQuote(String input) {
        return input.replaceAll("&rdquo;", "\"");
    }

    private static String handleBold(String input) {
        return input.replaceAll("\\*(.+)\\*", "<bold>$1</bold>");
    }
}
