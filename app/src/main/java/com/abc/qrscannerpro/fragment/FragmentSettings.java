package com.abc.qrscannerpro.fragment;

import android.graphics.PointF;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.abc.qrscannerpro.R;
import com.abc.qrscannerpro.converse.QRSizeSave;
import com.abc.qrscannerpro.constant.Constants;
import com.abc.qrscannerpro.constant.ConstantsColor;
import com.abc.qrscannerpro.helper.PaintHelper;
import com.abc.qrscannerpro.utils.SharedPreferencesUtils;
import com.kyleduo.switchbutton.SwitchButton;

public class FragmentSettings extends FragmentBase implements Constants, View.OnClickListener, ConstantsColor, QRSizeSave.OnClickListener {
    private View rootView;
    private SwitchButton switchButtonAutoFocus;
    private SwitchButton switchButtonSquare;
    private SwitchButton switchButtonAutoSave;

    public static Fragment newInstance() {
        return new FragmentSettings();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        SharedPreferencesUtils.initSharedReferences(listenerActivity);
        rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        setHasOptionsMenu(true);
        listenerActivity.visibleBackButton();
        initView();
        setStyle();
        return rootView;
    }

    private void initView() {
        if (listenerActivity != null) {
            listenerActivity.setTitle(listenerActivity.getString(R.string.menu_item_settings_label));
        }

        switchButtonAutoFocus = (SwitchButton) rootView.findViewById(R.id.sb_auto_focus);
        switchButtonSquare = (SwitchButton) rootView.findViewById(R.id.sb_square);
        switchButtonAutoSave = (SwitchButton) rootView.findViewById(R.id.sb_auto_save);
        setSBStyle(switchButtonAutoFocus);
        setSBStyle(switchButtonSquare);
        setSBStyle(switchButtonAutoSave);

        setSBStartPosition();
        setSBChengeListener();


        ((TextView) rootView.findViewById(R.id.settings_qr_size_desc)).setText(String.valueOf(SharedPreferencesUtils.getQRImgSize()) + "px");

        (rootView.findViewById(R.id.settings_auto_focus_layout)).setOnClickListener(this);
        (rootView.findViewById(R.id.settings_square_layout)).setOnClickListener(this);
        (rootView.findViewById(R.id.settings_auto_save_layout)).setOnClickListener(this);
        (rootView.findViewById(R.id.settings_qr_size_layout)).setOnClickListener(this);
        (rootView.findViewById(R.id.settings_code_formats_layout)).setOnClickListener(this);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.settings_auto_focus_layout:
                switchButtonAutoFocus.setChecked(!switchButtonAutoFocus.isChecked());
                break;
            case R.id.settings_square_layout:
                switchButtonSquare.setChecked(!switchButtonSquare.isChecked());
                break;
            case R.id.settings_auto_save_layout:
                switchButtonAutoSave.setChecked(!switchButtonAutoSave.isChecked());
                break;
            case R.id.settings_qr_size_layout:
                QRSizeSave.showSaveQRSizeDialog(listenerActivity, FragmentSettings.this);
                break;
            case R.id.settings_code_formats_layout:
                listenerActivity.showFragment(FragmentCode.newInstance(), ANIM_FORWARD);
                break;
            default:
                break;
        }
    }

    private void setStyle() {
        PaintHelper.setIconsColors(listenerActivity, rootView, R.id.settings_ic_auto_focus, R.drawable.settings_ic_auto_focus);
        PaintHelper.setIconsColors(listenerActivity, rootView, R.id.settings_ic_square, R.drawable.settings_ic_square);
        PaintHelper.setIconsColors(listenerActivity, rootView, R.id.settings_ic_auto_save, R.drawable.settings_ic_auto_save);
        PaintHelper.setIconsColors(listenerActivity, rootView, R.id.settings_ic_qr_size, R.drawable.settings_ic_qr_size);
        PaintHelper.setIconsColors(listenerActivity, rootView, R.id.settings_ic_code_formats, R.drawable.settings_ic_code_formats);
    }

    private void setSBStyle(SwitchButton switchButton) {
        float size = 18 * getResources().getDisplayMetrics().density;
        switchButton.setThumbSize(new PointF(size, size));
        switchButton.setThumbColorRes(R.color.color_switch_button);
        switchButton.setBackColorRes(SWITCH_BTN_ENABLE[SharedPreferencesUtils.getThemeNumber()]);
        switchButton.setThumbRadius(100);
        switchButton.setBackRadius(100);
    }

    private void setSBStartPosition() {
        if (!SharedPreferencesUtils.isAutoFocus()) {
            switchButtonAutoFocus.setChecked(false);
            switchButtonAutoFocus.setBackColorRes(SWITCH_BTN_DISABLE[SharedPreferencesUtils.getThemeNumber()]);
            disableAutoFocus();
        } else {
            switchButtonAutoFocus.setChecked(true);
            switchButtonAutoFocus.setBackColorRes(SWITCH_BTN_ENABLE[SharedPreferencesUtils.getThemeNumber()]);
            activeAutoFocus();
        }

        if (!SharedPreferencesUtils.isSqare()) {
            switchButtonSquare.setChecked(false);
            switchButtonSquare.setBackColorRes(SWITCH_BTN_DISABLE[SharedPreferencesUtils.getThemeNumber()]);
            disableSquare();
        } else {
            switchButtonSquare.setChecked(true);
            switchButtonSquare.setBackColorRes(SWITCH_BTN_ENABLE[SharedPreferencesUtils.getThemeNumber()]);
            activeSquare();
        }

        if (!SharedPreferencesUtils.isAutoSave()) {
            switchButtonAutoSave.setChecked(false);
            switchButtonAutoSave.setBackColorRes(SWITCH_BTN_DISABLE[SharedPreferencesUtils.getThemeNumber()]);
            disableAutoSave();
        } else {
            switchButtonAutoSave.setChecked(true);
            switchButtonAutoSave.setBackColorRes(SWITCH_BTN_ENABLE[SharedPreferencesUtils.getThemeNumber()]);
            activeAutoSave();
        }
    }

    private void setSBChengeListener() {
        switchButtonAutoFocus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    activeAutoFocus();
                } else {
                    disableAutoFocus();
                }

            }
        });
        switchButtonSquare.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    activeSquare();
                } else {
                    disableSquare();
                }

            }
        });
        switchButtonAutoSave.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    activeAutoSave();
                } else {
                    disableAutoSave();
                }

            }
        });
    }

    private void activeAutoFocus() {
        switchButtonAutoFocus.setBackColorRes(SWITCH_BTN_ENABLE[SharedPreferencesUtils.getThemeNumber()]);
        SharedPreferencesUtils.setIsAutoFocus(true);
    }

    private void disableAutoFocus() {
        switchButtonAutoFocus.setBackColorRes(SWITCH_BTN_DISABLE[SharedPreferencesUtils.getThemeNumber()]);
        SharedPreferencesUtils.setIsAutoFocus(false);
    }

    private void activeSquare() {
        switchButtonSquare.setBackColorRes(SWITCH_BTN_ENABLE[SharedPreferencesUtils.getThemeNumber()]);
        SharedPreferencesUtils.setIsSqare(true);
    }

    private void disableSquare() {
        switchButtonSquare.setBackColorRes(SWITCH_BTN_DISABLE[SharedPreferencesUtils.getThemeNumber()]);
        SharedPreferencesUtils.setIsSqare(false);
    }

    private void activeAutoSave() {
        switchButtonAutoSave.setBackColorRes(SWITCH_BTN_ENABLE[SharedPreferencesUtils.getThemeNumber()]);
        SharedPreferencesUtils.setIsAutoSave(true);
    }

    private void disableAutoSave() {
        switchButtonAutoSave.setBackColorRes(SWITCH_BTN_DISABLE[SharedPreferencesUtils.getThemeNumber()]);
        SharedPreferencesUtils.setIsAutoSave(false);
    }


    @Override
    public void onClick(int size) {
        SharedPreferencesUtils.setQRImgSize(size);
        ((TextView) rootView.findViewById(R.id.settings_qr_size_desc)).setText(String.valueOf(size) + "px");
    }
}
