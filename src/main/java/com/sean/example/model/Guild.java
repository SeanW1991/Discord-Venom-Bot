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
@DatabaseTable(tableName= DatabaseConstants.GUILD_TABLE_NAME)
public class Guild {

    /**]
     * The id of the guild.
     */
    @DatabaseField(generatedId = true, allowGeneratedIdInsert = true)
    private int id;

    /**
     * The guild id assigned by discord.
     */
    @DatabaseField(columnName = DatabaseConstants.GUILD_ID_COLUMN, canBeNull = false)
    private String guildId;

    /**
     * The name of the guild.
     */
    @DatabaseField(columnName = DatabaseConstants.NAME_COLUMN, canBeNull = false)
    private String name;

    /**
     * The {@link ForeignCollection} of {@link AccountGuild}s.
     */
    @ForeignCollectionField
    private ForeignCollection<AccountGuild> guilds;

    /**
     * Creates a new {@link Guild}.
     * @param name The name of the guild.
     * @param guildId The assigned id from discord.
     * @param guilds The collection of guilds that are registered with an account.
     */
    public Guild(String name, String guildId, ForeignCollection<AccountGuild> guilds) {
        this.name = name;
        this.guilds = guilds;
        this.guildId = guildId;
    }

    /**
     * Default constructor for ormlite.
     */
    Guild() {

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
        builder.append(String.format("Name=[%s], ", name));
        builder.append(String.format("GuildId, ", guildId));
        return builder.toString();
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
     * @param id The id.
     */
    public void setId(int id) {
        this.id = id;
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
     * Gets the name of the department.
     * @return The {@code name}.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the guild name.
     * @param name The name to update.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the guild id.
     * @return The {@code guildId}.
     */
    public String getGuildId() {
        return guildId;
    }

    /**
     * Sets the guild id.
     * @param guildId The id of the guild to set.
     */
    public void setGuildId(String guildId) {
        this.guildId = guildId;
    }
}
