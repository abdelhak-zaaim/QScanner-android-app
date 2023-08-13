package com.abc.qrscannerpro.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.abc.qrscannerpro.R;
import com.abc.qrscannerpro.constant.Constants;
import com.abc.qrscannerpro.helper.PaintHelper;


public class FragmentType extends FragmentBase implements Constants, View.OnClickListener{
    private View rootView;
    private int mWindowType = 0;
    private int mCodeType = 0;
    private String mTypeText = "";
    private ImageView mTextImg;
    private ImageView mBarcodeImg;
    private ImageView mWifiImg;
    private ImageView mGeoImg;
    private ImageView mWebLinkImg;
    private ImageView mContactImg;
    private static OnChangeType mOnChange;

    public interface OnChangeType {
        void onChange(int codeType, String codeTypeText);
    }

    public static Fragment newInstance(int codeType, int backType, OnChangeType onChange) {
        Fragment fragment = new FragmentType();
        Bundle bundle = new Bundle();
        bundle.putInt(CODE_PREVIEW_TYPE, codeType);
        bundle.putInt(CODE_PREVIEW_TYPE_BACK, backType);
        fragment.setArguments(bundle);
        mOnChange = onChange;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_type, container, false);
        setHasOptionsMenu(true);
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(CODE_PREVIEW_TYPE) && bundle.containsKey(CODE_PREVIEW_TYPE_BACK)) {
            mCodeType = bundle.getInt(CODE_PREVIEW_TYPE);
            mWindowType = bundle.getInt(CODE_PREVIEW_TYPE_BACK);
        }
        initView();
        setStyle();
        return rootView;
    }

    private void initView() {
        if (listenerActivity != null) {
            listenerActivity.setTitle(listenerActivity.getString(R.string.header_code_type));
        }

        (rootView.findViewById(R.id.code_type_text_layout)).setOnClickListener(this);
        (rootView.findViewById(R.id.code_type_barcode_layout)).setOnClickListener(this);;
        (rootView.findViewById(R.id.code_type_wifi_layout)).setOnClickListener(this);;
        (rootView.findViewById(R.id.code_type_geo_layout)).setOnClickListener(this);;
        (rootView.findViewById(R.id.code_type_web_link_layout)).setOnClickListener(this);;
        (rootView.findViewById(R.id.code_type_contact_layout)).setOnClickListener(this);;

        mTextImg = (ImageView) rootView.findViewById(R.id.code_type_text_cb_ic);
        mBarcodeImg = (ImageView) rootView.findViewById(R.id.code_type_barcode_cb_ic);
        mWifiImg = (ImageView) rootView.findViewById(R.id.code_type_wifi_cb_ic);
        mGeoImg = (ImageView) rootView.findViewById(R.id.code_type_geo_cb_ic);
        mWebLinkImg = (ImageView) rootView.findViewById(R.id.code_type_web_link_cb_ic);
        mContactImg = (ImageView) rootView.findViewById(R.id.code_type_contact_cb_ic);

        clearAllCheck();
        switch (mCodeType) {
            case CODE_PREVIEW_TYPE_TEXT:
                mTextImg.setVisibility(View.VISIBLE);
                break;
            case CODE_PREVIEW_TYPE_BARCODE:
                mBarcodeImg.setVisibility(View.VISIBLE);
                break;
            case CODE_PREVIEW_TYPE_WIFI:
                mWifiImg.setVisibility(View.VISIBLE);
                break;
            case CODE_PREVIEW_TYPE_GEO:
                mGeoImg.setVisibility(View.VISIBLE);
                break;
            case CODE_PREVIEW_TYPE_WEB_LINK:
                mWebLinkImg.setVisibility(View.VISIBLE);
                break;
            case CODE_PREVIEW_TYPE_CONTACT:
                mContactImg.setVisibility(View.VISIBLE);
                break;
        }

    }

    private void clearAllCheck() {
        mTextImg.setVisibility(View.GONE);
        mBarcodeImg.setVisibility(View.GONE);
        mWifiImg.setVisibility(View.GONE);
        mGeoImg.setVisibility(View.GONE);
        mWebLinkImg.setVisibility(View.GONE);
        mContactImg.setVisibility(View.GONE);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.code_type_text_layout:
                mCodeType = CODE_PREVIEW_TYPE_TEXT;
                saveSettings();
                break;
            case R.id.code_type_barcode_layout:
                mCodeType = CODE_PREVIEW_TYPE_BARCODE;
                saveSettings();
                break;
            case R.id.code_type_wifi_layout:
                mCodeType = CODE_PREVIEW_TYPE_WIFI;
                saveSettings();
                break;
            case R.id.code_type_geo_layout:
                mCodeType = CODE_PREVIEW_TYPE_GEO;
                saveSettings();
                break;
            case R.id.code_type_web_link_layout:
                mCodeType = CODE_PREVIEW_TYPE_WEB_LINK;
                saveSettings();
                break;
            case R.id.code_type_contact_layout:
                mCodeType = CODE_PREVIEW_TYPE_CONTACT;
                saveSettings();
                break;
            default:
                break;
        }
    }

    private void saveSettings() {
        switch (mCodeType) {
            case CODE_PREVIEW_TYPE_TEXT:
                mTypeText = listenerActivity.getString(R.string.code_type_text);
                break;
            case CODE_PREVIEW_TYPE_BARCODE:
                mTypeText = listenerActivity.getString(R.string.code_type_barcode);
                break;
            case CODE_PREVIEW_TYPE_WIFI:
                mTypeText = listenerActivity.getString(R.string.code_type_wifi);
                break;
            case CODE_PREVIEW_TYPE_GEO:
                mTypeText = listenerActivity.getString(R.string.code_type_geo);
                break;
            case CODE_PREVIEW_TYPE_WEB_LINK:
                mTypeText = listenerActivity.getString(R.string.code_type_web_link);
                break;
            case CODE_PREVIEW_TYPE_CONTACT:
                mTypeText = listenerActivity.getString(R.string.code_type_contact);
                break;
        }
        mOnChange.onChange(mCodeType, mTypeText);
        listenerActivity.onBackPressed();
    }

    public int getBackType() {
        return mWindowType;
    }

    private void setStyle() {
        PaintHelper.setIconsColors(listenerActivity, rootView, R.id.code_type_text_cb_ic, R.drawable.settings_check_ic);
        PaintHelper.setIconsColors(listenerActivity, rootView, R.id.code_type_barcode_cb_ic, R.drawable.settings_check_ic);
        PaintHelper.setIconsColors(listenerActivity, rootView, R.id.code_type_wifi_cb_ic, R.drawable.settings_check_ic);
        PaintHelper.setIconsColors(listenerActivity, rootView, R.id.code_type_geo_cb_ic, R.drawable.settings_check_ic);
        PaintHelper.setIconsColors(listenerActivity, rootView, R.id.code_type_web_link_cb_ic, R.drawable.settings_check_ic);
        PaintHelper.setIconsColors(listenerActivity, rootView, R.id.code_type_contact_cb_ic, R.drawable.settings_check_ic);

        PaintHelper.setIconsColors(listenerActivity, rootView, R.id.code_type_text_ic, R.drawable.code_type_text_ic);
        PaintHelper.setIconsColors(listenerActivity, rootView, R.id.code_type_barcode_ic, R.drawable.code_type_barcode_ic);
        PaintHelper.setIconsColors(listenerActivity, rootView, R.id.code_type_wifi_ic, R.drawable.code_type_wifi_ic);
        PaintHelper.setIconsColors(listenerActivity, rootView, R.id.code_type_geo_ic, R.drawable.code_type_geo_ic);
        PaintHelper.setIconsColors(listenerActivity, rootView, R.id.code_type_web_link_ic, R.drawable.code_type_web_link_ic);
        PaintHelper.setIconsColors(listenerActivity, rootView, R.id.code_type_contact_ic, R.drawable.code_type_contact_ic);
    }

}
