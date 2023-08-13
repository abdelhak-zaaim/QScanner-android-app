package com.abc.qrscannerpro.db;

import com.j256.ormlite.field.DatabaseField;

public class QRCodeItem {
    public final static String NAME_FIELD_ID = "id";
    public final static String NAME_FIELD_RECORD_TYPE = "record_type";
    public final static String NAME_FIELD_RECORD_NAME = "record_name";
    public final static String NAME_FIELD_RECORD_DATE = "record_date";
    public final static String NAME_FIELD_RECORD_TIME = "record_time";
    public final static String NAME_FIELD_DESC = "desc";

    @DatabaseField(generatedId = true, columnName = NAME_FIELD_ID)
    private int Id;

    @DatabaseField(columnName = NAME_FIELD_RECORD_TYPE)
    private int recordType;

    @DatabaseField(columnName = NAME_FIELD_RECORD_NAME)
    private String recordName;

    @DatabaseField(columnName = NAME_FIELD_RECORD_DATE)
    private long recordDate;

    @DatabaseField(columnName = NAME_FIELD_RECORD_TIME)
    private String recordTime;

    @DatabaseField(columnName = NAME_FIELD_DESC)
    private String desc;


    public QRCodeItem() {
        this.recordType = 0;
        this.recordName = "";
        this.recordDate = 0L;
        this.recordTime = "";
        this.desc = "";
    }

    public QRCodeItem(int recordType, String recordName, long recordDate, String recordTime, String desc) {
        this.recordType = recordType;
        this.recordName = recordName;
        this.recordDate = recordDate;
        this.recordTime = recordTime;
        this.desc = desc;
    }


    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getRecordName() {
        return recordName;
    }

    public void setRecordName(String recordName) {
        this.recordName = recordName;
    }

    public long getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(long recordDate) {
        this.recordDate = recordDate;
    }

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getRecordType() {
        return recordType;
    }

    public void setRecordType(int recordType) {
        this.recordType = recordType;
    }
}
