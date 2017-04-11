package com.sean.example.database.impl;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;
import com.sean.example.database.DatabaseConstants;
import com.sean.example.database.DatabaseManager;
import com.sean.example.database.Repository;
import com.sean.example.model.*;
import io.reactivex.Flowable;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;

/**
 * Created by sean on 10/04/2017.
 */
public final class GlyphRepository extends Repository<Glyph> {

    /**
     * The error when no glyphs for an assigned platform.
     */
    private static final String ERROR_NO_GLYPHS_FOR_PLATFORM = "No Glyphs by platform [%s].";

    /**
     * The error when no glyphs are assigned to a guild.
     */
    private static final String ERROR_NO_GLYPHS_IN_GUILD = "Sorry but there are no glyphs assigned to this guild yet.";

    /**
     * The error when no glyph are found based on their platform within a guild.
     */
    private static final String ERROR_NO_GLYPHS_BY_PLATFORM_IN_GUILD = "No Glyphs in Guild [%s] by platform [%s].";

    /**
     * The error when no available glyphs are remaining.
     */
    private static final String ERROR_NO_GLYPHS_AVAILABLE = "Sorry, there are no  remaining Glyphs available for the [%s] platform.";

    /**
     * @see Repository
     */
    public GlyphRepository(DatabaseManager databaseManager) {
        super(databaseManager);
    }

    @Override
    public Optional<Glyph> findById(String id) throws SQLException {
        throw new IllegalArgumentException(String.format(Repository.ERROR_REPOSITORY_METHOD_NOT_SUPPORTED, getClass()));
    }

    /**
     * Finds a {@link List} based on the {@link PlatformType} assigned to them.
     * @param type The {@link PlatformType} of the assigned {@link Glyph}s.
     * @return The {@link List} of {@link Glyph}s based on the {@link PlatformType}.
     * @throws SQLException The exception thrown if an sql error occurs.
     */
    public List<Glyph> findGlyphsByPlatform(PlatformType type) throws SQLException {
        List<Glyph> glyphs = dao().query(queryGlyphsByPlatform(type));
        if(glyphs.isEmpty()) {
            throw new IllegalArgumentException(String.format(ERROR_NO_GLYPHS_FOR_PLATFORM, type));
        }
        return glyphs;
    }

    /**
     * Finds a {@link List} of {@link Glyph}s based on their assigned {@link PlatformType} and {@link String} guild id.
     * @param type The {@link PlatformType}.
     * @param guildId The {@link String} guildId.
     * @return The {@link List} of {@link Glyph} based on their assigned {@link PlatformType} and guild id.
     * @throws SQLException The exception thrown if an sql error occurs.
     */
    public List<Glyph> findGlyphsByPlatformAndGuild(PlatformType type, String guildId) throws SQLException {
        List<Glyph> glyphs = dao().query(queryGlyphsByPlatformAndGuild(type, guildId));
        if(glyphs.isEmpty()) {
            throw new IllegalArgumentException(String.format(ERROR_NO_GLYPHS_BY_PLATFORM_IN_GUILD, guildId, type));
        }
        return glyphs;
    }

    /**
     * Finds a {@link List} of {@link Glyph}s based on their assigned {@link Guild}.
     * @param guildId The {@link String} id.
     * @return The {@link List} of {@link Glyph}s based on their assigned {@link Guild}.
     * @throws SQLException The exception thrown if an sql error occurs.
     */
    public List<Glyph> findGlyphsByGuildId(String guildId) throws SQLException {
        List<Glyph> guilds = dao().query(queryGlyphsByGuildId(guildId));
        if(guilds.isEmpty()) {
            throw new IllegalArgumentException(ERROR_NO_GLYPHS_IN_GUILD);
        }
        return guilds;
    }

    /**
     * Finds a {@link List} of available {@link Glyph}s for a specific {@link PlatformType} and {@link Guild}.
     * @param type The type of {@link Platform}.
     * @param guildId The {@link String} guild id.
     * @return The {@link List} of available {@link Glyph}s.
     * @throws SQLException The exception thrown if an sql error occurs.
     */
    public List<Glyph> findAvailableGlyphs(PlatformType type, String guildId) throws SQLException {
        List<Glyph> glyphs = dao().query(queryAvailableGlyphs(type, guildId));
        if(glyphs.isEmpty()) {
            throw new IllegalArgumentException(String.format(ERROR_NO_GLYPHS_AVAILABLE, type));
        }
        return glyphs;
    }

    /**
     * Finds a {@link Glyph} assigned to an {@link Account} within a {@link Guild}.
     * @param accountId The {@link String} account id.
     * @param guildId The {@link String} guild id.
     * @return The {@link List} of {@link Glyph}s owned by an {@link Account} within a {@link Guild}.
     * @throws SQLException The exception thrown if an sql error occurs.
     */
    public List<Glyph> findGlyphsByAccount(String accountId, String guildId) throws SQLException {
        List<Glyph> glyphs = dao().query(queryGlyphsByAccount(accountId, guildId));
        if(glyphs.isEmpty()) {
            throw new IllegalArgumentException();
        }
        return glyphs;
    }

    /**
     * Finds a {@link Glyph} assigned to an {@link Account} within a {@link Guild} as a {@link Flowable}.
     * @param accountId The {@link String} account id.
     * @param guildId The {@link String} guild id.
     * @return The {@link List} of {@link Glyph}s owned by an {@link Account} within a {@link Guild}.
     * @throws SQLException The exception thrown if an sql error occurs.
     */
    public Flowable<List<Glyph>> findGlyphsByAccountAsFlowable(String accountId, String guildId) throws SQLException {
        return Flowable.fromCallable(() -> findGlyphsByAccount(accountId, guildId));
    }

    /**
     * Finds a {@link List} of available {@link Glyph}s for a specific {@link PlatformType} and {@link Guild} as a {@link Flowable}.
     * @param type The type of {@link Platform}.
     * @param guildId The {@link String} guild id.
     * @return The {@link List} of available {@link Glyph}s as a {@link Flowable}.
     * @throws SQLException The exception thrown if an sql error occurs.
     */
    public Flowable<List<Glyph>> findAvailableGlyphsAsFlowable(PlatformType type, String guildId) {
        return Flowable.fromCallable(() -> findAvailableGlyphs(type, guildId));
    }

    /**
     * Finds a {@link List} of {@link Glyph}s based on their assigned {@link Guild} as a {@link Flowable}.
     * @param guildId The {@link String} id.
     * @return The {@link List} of {@link Glyph}s based on their assigned {@link Guild} as a {@link Flowable}.
     * @throws SQLException The exception thrown if an sql error occurs.
     */
    public Flowable<List<Glyph>> findGlyphsByGuildIdAsFlowable(String guildId) {
        return Flowable.fromCallable(() -> findGlyphsByGuildId(guildId));
    }

    /**
     * {@link List} of {@link Glyph}s based on their assigned {@link PlatformType} and {@link String} guild id as a {@link Flowable}
     * @param type The {@link PlatformType}.
     * @param guildId The {@link String} guildId.
     * @return The {@link List} of {@link Glyph} based on their assigned {@link PlatformType} and guild id as a {@link Flowable}.
     */
    public Flowable<List<Glyph>> findGlyphsByPlatformAndGuildAsFlowable(PlatformType type, String guildId) {
        return Flowable.fromCallable(() -> GlyphRepository.this.findGlyphsByPlatformAndGuild(type, guildId));
    }

    /**
     * Finds a {@link List} based on the {@link PlatformType} assigned to them as a {@link Flowable}.
     * @param type The {@link PlatformType} of the assigned {@link Glyph}s.
     * @return The {@link List} of {@link Glyph}s based on the {@link PlatformType} as a {@link Flowable}.
     * @throws SQLException The exception thrown if an sql error occurs.
     */
    public Flowable<List<Glyph>> findGlyphsByPlatformAsFlowable(PlatformType type) {
        return Flowable.fromCallable(() -> findGlyphsByPlatform(type));
    }

    @Override
    public Dao<Glyph, Integer> dao() {
        return databaseManager.getGlyphDao();
    }


    /**
     * The {@link PreparedQuery} to find all {@link Glyph}s based on the assigned {@link PlatformType}.
     * @param type The {@link PlatformType} of the {@link Glyph}s.
     * @return The {@link List} of {@link Glyph}s based on the {@link PlatformType}.
     * @throws SQLException The exception thrown if an sql error occurs.
     */
    private PreparedQuery<Glyph> queryGlyphsByPlatform(PlatformType type) throws SQLException {

        /**
         * The {@link SelectArg} for the platforms select query.
         */
        SelectArg platformTypeArgument = new SelectArg(DatabaseConstants.PLATFORM_COLUMN_NAME, type);

        /**
         * The {@link QueryBuilder} for the querying of the platform based on the {@link PlatformType}.
         */
        QueryBuilder<Platform, Integer> platformQuery = databaseManager.getPlatformDao().queryBuilder();
        platformQuery.where().eq(DatabaseConstants.PLATFORM_COLUMN_NAME, platformTypeArgument);

        /**
         * The {@link QueryBuilder} for finding all {@link Glyph}s based on the {@code platformQuery}.
         */
        QueryBuilder<Glyph, Integer> glyphQuery = databaseManager.getGlyphDao().queryBuilder();
        glyphQuery.join(platformQuery);

        return glyphQuery.prepare();
    }

    /**
     * The {@link PreparedQuery} for the querying of a {@link Glyph} based on the {@link Guild} and {@link Account}.
     * @param accountId The {@link String} id of the {@link Account}.
     * @param guildId The {@link String} id of the {@link Guild}.
     * @return The {@link PreparedQuery} for the querying of all {@link Glyph}s assigned to an {@link Account} from a {@link Guild}.
     * @throws SQLException The exception thrown if an sql error occurs.
     */
    private PreparedQuery<Glyph> queryGlyphsByAccount(String accountId, String guildId) throws SQLException {

        /**
         * The select arguments for the selecting of the guild and account.
         */
        SelectArg accountArgument = new SelectArg(DatabaseConstants.ACCOUNT_ID_NAME_COLUMN, accountId);
        SelectArg guildArgument = new SelectArg(DatabaseConstants.GUILD_ID_COLUMN, guildId);

        /**
         * The {@link QueryBuilder} for the guild querying.
         */
        QueryBuilder<Guild, Integer> guildQuery = databaseManager.getGuildDao().queryBuilder();
        guildQuery.where().eq(DatabaseConstants.GUILD_ID_COLUMN, guildArgument);

        /**
         * The {@link QueryBuilder} for the account querying.
         */
        QueryBuilder<Account, Integer> accountQuery = databaseManager.getAccountDao().queryBuilder();
        accountQuery.where().eq(DatabaseConstants.ACCOUNT_ID_NAME_COLUMN, accountId);

        /**
         * The {@link QueryBuilder} for glyph querying by joining the guild query and the account query.
         */
        QueryBuilder<Glyph, Integer> glyphQuery = dao().queryBuilder();
        glyphQuery.join(guildQuery).join(accountQuery);

        return glyphQuery.prepare();
    }

    /**
     * The {@link PreparedQuery} for the querying of any avilable {@link Glyph} based on their
     * assigned {@link PlatformType} and {@link Guild}.
     * @param type The {@link PlatformType}.
     * @param guildId The {@link String} guild id.
     * @return The {@link PreparedQuery} for the querying of any avilable {@link Glyph} based on
     * the {@link PlatformType} and {@link Guild}.
     * @throws SQLException The exception thrown when an sql error occurs.
     */
    private PreparedQuery<Glyph> queryAvailableGlyphs(PlatformType type, String guildId) throws SQLException {

        /**
         * The select arguments for the selecting of both the platform and the guild.
         */
        SelectArg platformTypeArgument = new SelectArg(DatabaseConstants.PLATFORM_COLUMN_NAME, type);
        SelectArg guildArgument = new SelectArg(DatabaseConstants.GUILD_ID_COLUMN, guildId);

        QueryBuilder<Guild, Integer> guildQuery = databaseManager.getGuildDao().queryBuilder();
        guildQuery.where().eq(DatabaseConstants.GUILD_ID_COLUMN, guildArgument);

        QueryBuilder<Platform, Integer> platformQuery = databaseManager.getPlatformDao().queryBuilder();
        platformQuery.where().eq(DatabaseConstants.PLATFORM_COLUMN_NAME, platformTypeArgument);

        QueryBuilder<Glyph, Integer> glyphQuery = dao().queryBuilder();

        glyphQuery.join(guildQuery).join(platformQuery).where().isNull(DatabaseConstants.ACCOUNT_ID_NAME_COLUMN);

        return glyphQuery.prepare();

    }

    /**
     * The {@link PreparedQuery} for the querying of the {@link Glyph}s based on their assigned {@link Guild}.
     * @param guildId The {@link String} id of the guild.
     * @return The {@link PreparedQuery} for the querying of {@link Glyph}s based on their assigned {@link Guild}.
     * @throws SQLException The exception thrown if an sql error occurs.
     */
    private PreparedQuery<Glyph> queryGlyphsByGuildId(String guildId) throws SQLException {

        /**
         * The guild select argument.
         */
        SelectArg guildIdArgument = new SelectArg(DatabaseConstants.GUILD_ID_COLUMN, guildId);

        /**
         * The {@link QueryBuilder} for the guild to select the column of a guild based on the guild id.
         */
        QueryBuilder<Guild, Integer> guildQuery = databaseManager.getGuildDao().queryBuilder();
        guildQuery.where().eq(DatabaseConstants.GUILD_ID_COLUMN, guildIdArgument);

        /**
         * The {@link QueryBuilder} for the selecting of a glyph based on the guild they are assigned to.
         */
        QueryBuilder<Glyph, Integer> glyphQuery = databaseManager.getGlyphDao().queryBuilder();
        glyphQuery.join(guildQuery);

        return glyphQuery.prepare();
    }

    /**
     * The {@link PreparedQuery} to find all {@link Glyph}s based on the assigned {@link PlatformType} and {@link Guild}.
     * @param type The {@link PlatformType} of the {@link Glyph}s.
     * @param guildId The guild {@link String} id.
     * @return The {@link List} of {@link Glyph}s based on the {@link PlatformType} and {@link Guild}.
     * @throws SQLException The exception thrown if an sql error occurs.
     */
    private PreparedQuery<Glyph> queryGlyphsByPlatformAndGuild(PlatformType type, String guildId) throws SQLException {

        /**
         * The {@link SelectArg} for the platforms and the guild querying.
         */
        SelectArg platformTypeArgument = new SelectArg(DatabaseConstants.PLATFORM_COLUMN_NAME, type);
        SelectArg guildArgument = new SelectArg(DatabaseConstants.GUILD_ID_COLUMN, guildId);

        /**
         * The {@link QueryBuilder} for the querying of the platform based on the {@link PlatformType}.
         */
        QueryBuilder<Guild, Integer> guildQuery = databaseManager.getGuildDao().queryBuilder();
        guildQuery.where().eq(DatabaseConstants.GUILD_ID_COLUMN, guildArgument);

        /**
         * The {@link QueryBuilder} for the querying of the platform based on the {@link PlatformType}.
         */
        QueryBuilder<Platform, Integer> platformQuery = databaseManager.getPlatformDao().queryBuilder();
        platformQuery.where().eq(DatabaseConstants.PLATFORM_COLUMN_NAME, platformTypeArgument);

        /**
         * The {@link QueryBuilder} for finding all {@link Glyph}s based on the {@code platformQuery} and the {@code guildQuery).
         */
        QueryBuilder<Glyph, Integer> glyphQuery = databaseManager.getGlyphDao().queryBuilder();
        glyphQuery.join(platformQuery).join(guildQuery);

        return glyphQuery.prepare();
    }
}
