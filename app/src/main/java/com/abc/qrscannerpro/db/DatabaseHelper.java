package com.abc.qrscannerpro.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.abc.qrscannerpro.R;
import com.abc.qrscannerpro.dao.CodeFormatDAO;
import com.abc.qrscannerpro.dao.QRCodeDAO;
import com.abc.qrscannerpro.helper.FactoryHelper;
import com.abc.qrscannerpro.constant.Constants;

import java.sql.SQLException;
import java.util.Calendar;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper implements Constants {
    private static final String TAG = DatabaseHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "database.db";
    private static final int DATABASE_VERSION = 1;
    private QRCodeDAO qrCodeDAO = null;
    private CodeFormatDAO codeFormatDAO = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, QRCodeItem.class);
            TableUtils.createTable(connectionSource, CodeFormatItem.class);
        } catch (SQLException e) {
            Log.e(TAG, "error creating DB " + DATABASE_NAME);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVer,
                          int newVer) {
        try {
            if (oldVer < 2) {
            }
        } catch (Exception e) {
            Log.e(TAG, "error upgrading db " + DATABASE_NAME + "from ver " + oldVer);
            throw new RuntimeException(e);
        }
    }

    public QRCodeDAO getQRCodeDAO() throws SQLException {
        if (qrCodeDAO == null) {
            qrCodeDAO = new QRCodeDAO(getConnectionSource(), QRCodeItem.class);
        }
        return qrCodeDAO;
    }

    public CodeFormatDAO getCodeFormatDAO() throws SQLException {
        if (codeFormatDAO == null) {
            codeFormatDAO = new CodeFormatDAO(getConnectionSource(), CodeFormatItem.class);
        }
        return codeFormatDAO;
    }

    @Override
    public void close() {
        super.close();
        qrCodeDAO = null;
        codeFormatDAO = null;
    }

    public void addQRCodeItemDB(boolean isAutoSave, Context context, int type, String callName, String desc) {
        final long date = Calendar.getInstance().getTime().getTime();
        Calendar timeC = Calendar.getInstance();
        String hours = String.valueOf(timeC.get(Calendar.HOUR_OF_DAY));
        if (hours.length() == 1)
            hours = "0" + hours;
        String minutes = String.valueOf(timeC.get(Calendar.MINUTE));
        if (minutes.length() == 1)
            minutes = "0" + minutes;
        String time = hours + ":" + minutes;
        try {
            QRCodeItem callRecordItem = new QRCodeItem(type, callName, date, time, desc);
            FactoryHelper.getHelper().getQRCodeDAO().create(callRecordItem);
            if (isAutoSave)
                Toast.makeText(context, context.getString(R.string.db_save),
                        Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addCodeFormatItemDB(Context context, int number, String name, boolean isActive) {
        try {
            CodeFormatItem callRecordItem = new CodeFormatItem(number, name, isActive);
            FactoryHelper.getHelper().getCodeFormatDAO().create(callRecordItem);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}