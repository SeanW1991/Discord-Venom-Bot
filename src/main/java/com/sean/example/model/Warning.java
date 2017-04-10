package com.sean.example.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.sean.example.database.DatabaseConstants;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.sql.Date;
import java.time.LocalDateTime;

/**
 * Created by sean on 4/8/2017.
 */
@DatabaseTable(tableName= DatabaseConstants.WARNINGS_TABLET_NAME)
public class Warning {

    /**
     * The id of the warning.
     */
    @DatabaseField(generatedId = true, allowGeneratedIdInsert = true)
    private int id;

    /**
     * The description of why the warning was given.
     */
    @DatabaseField(columnName = DatabaseConstants.MESSAGE_NAME_COLUMN, canBeNull = false)
    private String message;

    /**
     * The date and time of when the warning was given.
     */
    @DatabaseField(columnName = DatabaseConstants.DATE_TIME_COLUMN_NAME, canBeNull = false)
    private Date dateTimeOfWarning;

    /**
     * The account the warning was given to.
     */
    @DatabaseField(columnName = DatabaseConstants.ACCOUNT_ID_NAME_COLUMN, foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = false)
    private Account account;

    /**
     * The guild the account is in when the warning was given,
     */
    @DatabaseField(columnName = DatabaseConstants.GUILD_ID_COLUMN, foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = false)
    private Guild guild;

    /**
     * Creates a new {@link Warning}.
     * @param message The message describing the why the warning was given.
     * @param dateTimeOfWarning The date and time the warning was given.
     * @param account The account the warning was given for.
     * @param guild The guild the warning was given in.
     */
    public Warning(String message, Date dateTimeOfWarning, Account account, Guild guild) {
        this.message = message;
        this.dateTimeOfWarning = dateTimeOfWarning;
        this.account = account;
        this.guild = guild;
    }

    /**
     * Default constructor for ormlite.
     */
    Warning() {

    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("Id=[%d], ", id));
        builder.append(String.format("Date=[%s], ", dateTimeOfWarning));
        builder.append(String.format("Warning_Message=[%s], ", message));
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
     * Gets the id.
     * @return The {@code id}.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id.
     * @param id The id to be set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the warning message.
     * @return The {@code message}.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the warning message.
     * @param message The message to be set.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Gets the date and time of when the warning was given.
     * @return The {@code dateTimeOfWarning}.
     */
    public Date getDateTimeOfWarning() {
        return dateTimeOfWarning;
    }

    /**
     * Sets the date and time of the warning.
     * @param dateTimeOfWarning The {@link LocalDateTime} to be set.
     */
    public void setDateTimeOfWarning(Date dateTimeOfWarning) {
        this.dateTimeOfWarning = dateTimeOfWarning;
    }

    /**
     * Gets the {@link Account} the warning was given to.
     * @return The {@code account}.
     */
    public Account getAccount() {
        return account;
    }

    /**
     * Sets the {@link Account} of the warning.
     * @param account The {@link Account} to set.
     */
    public void setAccount(Account account) {
        this.account = account;
    }

    /**
     * Gets the {@link Guild}.
     * @return The {@code guild}.
     */
    public Guild getGuild() {
        return guild;
    }

    /**
     * Sets the {@link Guild}.
     * @param guild The {@code guild} to set.
     */
    public void setGuild(Guild guild) {
        this.guild = guild;
    }
}
