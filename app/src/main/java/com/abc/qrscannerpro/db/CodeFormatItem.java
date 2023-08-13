package com.abc.qrscannerpro.db;

import com.j256.ormlite.field.DatabaseField;

public class CodeFormatItem {
    public final static String NAME_FIELD_ID = "id";
    public final static String NAME_FIELD_FORMAT_NUMBER = "format_number";
    public final static String NAME_FIELD_FORMAT_NAME = "format_name";
    public final static String NAME_FIELD_IS_ACTIVE = "is_active";

    @DatabaseField(generatedId = true, columnName = NAME_FIELD_ID)
    private int Id;


    @DatabaseField(columnName = NAME_FIELD_FORMAT_NUMBER)
    private int formatNumber;

    @DatabaseField(columnName = NAME_FIELD_FORMAT_NAME)
    private String formatName;

    @DatabaseField(columnName = NAME_FIELD_IS_ACTIVE)
    private boolean isActive;


    public CodeFormatItem() {
        this.formatNumber = 0;
        this.formatName = "";
        this.isActive = false;
    }

    public CodeFormatItem(int formatNumber, String formatName, boolean isActive) {
        this.formatNumber = formatNumber;
        this.formatName = formatName;
        this.isActive = isActive;
    }


    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getFormatName() {
        return formatName;
    }

    public void setFormatName(String formatName) {
        this.formatName = formatName;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getFormatNumber() {
        return formatNumber;
    }

    public void setFormatNumber(int formatNumber) {
        this.formatNumber = formatNumber;
    }
}
