package com.abc.qrscannerpro.utils;

import android.graphics.Rect;

public interface IViewFinder {

    void setLaserColor(int laserColor);
    void setMaskColor(int maskColor);
    void setBorderColor(int borderColor);
    void setBorderStrokeWidth(int borderStrokeWidth);
    void setBorderLineLength(int borderLineLength);
    void setLaserEnabled(boolean isLaserEnabled);

    void setBorderCornerRounded(boolean isBorderCornersRounded);
    void setBorderAlpha(float alpha);
    void setBorderCornerRadius(int borderCornersRadius);
    void setViewFinderOffset(int offset);
    void setSquareViewFinder(boolean isSquareViewFinder);

    void setupViewFinder();


    Rect getFramingRect();


    int getWidth();


    int getHeight();
}
