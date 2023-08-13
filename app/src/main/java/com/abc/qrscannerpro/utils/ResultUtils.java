package com.abc.qrscannerpro.utils;

public class ResultUtils {
    private String mContents;
    private FormatUtils mBarcodeFormat;

    public void setContents(String contents) {
        mContents = contents;
    }

    public void setBarcodeFormat(FormatUtils format) {
        mBarcodeFormat = format;
    }

    public FormatUtils getBarcodeFormat() {
        return mBarcodeFormat;
    }

    public String getContents() {
        return mContents;
    }
}