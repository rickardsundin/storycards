package nu.snart;

public class JiraMarkup {
    /**
     * Convert some Jira markup to HTML.
     */
    static String toHtml(String input) {
        return input.replaceAll("\\*(.+)\\*", "<bold>$1</bold>");
    }

}
