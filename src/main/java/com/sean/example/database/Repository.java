package com.sean.example.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;
import io.reactivex.Flowable;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * @author Sean
 */
public abstract class Repository<T> {

    /**
     * The error when a method is not supported within a repository.
     */
    public static final String ERROR_REPOSITORY_METHOD_NOT_SUPPORTED = "Method in %s not supported.";

    /**
     * The error when a name does not exist.
     */
    private static final String NAME_DOES_NOT_EXIST = "String Id [%s] does not exist.";

    /**
     * The error when the id does not exist.
     */
    private static final String ID_DOES_NOT_EXIST = "Id [%d] does not exist.";

    /**
     * The {@link DatabaseManager}.
     */
    protected final DatabaseManager databaseManager;

    /**
     * Creates a new {@link Repository}.
     * @param databaseManager The {@link DatabaseManager}.
     */
    protected Repository(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    /**
     * Finds a object type based
     * @param id The id of the object in the com.sean.example.database.
     * @return The {@link Optional} either containing a {@link T} or
     * an {@link Optional#EMPTY}.
     */
    public Optional<T> findById(int id) throws SQLException {
        return Optional.ofNullable(dao().queryForFirst(queryById(new SelectArg(DatabaseConstants.ID_TABLE_NAME, id))));
    }

    /**
     * Finds a object type based
     * @param id The {@link String} id of the object in the com.sean.example.database.
     * @return The {@link Optional} either containing a {@link T} or
     * an {@link Optional#EMPTY}.
     */
    public abstract Optional<T> findById(String id) throws SQLException;

    /**
     * Finds all the {@link T} within the com.sean.example.database.
     * @return A {@link Optional} with the {@link List} of {@link T} or
     * an {@link Optional#EMPTY}.
     */
    public Optional<List<T>> findAll() throws SQLException {
        return Optional.ofNullable(dao().queryForAll());
    }

    /**
     * The repository com.sean.example.database access object definition.
     * @return The dedicated {@link Dao} for a specific repository.
     */
    public abstract Dao<T, Integer> dao();

    /**
     * The {@link PreparedQuery} for selecting a {@link T} table by its id.
     * @param id The {@link SelectArg} for the id.
     * @return A new secure {@link PreparedQuery}.
     * @throws SQLException The sql exception when an error occurs.
     */
    private PreparedQuery<T> queryById(SelectArg id) throws SQLException {
        QueryBuilder<T, Integer> idQuery = dao().queryBuilder();
        idQuery.where().eq(DatabaseConstants.ID_TABLE_NAME, id);
        return idQuery.prepare();
    }

    /**
     * see {@link Dao#update(Object)}.
     */
    public int update(T obj) throws SQLException {
       return dao().update(obj);
    }

    /**
     * see {@link Dao#refresh(Object)}.
     */
    public int refresh(T obj) throws SQLException {
        return dao().refresh(obj);
    }

    /**
     * see {@link Dao#create(Object)}.
     */
    public int create(T obj) throws SQLException {
        return dao().create(obj);
    }

    /**
     * see {@link Dao#createOrUpdate(Object)}.
     */
    public Dao.CreateOrUpdateStatus createOrUpdate(T obj) throws SQLException {
        return dao().createOrUpdate(obj);
    }

    /**
     * Wraps the {@link Repository#update(Object)} as a {@link Flowable}.
     */
    public Flowable<Integer> updateAsFlowable(T obj) throws SQLException {
        return Flowable.fromCallable(() -> dao().update(obj));
    }

    /**
     * Wraps the {@link Repository#refresh(Object)} as a {@link Flowable}.
     */
    public Flowable<Integer> refreshAsFlowable(T obj) throws SQLException {
        return Flowable.fromCallable(() -> dao().refresh(obj));
    }

    /**
     * Wraps the {@link Repository#refresh(Object)} as a {@link Flowable}.
     */
    public Flowable<Integer> createAsFlowable(T obj) throws SQLException {
        return Flowable.fromCallable(() -> dao().create(obj));
    }

    /**
     * Wraps the {@link Repository#createOrUpdate(Object)} as a {@link Flowable}.
     */
    public Flowable<Dao.CreateOrUpdateStatus> createOrUpdateAsFlowable(T obj) throws SQLException {
        return Flowable.fromCallable(() -> dao().createOrUpdate(obj));
    }

    /**
     * Finds a {@link T} type by id and wraps it around a {@link Flowable}.
     * @param id The id to lookup.
     * @return An {@link Flowable} wrapping an {@link Optional}.
     */
    public Flowable<T> findByIdAsFlowable(int id) {
        return Flowable.fromCallable(() -> findById(id).orElseThrow(() -> new IllegalArgumentException(String.format(ID_DOES_NOT_EXIST, id))));
    }

    /**
     * Finds a {@link T} type by {@link String} id and wraps it around a {@link Flowable}.
     * @param id The name to lookup.
     * @return An {@link Flowable} wrapping an {@link Optional}.
     */
    public Flowable<T> findByIdAsFlowable(String id) {
        return Flowable.fromCallable(() -> findById(id).orElseThrow(() -> new IllegalArgumentException(String.format(NAME_DOES_NOT_EXIST, id))));
    }

}
