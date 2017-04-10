package com.sean.example.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.sean.example.database.DatabaseConstants;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Created by sean on 4/8/2017.
 */
@DatabaseTable(tableName= DatabaseConstants.GLYPH_TABLET_NAME)
public class Glyph {

    /**
     * The id of the platform.
     */
    @DatabaseField(generatedId = true, allowGeneratedIdInsert = true)
    private int id;

    /**
     * The glyph key.
     */
    @DatabaseField(columnName = DatabaseConstants.GLYPH_COLUMN_NAME)
    private String glyph;

    /**
     * The {@link Account} who owns the glyph.
     */
    @DatabaseField(columnName = DatabaseConstants.ACCOUNT_ID_NAME_COLUMN, foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = true)
    private Account account;

    /**
     * The platform for the specific glyph.
     */
    @DatabaseField(columnName = DatabaseConstants.PLATFORM_ID_NAME_COLUMN, foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, canBeNull = false)
    private Platform platform;

    /**
     * The guild the glyph belongs to.
     */
    @DatabaseField(columnName = DatabaseConstants.GUILD_ID_COLUMN, foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true ,canBeNull = false)
    private Guild guild;

    /**
     * Creates a new {@link Glyph}.
     * @param glyph The glyph key.
     * @param platform The platform for the specific glyph.
     * @param guild The guild the glyph belongs to.
     */
    public Glyph(String glyph, Platform platform, Guild guild) {
        this.glyph = glyph;
        this.platform = platform;
        this.guild = guild;
    }

    /**
     * Default constructor for ormlite.
     */
    Glyph() {

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
        builder.append(String.format("Glyph=[%s], ", glyph));
        if(platform != null) {
            builder.append(String.format("Platform=[%s], ", platform.getPlatformType().name()));
        }
        if(account != null) {
            builder.append(String.format("UserId=[%s], ", account.getUserId()));
        }
        if(guild != null) {
            builder.append(String.format("Guild=[%s], ", guild.getName()));
        }
        return builder.toString();
    }


    /**
     * Gets the id of the glyph.
     * @return The {@code id}.
     */
    public int getId() {
        return id;
    }

    /**
     * Srts the id.
     * @param id The id to be set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the glyph.
     * @return The {@code glyph}.
     */
    public String getGlyph() {
        return glyph;
    }

    /**
     * Sets the glyph code.
     * @param glyph The code of the glyph to be set.
     */
    public void setGlyph(String glyph) {
        this.glyph = glyph;
    }

    /**
     * Gets the platform of the glyph.
     * @return The {@code platform}.
     */
    public Platform getPlatform() {
        return platform;
    }

    /**
     * Sets the platform of the glyph.
     * @param platform The platform of the glyph to be set.
     */
    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    /**
     * Gets the guild of the glyph.
     * @return The {@code glyph}.
     */
    public Guild getGuild() {
        return guild;
    }

    /**
     * Sets the guild that the glyph belongs to.
     * @param guild The guild to be set.
     */
    public void setGuild(Guild guild) {
        this.guild = guild;
    }

    /**
     * Gets the {@link Account} owner of the glyph
     * @return The {@code account}.
     */
    public Account getAccount() {
        return account;
    }

    /**
     * Sets the {@link Account} of the glyph.
     * @param account The {@link Account} to set as the owner of the glyph.
     */
    public void setAccount(Account account) {
        this.account = account;
    }
}
