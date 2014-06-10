package nu.snart.storycards;

/**
 * A really simple REST client.
 */
public interface RestClient {
    /**
     * Get data from the uri that results from joining the provided path elements.
     * @param pathElements path elements
     * @return response entity as a String
     */
    String get(String... pathElements);
}