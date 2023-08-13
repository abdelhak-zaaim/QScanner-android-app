package com.abc.qrscannerpro.utils;

import net.sourceforge.zbar.Symbol;

import java.util.List;
import java.util.ArrayList;

public class FormatUtils {
    private int mId;
    private String mName;

    public static final FormatUtils NONE = new FormatUtils(Symbol.NONE, "NONE");
    public static final FormatUtils PARTIAL = new FormatUtils(Symbol.PARTIAL, "PARTIAL");
    public static final FormatUtils EAN8 = new FormatUtils(Symbol.EAN8, "EAN8");
    public static final FormatUtils UPCE = new FormatUtils(Symbol.UPCE, "UPCE");
    public static final FormatUtils ISBN10 = new FormatUtils(Symbol.ISBN10, "ISBN10");
    public static final FormatUtils UPCA = new FormatUtils(Symbol.UPCA, "UPCA");
    public static final FormatUtils EAN13 = new FormatUtils(Symbol.EAN13, "EAN13");
    public static final FormatUtils ISBN13 = new FormatUtils(Symbol.ISBN13, "ISBN13");
    public static final FormatUtils I25 = new FormatUtils(Symbol.I25, "I25");
    public static final FormatUtils DATABAR = new FormatUtils(Symbol.DATABAR, "DATABAR");
    public static final FormatUtils DATABAR_EXP = new FormatUtils(Symbol.DATABAR_EXP, "DATABAR_EXP");
    public static final FormatUtils CODABAR = new FormatUtils(Symbol.CODABAR, "CODABAR");
    public static final FormatUtils CODE39 = new FormatUtils(Symbol.CODE39, "CODE39");
    public static final FormatUtils PDF417 = new FormatUtils(Symbol.PDF417, "PDF417");
    public static final FormatUtils QRCODE = new FormatUtils(Symbol.QRCODE, "QRCODE");
    public static final FormatUtils CODE93 = new FormatUtils(Symbol.CODE93, "CODE93");
    public static final FormatUtils CODE128 = new FormatUtils(Symbol.CODE128, "CODE128");

    public static final List<FormatUtils> ALL_FORMATS = new ArrayList<FormatUtils>();

    static {
        ALL_FORMATS.add(FormatUtils.PARTIAL);
        ALL_FORMATS.add(FormatUtils.EAN8);
        ALL_FORMATS.add(FormatUtils.UPCE);
        ALL_FORMATS.add(FormatUtils.ISBN10);
        ALL_FORMATS.add(FormatUtils.UPCA);
        ALL_FORMATS.add(FormatUtils.EAN13);
        ALL_FORMATS.add(FormatUtils.ISBN13);
        ALL_FORMATS.add(FormatUtils.I25);
        ALL_FORMATS.add(FormatUtils.DATABAR);
        ALL_FORMATS.add(FormatUtils.DATABAR_EXP);
        ALL_FORMATS.add(FormatUtils.CODABAR);
        ALL_FORMATS.add(FormatUtils.CODE39);
        ALL_FORMATS.add(FormatUtils.PDF417);
        ALL_FORMATS.add(FormatUtils.QRCODE);
        ALL_FORMATS.add(FormatUtils.CODE93);
        ALL_FORMATS.add(FormatUtils.CODE128);
    }

    public FormatUtils(int id, String name) {
        mId = id;
        mName = name;
    }

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public static FormatUtils getFormatById(int id) {
        for(FormatUtils format : ALL_FORMATS) {
            if(format.getId() == id) {
                return format;
            }
        }
        return FormatUtils.NONE;
    }
}