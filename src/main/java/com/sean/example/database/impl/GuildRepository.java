package com.sean.example.database.impl;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;
import com.sean.example.database.DatabaseConstants;
import com.sean.example.database.DatabaseManager;
import com.sean.example.database.Repository;
import com.sean.example.model.Account;
import com.sean.example.model.AccountGuild;
import com.sean.example.model.Guild;
import io.reactivex.Flowable;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Created by sean on 10/04/2017.
 */
public final class GuildRepository extends Repository<Guild> {

    /**
     * The error message displayed if an account does not contain any errors.
     */
    private static final String ERROR_GUILDS_NOT_EXIST = "Account [%s] does not contain any assigned Guilds";

    /**
     * @see Repository
     */
    public GuildRepository(DatabaseManager databaseManager) {
        super(databaseManager);
    }

    @Override
    public Optional<Guild> findById(String id) throws SQLException {
        return Optional.ofNullable(dao().queryForFirst(queryGuildById(id)));
    }

    /**
     * {@link List}s all the{@link Guild}s associated to an {@link Account}.
     * @param userId The {@link String} user id.
     * @return The {@link List} of {@link Guild}s associated to the{@link Account}.
     * @throws SQLException The exception thrown if an sql error occurs.
     */
    public List<Guild> findGuildsByAccountId(String userId) throws SQLException {
        List<Guild> guilds = dao().query(queryGuildsByAccount(userId));
        if(guilds.isEmpty()) {
            throw new IllegalArgumentException(String.format(ERROR_GUILDS_NOT_EXIST, userId));
        }
        return guilds;
    }

    /**
     * {@link List}s all the{@link Guild}s associated to an {@link Account} as a {@link Flowable}.
     * @param userId The {@link String} user id.
     * @return The {@link List} of {@link Guild}s associated to the{@link Account} as a {@link Flowable}.
     * @throws SQLException The exception thrown if an sql error occurs.
     */
    public Flowable<List<Guild>> findGuildsByAccountIdAsFlowable(String userId) throws SQLException {
        return Flowable.fromCallable(() -> findGuildsByAccountId(userId));
    }

    @Override
    public Dao<Guild, Integer> dao() {
        return databaseManager.getGuildDao();
    }

    /**
     * The {@link PreparedQuery} to retrieve all {@link Guild}s from an {@link Account}.
     * @param userId The {@link String} id of the {@link Account}.
     * @return The {@link PreparedQuery} for the retrieving of all {@link Guild}s from an {@link Account}.
     * @throws SQLException The exception thrown if an sql error occurs.
     */
    private PreparedQuery<Guild> queryGuildsByAccount(String userId) throws SQLException {
        SelectArg accountIdArgument = new SelectArg(DatabaseConstants.ACCOUNT_ID_NAME_COLUMN, userId);

        QueryBuilder<Account, Integer> accountQuery = databaseManager.getAccountDao().queryBuilder();
        accountQuery.where().eq(DatabaseConstants.ACCOUNT_ID_NAME_COLUMN, accountIdArgument);

        QueryBuilder<AccountGuild, Integer> accountGuildQuery = databaseManager.getAccountGuildDao().queryBuilder();
        accountGuildQuery.where().in(DatabaseConstants.ACCOUNT_ID_NAME_COLUMN, accountQuery);

        QueryBuilder<Guild, Integer> guildQuery = databaseManager.getGuildDao().queryBuilder();
        guildQuery.join(accountGuildQuery);

        return guildQuery.prepare();
    }

    /**
     * The {@link PreparedQuery} to find a {@link Guild} based on its {@link String} id.
     * @param guildId The {@link String} id of the {@link Guild}.
     * @return The {@link PreparedQuery} for querying a {@link Guild} by its {@link String} id.
     * @throws SQLException The exception thrown if an sql error occurs.
     */
    private PreparedQuery<Guild> queryGuildById(String guildId) throws SQLException {
        SelectArg guildIdArgument = new SelectArg(DatabaseConstants.GUILD_ID_COLUMN, guildId);

        QueryBuilder<Guild, Integer> guildQuery = databaseManager.getGuildDao().queryBuilder();
        guildQuery.where().eq(DatabaseConstants.GUILD_ID_COLUMN, guildIdArgument);

        return guildQuery.prepare();
    }
}
