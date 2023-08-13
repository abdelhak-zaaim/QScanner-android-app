package com.abc.qrscannerpro.constant;


public interface Constants {
    //Privacy Policy
    String PRIVACY_POLICY = "http://pharidali.com.np/privacy-policy/"; //Policy Url

    //Ads 1
    String Image1 = "https://i.imgur.com/LTCo1fi.png"; //Image Url
    String AdsPACKAGE1 = "com.abc.qhospitalmap"; // Ads Package Id

    //Ads 2
    String Image2 = "https://i.imgur.com/dPpomH9.png"; //Image Url
    String AdsPACKAGE2 = "com.abc.callvoicerecorder"; // Ads Package Id

    //Ads 3
    String Image3 = "https://i.imgur.com/BTrM0ua.png"; //Image Url
    String AdsPACKAGE3 = "com.abc.filmy"; // Ads Package Id


    String IS_AUTO_FOCUS = "IS_AUTO_FOCUS";
    String IS_SQUARE = "IS_SQUARE";

    String IMG_PATH = "IMG_PATH";

    int MAIN_APP_HOME = 0;
    int MAIN_SCREEN_QR = 1;
    int MAIN_SCREEN_BARCODE = 2;

    String CODE_PREVIEW_TYPE = "CODE_PREVIEW_TYPE";
    int CODE_PREVIEW_TYPE_TEXT = 0;
    int CODE_PREVIEW_TYPE_BARCODE = 1;
    int CODE_PREVIEW_TYPE_WIFI = 2;
    int CODE_PREVIEW_TYPE_GEO = 3;
    int CODE_PREVIEW_TYPE_WEB_LINK = 4;
    int CODE_PREVIEW_TYPE_CONTACT = 5;

    String CODE_PREVIEW_TYPE_BACK = "CODE_PREVIEW_TYPE_BACK";
    int CODE_PREVIEW_TYPE_BACK_SAVE = 0;
    int CODE_PREVIEW_TYPE_BACK_EDIT = 1;
    int CODE_PREVIEW_TYPE_BACK_FILE = 2;

    String CODE_ID = "CODE_ID";

    int ANIM_FORWARD = 0;
    int ANIM_BACKWARD = 1;

    String CODE_PREVIEW_LAST_FRAGMENT = "CODE_PREVIEW_LAST_FRAGMENT";
    int CODE_PREVIEW_LAST_FRAGMENT_MAIN = 0;
    int CODE_PREVIEW_LAST_FRAGMENT_DELETE = 1;
}
