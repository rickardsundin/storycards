package nu.snart.storycards;

import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import javax.ws.rs.client.Client;
import java.net.URI;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

import static javax.ws.rs.client.ClientBuilder.newClient;

public class HttpBasicAuthenticationRestClient implements RestClient {
    private final URI baseUri;
    private final String username;
    private final char[] password;

    /**
     * @param baseUri Uri that is used as a base for your communication (e.g. "https://some.service.com/")
     * @param username Username for authentication
     * @param password Password for authentication
     */
    public HttpBasicAuthenticationRestClient(URI baseUri, String username, char[] password) {
        this.baseUri = baseUri;
        this.username = username;
        this.password = password;
        ensureHttps();
    }

    private void ensureHttps() {
        ensureHttps(baseUri.getScheme());
    }

    private void ensureHttps(String scheme) {
        if (!scheme.equalsIgnoreCase("https")) {
            throw new IllegalArgumentException("Make sure that your URI starts with 'https'. This is important since HTTP Basic authentication is used.");
        }
    }

    @Override
    public String get(String... pathElements) {
        URI uri = baseUri;
        for (String path : pathElements) {
            uri = uri.resolve(path);
        }
        return get(uri);
    }

    private String get(URI uri) {
        return authenticatedClient().target(uri).request().get(String.class);
    }

    private Client authenticatedClient() {
        return newClient().register(authentication());
    }

    private HttpAuthenticationFeature authentication() {
        return HttpAuthenticationFeature.basic(username, asBytes(password, Charset.defaultCharset().name()));
    }

    private byte[] asBytes(char[] chars, String charsetName) {
        Charset charset = Charset.forName(charsetName);
        return charset.encode(CharBuffer.wrap(chars)).array();
    }
}