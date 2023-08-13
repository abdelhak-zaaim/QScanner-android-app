package com.abc.qrscannerpro.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferencesUtils {

    private static final String APP_SHARED_PREFS = SharedPreferencesUtils.class.getSimpleName();
    private static final String THEME_NUMBER = "THEME_NUMBER";
    private static final String IS_AUTO_FOCUS = "IS_AUTO_FOCUS";
    private static final String IS_SQUARE = "IS_SQUARE";
    private static final String MAIN_SCREEN_TYPE = "MAIN_SCREEN_TYPE";
    private static final String IS_AUTO_SAVE = "IS_AUTO_SAVE";
    private static final String QR_IMG_SIZE = "QR_IMG_SIZE";

    private static SharedPreferences sPref;

    public static void initSharedReferences(Context context) {
        if (sPref == null) {
            sPref = context.getSharedPreferences(APP_SHARED_PREFS, Activity.MODE_PRIVATE);
        }
    }
    public static int getThemeNumber() {
        return sPref.getInt(THEME_NUMBER, 0);
    }

    public static boolean isAutoFocus() {
        return sPref.getBoolean(IS_AUTO_FOCUS, true);
    }

    public static void setIsAutoFocus(boolean number) {
        Editor ed = sPref.edit();
        ed.putBoolean(IS_AUTO_FOCUS, number);
        ed.commit();
    }

    public static boolean isSqare() {
        return sPref.getBoolean(IS_SQUARE, false);
    }

    public static void setIsSqare(boolean number) {
        Editor ed = sPref.edit();
        ed.putBoolean(IS_SQUARE, number);
        ed.commit();
    }

    public static int getMainScreenType() {
        return sPref.getInt(MAIN_SCREEN_TYPE, 0);
    }

    public static void setMainScreenType(int number) {
        Editor ed = sPref.edit();
        ed.putInt(MAIN_SCREEN_TYPE, number);
        ed.commit();
    }

    public static boolean isAutoSave() {
        return sPref.getBoolean(IS_AUTO_SAVE, false);
    }

    public static void setIsAutoSave(boolean number) {
        Editor ed = sPref.edit();
        ed.putBoolean(IS_AUTO_SAVE, number);
        ed.commit();
    }

    public static int getQRImgSize() {
        return sPref.getInt(QR_IMG_SIZE, 600);
    }

    public static void setQRImgSize(int number) {
        Editor ed = sPref.edit();
        ed.putInt(QR_IMG_SIZE, number);
        ed.commit();
    }


}
