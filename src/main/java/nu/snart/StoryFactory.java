package nu.snart;

import org.json.JSONObject;

/**
 * Implement this interface to make sure that Story objects
 * are being generated from issues the way you want them to.
 */
public interface StoryFactory {
    /**
     * If you return an array of fields, only these fields will be requested when fetching issues from Jira.
     * Returning null or an empty array to request all fields.
     * @return an array of the issue fields you need
     */
    String[] fields();

    /**
     * Provides a mapping from json to a Story object.
     */
    Story createStoryFromJson(JSONObject json);
}
