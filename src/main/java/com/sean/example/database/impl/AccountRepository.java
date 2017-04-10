package com.sean.example.database.impl;

import com.j256.ormlite.dao.Dao;
import com.sean.example.database.DatabaseManager;
import com.sean.example.database.Repository;
import com.sean.example.model.Account;

import java.sql.SQLException;
import java.util.Optional;

/**
 * Created by sean on 09/04/2017.
 */
public final class AccountRepository extends Repository<Account> {

    /**
     * @see Repository
     */
    public AccountRepository(DatabaseManager databaseManager) {
        super(databaseManager);
    }

    @Override
    public Optional<Account> findById(String id) throws SQLException {
        throw new IllegalArgumentException(String.format(Repository.ERROR_REPOSITORY_METHOD_NOT_SUPPORTED, getClass()));
    }

    @Override
    public Dao<Account, Integer> dao() {
        return databaseManager.getAccountDao();
    }

}
