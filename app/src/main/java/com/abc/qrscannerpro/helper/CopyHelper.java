package com.abc.qrscannerpro.helper;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Build;

public class CopyHelper {
    @SuppressLint("NewApi")
    @SuppressWarnings("deprecation")
    public static boolean copyToClipboard(Context context, String text) {
        try {
            int sdk = Build.VERSION.SDK_INT;
            if (sdk < Build.VERSION_CODES.HONEYCOMB) {
                android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context
                        .getSystemService(context.CLIPBOARD_SERVICE);
                clipboard.setText(text);
            } else {
                ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText(context.getPackageName(), text);
                clipboard.setPrimaryClip(clip);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
