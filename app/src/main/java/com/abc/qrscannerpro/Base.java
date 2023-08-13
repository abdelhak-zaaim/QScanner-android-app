package com.abc.qrscannerpro;

import android.content.Context;
import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.abc.qrscannerpro.helper.FactoryHelper;
import com.abc.qrscannerpro.utils.SharedPreferencesUtils;
import com.onesignal.OneSignal;


public class Base extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .autoPromptLocation(true)

                .init();

        SharedPreferencesUtils.initSharedReferences(this);
        FactoryHelper.setHelper(getApplicationContext());
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
