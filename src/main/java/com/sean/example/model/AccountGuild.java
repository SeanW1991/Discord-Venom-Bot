package com.sean.example.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.sean.example.database.DatabaseConstants;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * @author Sean
 */
@DatabaseTable(tableName= DatabaseConstants.ACCOUNT_GUILD_TABLE_NAME)
public class AccountGuild {

    /**
     * The id of the account in the database.
     */
    @DatabaseField(generatedId = true, allowGeneratedIdInsert = true)
    private int id;

    /**
     * The account reference.
     */
    @DatabaseField(columnName = DatabaseConstants.ACCOUNT_ID_NAME_COLUMN, foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = false)
    private Account account;

    /**
     * The guild reference.
     */
    @DatabaseField(columnName = DatabaseConstants.GUILD_ID_COLUMN, foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true ,canBeNull = false)
    private Guild guild;

    /**
     * Creates a new {@link AccountGuild}.
     * @param account The account to be set.
     * @param guild The guild to be set.
     */
    public AccountGuild(Account account, Guild guild) {
        this.account = account;
        this.guild = guild;
    }

    /**
     * Default constructor for ormlite.
     */
    AccountGuild() {

    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("Id=[%d], ", id));
        if(account != null) {
            builder.append(String.format("UserId=[%s], ", account.getUserId()));
        }
        if(guild != null) {
            builder.append(String.format("Guild=[%s], ", guild.getName()));
        }
        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    /**
     * Gets the id of the account.
     * @return The {@code id}.
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the account.
     * @return The {@code account}.
     */
    public Account getAccount() {
        return account;
    }

    /**
     * Gets the guild of the account.
     * @return The {@code guild}.
     */
    public Guild getGuild() {
        return guild;
    }

    /**
     * Sets the id of the account.
     * @param id The id to be set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Sets the account.
     * @param account The account to be set.
     */
    public void setAccount(Account account) {
        this.account = account;
    }

    /**
     * Sets the guild of the account.
     * @param guild The account to be set.
     */
    public void setGuild(Guild guild) {
        this.guild = guild;
    }
}
