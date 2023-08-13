package com.abc.qrscannerpro.helper;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.abc.qrscannerpro.constant.ConstantsColor;
import com.abc.qrscannerpro.utils.SharedPreferencesUtils;

public class PaintHelper implements ConstantsColor {

    public static void setIconsColors(Context context, View view, int imgId, int resIconId) {
        ImageView img = (ImageView) view.findViewById(imgId);
        if (img != null) {
            Drawable itemIcon = context.getResources().getDrawable(resIconId);
            if (itemIcon != null) {
                itemIcon.setColorFilter(context.getResources().getColor(COLOR_ICONS[SharedPreferencesUtils.getThemeNumber()]), PorterDuff.Mode.SRC_ATOP);
            }
            img.setImageDrawable(itemIcon);
        }
    }

    public static Drawable recolorIcon(Context context, int resIconId, int colorId) {
        Drawable itemIcon = context.getResources().getDrawable(resIconId);
        if (itemIcon != null) {
            itemIcon.setColorFilter(context.getResources().getColor(colorId), PorterDuff.Mode.SRC_ATOP);
        }
        return itemIcon;

    }
}
