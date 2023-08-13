package com.abc.qrscannerpro.helper;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.abc.qrscannerpro.R;
import com.abc.qrscannerpro.constant.Constants;
import com.abc.qrscannerpro.constant.ConstantsColor;
import com.abc.qrscannerpro.utils.SharedPreferencesUtils;

public class IconHelper implements Constants, ConstantsColor {

    public static Drawable getCodeTypeIcon(Context context, int codeType) {
        Drawable itemIcon = null;
        switch (codeType) {
            case CODE_PREVIEW_TYPE_TEXT:
                itemIcon = PaintHelper.recolorIcon(context, R.drawable.code_type_text_ic, COLOR_ICONS[SharedPreferencesUtils.getThemeNumber()]);
                break;
            case CODE_PREVIEW_TYPE_BARCODE:
                itemIcon = PaintHelper.recolorIcon(context, R.drawable.code_type_barcode_ic, COLOR_ICONS[SharedPreferencesUtils.getThemeNumber()]);
                break;
            case CODE_PREVIEW_TYPE_WIFI:
                itemIcon = PaintHelper.recolorIcon(context, R.drawable.code_type_wifi_ic, COLOR_ICONS[SharedPreferencesUtils.getThemeNumber()]);
                break;
            case CODE_PREVIEW_TYPE_GEO:
                itemIcon = PaintHelper.recolorIcon(context, R.drawable.code_type_geo_ic, COLOR_ICONS[SharedPreferencesUtils.getThemeNumber()]);
                break;
            case CODE_PREVIEW_TYPE_WEB_LINK:
                itemIcon = PaintHelper.recolorIcon(context, R.drawable.code_type_web_link_ic, COLOR_ICONS[SharedPreferencesUtils.getThemeNumber()]);
                break;
            case CODE_PREVIEW_TYPE_CONTACT:
                itemIcon = PaintHelper.recolorIcon(context, R.drawable.code_type_contact_ic, COLOR_ICONS[SharedPreferencesUtils.getThemeNumber()]);
                break;
        }

        return itemIcon;
    }

}
