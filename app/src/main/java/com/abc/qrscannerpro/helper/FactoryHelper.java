package com.abc.qrscannerpro.helper;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.abc.qrscannerpro.db.DatabaseHelper;


public class FactoryHelper {
    private static DatabaseHelper databaseHelper;

    public static DatabaseHelper getHelper(){
        return databaseHelper;
    }
    public static void setHelper(Context context){
        databaseHelper = OpenHelperManager.getHelper(context, DatabaseHelper.class);
    }
    public static void releaseHelper(){
        OpenHelperManager.releaseHelper();
        databaseHelper = null;
    }
}
