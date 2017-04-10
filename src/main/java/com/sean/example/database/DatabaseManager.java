package com.sean.example.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.sean.example.ApplicationContext;
import com.sean.example.model.*;

import java.io.IOException;
import java.sql.SQLException;

/**
 * @author Sean
 */
public final class DatabaseManager {

    /**
     * The {@link JdbcPooledConnectionSource} for the SQLLite database.
     */
    private final JdbcPooledConnectionSource connectionSource;

    /**
     * The {@link Dao} for  the {@link Account}.
     */
    private final Dao<Account, Integer> accountDao;

    /**
     * The {@link Dao} for  the {@link Guild}.
     */
    private final Dao<Guild, Integer> guildDao;

    /**
     * The {@link Dao} for  the {@link AccountGuild}.
     */
    private final Dao<AccountGuild, Integer> accountGuildDao;

    /**
     * The {@link Dao} for  the {@link Platform}.
     */
    private final Dao<Platform, Integer> platformDao;

    /**
     * The {@link Dao} for  the {@link Glyph}.
     */
    private final Dao<Glyph, Integer> glyphDao;

    /**
     * The {@link Dao} for  the {@link Ban}.
     */
    private final Dao<Ban, Integer> banDao;

    /**
     * The {@link Dao} for  the {@link Warning}.
     */
    private final Dao<Warning, Integer> warningDao;

    /**
     * Creates a new {@link DatabaseManager}.
     * @param applicationContext The {@link ApplicationContext}.
     * @throws SQLException The exception thrown if an sql type error occurs.
     */
    public DatabaseManager(ApplicationContext applicationContext) throws SQLException {
        JdbcPooledConnectionSource connectionSource = new JdbcPooledConnectionSource(applicationContext.getUrl(), applicationContext.getUsername(), applicationContext.getPassword());
        connectionSource.setCheckConnectionsEveryMillis(60 * 1000);
        connectionSource.setTestBeforeGet(true);

        this.accountDao = DaoManager.createDao(connectionSource, Account.class);
        this.guildDao = DaoManager.createDao(connectionSource, Guild.class);
        this.accountGuildDao = DaoManager.createDao(connectionSource, AccountGuild.class);
        this.platformDao = DaoManager.createDao(connectionSource, Platform.class);
        this.glyphDao = DaoManager.createDao(connectionSource, Glyph.class);
        this.banDao = DaoManager.createDao(connectionSource, Ban.class);
        this.warningDao = DaoManager.createDao(connectionSource, Warning.class);

        this.connectionSource = connectionSource;
    }

    /**
     * Gets the {@link Dao} for the {@link Platform}.
     * @return The {@code platform}.
     */
    public Dao<Platform, Integer> getPlatformDao() {
        return platformDao;
    }

    /**
     * Gets the {@link Dao} for the {@link Glyph}.
     * @return The {@code glyphDao}.
     */
    public Dao<Glyph, Integer> getGlyphDao() {
        return glyphDao;
    }

    /**
     * Gets the {@link Dao} for the {@link Ban}.
     * @return The {@code banDao}.
     */
    public Dao<Ban, Integer> getBanDao() {
        return banDao;
    }

    /**
     * Gets the {@link Dao} for the {@link Warning}.
     * @return The {@code warningDao}.
     */
    public Dao<Warning, Integer> getWarningDao() {
        return warningDao;
    }

    /**
     * Gets the {@link Dao} for the {@link Account}.
     * @return The {@code accountDao}.
     */
    public Dao<Account, Integer> getAccountDao() {
        return accountDao;
    }

    /**
     * Gets the {@link Guild} {@link Dao}
     * @return The {@code accountDao}.
     */
    public Dao<Guild, Integer> getGuildDao() {
        return guildDao;
    }

    /**
     * Gets the {@link Dao} for the {@link AccountGuild}.
     * @return The {@code accountGuildDao}.
     */
    public Dao<AccountGuild, Integer> getAccountGuildDao() {
        return accountGuildDao;
    }

    /**
     * Closes the {@link JdbcPooledConnectionSource}.
     * @throws IOException The exception thrown if an i/o error occurs.
     */
    public void disconnect() throws IOException {
        connectionSource.close();
    }

}
