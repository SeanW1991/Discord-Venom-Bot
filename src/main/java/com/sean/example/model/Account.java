package com.sean.example.model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import com.sean.example.database.DatabaseConstants;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Created by sean on 03/04/2017.
 */
@DatabaseTable(tableName= DatabaseConstants.ACCOUNT_TABLE_NAME)
public class Account {

    /**
     * The id of the account in the database.
     */
    @DatabaseField(generatedId = true, allowGeneratedIdInsert = true)
    private int id;

    /**
     * The userId of the account.
     */
    @DatabaseField(columnName = DatabaseConstants.ACCOUNT_ID_NAME_COLUMN, canBeNull = false)
    private String userId;

    /**
     * The {@link ForeignCollection} of {@link AccountGuild}s.
     */
    @ForeignCollectionField
    private ForeignCollection<AccountGuild> guilds;

    /**
     * Creates a new {@link Account}.
     * @param userId The userId to be set.
     */
    public Account(String userId, ForeignCollection<AccountGuild> guilds) {
        this.userId = userId;
        this.guilds = guilds;
    }

    /**
     * Empty constructor for ormlite.
     */
    Account() {

    }

    @Override
    public boolean equals(Object o) {
       return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("Id=[%d], ", id));
        builder.append(String.format("UserId=[%s], ", userId));
        if(guilds != null) {
            builder.append(String.format("Registered_Guilds=[%d], ", guilds.size()));
        }
        return builder.toString();
    }

    /**
     * Gets the id of the account.
     * @return The {@code id}.
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the userId of the account.
     * @return The {@code userId}.
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Gets the {@link ForeignCollection} of {@link AccountGuild}s.
     * @return The {@code AccountGuild}.
     */
    public ForeignCollection<AccountGuild> getGuilds() {
        return guilds;
    }

    /**
     * Sets the {@link ForeignCollection} of {@link AccountGuild}s.
     * @return The {@code AccountGuild} to be set.
     */
    public void setGuilds(ForeignCollection<AccountGuild> guilds) {
        this.guilds = guilds;
    }

    /**
     * Sets the id of the account.
     * @param id The id to be set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Sets the userId of the account.
     * @param userId The userId to be set.
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

}
