package com.sean.example.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.sean.example.database.DatabaseConstants;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Created by sean on 4/8/2017.
 */
@DatabaseTable(tableName= DatabaseConstants.PLATFORM_TABLET_NAME)
public class Platform {

    /**
     * The id of the platform.
     */
    @DatabaseField(generatedId = true, allowGeneratedIdInsert = true)
    private int id;

    /**
     * The type of platform.
     */
    @DatabaseField(columnName = DatabaseConstants.PLATFORM_COLUMN_NAME, dataType = DataType.ENUM_STRING)
    private PlatformType platformType;

    /**
     * Creates a new {@link Platform} with its own {@link PlatformType}.
     * @param platformType The type of platform.
     */
    public Platform(PlatformType platformType) {
        this.platformType = platformType;
    }

    /**
     * The default constructor for ormlite.
     */
    Platform() {

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
        builder.append(String.format("Platform=[%s], ", platformType.name()));
        return builder.toString();
    }

    /**
     * Gets the id of the platform.
     * @return The {@code id}.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id of the platform.
     * @param id The id to be set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the {@link PlatformType}.
     * @return The {@code platformType}.
     */
    public PlatformType getPlatformType() {
        return platformType;
    }

    /**
     * Sets the {@link PlatformType}
     * @param platformType The type of platform to be set.
     */
    public void setPlatformType(PlatformType platformType) {
        this.platformType = platformType;
    }
}
