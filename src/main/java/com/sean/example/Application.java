package com.sean.example;

import com.sean.example.database.DatabaseManager;
import com.sean.example.event.ReadyEventListener;
import com.sean.example.parser.impl.ApplicationContextParser;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventDispatcher;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.RateLimitException;

import java.io.IOException;
import java.nio.file.Path;
import java.sql.SQLException;

/**
 * @author Sean
 */
public final class Application {

    /**
     * Starts the application.
     * @param args The arguments of the application.
     */
    public static void main(String[] args) {
        try {
            ApplicationContext applicationContext = new ApplicationContextParser().parse();
            new Application(applicationContext).start();
        } catch (IOException | SQLException | DiscordException | RateLimitException e) {
            e.printStackTrace();
        }

    }

    /**
     * The {@link IDiscordClient}.
     */
    private final IDiscordClient discordClient;

    /**
     * The {@link DatabaseManager} for handling the sqlite database.
     */
    private final DatabaseManager databaseManager;

    /**
     * The {@link ApplicationContext} containing vital information for the application.
     */
    private final ApplicationContext applicationContext;

    /**
     * Creates a new {@link Application} with the {@link Path} of the directory of the sqlite database.
     * @param context The {@link ApplicationContext} containing information for the application.
     * @throws SQLException The exception thrown if an sql error occurs.
     */
    public Application(ApplicationContext context) throws SQLException, DiscordException {
        this.databaseManager = new DatabaseManager(context);
        this.applicationContext = context;
        this.discordClient = new ClientBuilder().withToken(context.getToken()).build();
    }

    /**
     * Starts the application.
     */
    public void start() throws SQLException, RateLimitException, DiscordException {

        /**
         * Login the discord api using the token.
         */
        this.discordClient.login();

        /**
         * The {@link EventDispatcher} for registering {@link sx.blah.discord.api.events.IListener}s.
         */
        EventDispatcher dispatcher = discordClient.getDispatcher();
        dispatcher.registerListener(new ReadyEventListener(this));

    }

    /**
     * Gets the {@link IDiscordClient}.
     * @return The {@code discordClient}.
     */
    public IDiscordClient getDiscordClient() {
        return discordClient;
    }

}
