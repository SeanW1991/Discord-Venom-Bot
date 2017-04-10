package com.sean.example;

import com.sean.example.parser.Definition;

/**
 * @author Sean
 */
public final class ApplicationContext implements Definition {

    /**
     * The discord token.
     */
    private final String token;

    /**
     * The username of the database.
     */
    private final String username;

    /**
     * The password of the database.
     */
    private final String password;

    /**
     * The url of the password.
     */
    private final String url;

    /**
     * Creates a new {@link ApplicationContext}.
     * @param token The discord token.
     * @param username The username of the database.
     * @param password The password of the database.
     * @param url The url of the database.
     */
    public ApplicationContext(String token, String username, String password, String url) {
        this.token = token;
        this.username = username;
        this.password = password;
        this.url = url;
    }

    /**
     * Gets the token.
     * @return The {@code token}.
     */
    public String getToken() {
        return token;
    }

    /**
     * Gets the username of the database.
     * @return The {@code username}.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the password of the database.
     * @return The {@code password}.
     */
    public String getPassword() {
        return password;
    }

    /**
     * The url of the of the database.
     * @return The {@code url}.
     */
    public String getUrl() {
        return url;
    }
}
