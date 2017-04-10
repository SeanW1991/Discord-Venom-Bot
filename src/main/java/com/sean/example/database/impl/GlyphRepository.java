package com.sean.example.database.impl;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;
import com.sean.example.database.DatabaseConstants;
import com.sean.example.database.DatabaseManager;
import com.sean.example.database.Repository;
import com.sean.example.model.Glyph;
import com.sean.example.model.Guild;
import com.sean.example.model.Platform;
import com.sean.example.model.PlatformType;
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
            throw new IllegalArgumentException(String.format("No Glyphs by platform [%s].", type));
        }
        return glyphs;
    }

    /**
     * {@link List} of {@link Glyph}s based on their assigned {@link PlatformType} and {@link String} guild id.
     * @param type The {@link PlatformType}.
     * @param guildId The {@link String} guildId.
     * @return The {@link List} of {@link Glyph} based on their assigned {@link PlatformType} and guild id.
     * @throws SQLException The exception thrown if an sql error occurs.
     */
    public List<Glyph> findGlyphsByPlatformAndGuild(PlatformType type, String guildId) throws SQLException {
        List<Glyph> glyphs = dao().query(queryGlyphsByPlatformAndGuild(type, guildId));
        if(glyphs.isEmpty()) {
            throw new IllegalArgumentException(String.format("No Glyphs in Guild [%s] by platform [%s].", guildId, type));
        }
        return glyphs;
    }

    /**
     * {@link List} of {@link Glyph}s based on their assigned {@link PlatformType} and {@link String} guild id as a {@link Flowable}
     * @param type The {@link PlatformType}.
     * @param guildId The {@link String} guildId.
     * @return The {@link List} of {@link Glyph} based on their assigned {@link PlatformType} and guild id as a {@link Flowable}.
     */
    public Flowable<List<Glyph>> findGlyphsByPlatformAndGuildAsFlowable(PlatformType type, String guildId) {
        return Flowable.fromCallable(() -> findGlyphsByPlatformAndGuild(type, guildId));
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
    public PreparedQuery<Glyph> queryGlyphsByPlatform(PlatformType type) throws SQLException {

        /**
         * The {@link SelectArg} for the platforms select query.
         */
        SelectArg platformTypeArgument = new SelectArg(DatabaseConstants.PLATFORM_COLUMN_NAME, type);

        /**
         * The {@link QueryBuilder} for the querying of the platform based on the {@link PlatformType}.
         */
        QueryBuilder<Platform, Integer> platformQuery = databaseManager.getPlatformDao().queryBuilder();
        platformQuery.selectColumns(DatabaseConstants.ID_TABLE_NAME).where().eq(DatabaseConstants.PLATFORM_COLUMN_NAME, platformTypeArgument);

        /**
         * The {@link QueryBuilder} for finding all {@link Glyph}s based on the {@code platformQuery}.
         */
        QueryBuilder<Glyph, Integer> glyphQuery = databaseManager.getGlyphDao().queryBuilder();
        glyphQuery.where().in(DatabaseConstants.PLATFORM_ID_NAME_COLUMN, platformQuery);

        return glyphQuery.prepare();
    }

    /**
     * The {@link PreparedQuery} to find all {@link Glyph}s based on the assigned {@link PlatformType} and {@link Guild}.
     * @param type The {@link PlatformType} of the {@link Glyph}s.
     * @param guildId The guild {@link String} id.
     * @return The {@link List} of {@link Glyph}s based on the {@link PlatformType} and {@link Guild}.
     * @throws SQLException The exception thrown if an sql error occurs.
     */
    public PreparedQuery<Glyph> queryGlyphsByPlatformAndGuild(PlatformType type, String guildId) throws SQLException {

        /**
         * The {@link SelectArg} for the platforms and the guild querying.
         */
        SelectArg platformTypeArgument = new SelectArg(DatabaseConstants.PLATFORM_COLUMN_NAME, type);
        SelectArg guildArgument = new SelectArg(DatabaseConstants.GUILD_ID_COLUMN, guildId);

        /**
         * The {@link QueryBuilder} for the querying of the platform based on the {@link PlatformType}.
         */
        QueryBuilder<Guild, Integer> guildQuery = databaseManager.getGuildDao().queryBuilder();
        guildQuery.selectColumns(DatabaseConstants.ID_TABLE_NAME).where().eq(DatabaseConstants.GUILD_ID_COLUMN, guildArgument);

        /**
         * The {@link QueryBuilder} for the querying of the platform based on the {@link PlatformType}.
         */
        QueryBuilder<Platform, Integer> platformQuery = databaseManager.getPlatformDao().queryBuilder();
        platformQuery.selectColumns(DatabaseConstants.ID_TABLE_NAME).where().eq(DatabaseConstants.PLATFORM_COLUMN_NAME, platformTypeArgument);

        /**
         * The {@link QueryBuilder} for finding all {@link Glyph}s based on the {@code platformQuery} and the {@code guildQuery).
         */
        QueryBuilder<Glyph, Integer> glyphQuery = databaseManager.getGlyphDao().queryBuilder();
        glyphQuery.where().in(DatabaseConstants.PLATFORM_ID_NAME_COLUMN, platformQuery).and().in(DatabaseConstants.GUILD_ID_COLUMN, guildQuery);

        return glyphQuery.prepare();
    }
}
