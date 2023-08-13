package com.abc.qrscannerpro.fragment;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.abc.qrscannerpro.BuildConfig;
import com.abc.qrscannerpro.R;
import com.abc.qrscannerpro.MainActivity;
import com.abc.qrscannerpro.TOSActivity;
import com.abc.qrscannerpro.constant.Constants;
import com.abc.qrscannerpro.constant.ConstantsColor;
import com.abc.qrscannerpro.utils.SharedPreferencesUtils;
import com.afollestad.materialdialogs.MaterialDialog;

public class FragmentDrawer implements View.OnClickListener, Constants, ConstantsColor {

    private Context mContext;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private LinearLayout navigationViewHeader;
    private LinearLayout navigationViewContainer;
    public static OnClickPathListener mOnClick;

    public interface OnClickPathListener {
        void onClickPath();
    }

    public FragmentDrawer(Context context, DrawerLayout drawerLayout, OnClickPathListener onClick) {
        mContext = context;
        mDrawerLayout = drawerLayout;
        mOnClick = onClick;
        initView();
        setViewStyle();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_apphome:
                ((MainActivity) mContext).showFragment(FragmentMain.newInstance(SharedPreferencesUtils.isAutoFocus(), SharedPreferencesUtils.isSqare()));
                closePanel();
                break;
            case R.id.btn_qr_camera:
                ((MainActivity) mContext).showFragment(FragmentQRcode.newInstance());
                closePanel();
                break;
            case R.id.btn_barcode_camera:
                ((MainActivity) mContext).showFragment(FragmentBarcode.newInstance());
                closePanel();
                break;
            case R.id.btn_qr_file:
                mOnClick.onClickPath();
                closePanel();
                break;
            case R.id.btn_qr_generate:
                ((MainActivity) mContext).showFragment(FragmentGenerate.newInstance());
                closePanel();
                break;
            case R.id.btn_code_list:
                ((MainActivity) mContext).showFragment(FragmentList.newInstance());
                closePanel();
                break;
            case R.id.btn_settings:
                ((MainActivity) mContext).showFragment(FragmentSettings.newInstance());
                closePanel();
                break;
            case R.id.btn_share:
                Intent intentShare = new Intent(Intent.ACTION_SEND);
                intentShare.putExtra(Intent.EXTRA_TEXT, mContext.getString(R.string.share_text) + "market://details?id=" + mContext.getPackageName());
                intentShare.setType("text/plain");
                mContext.startActivity(Intent.createChooser(intentShare, mContext.getString(R.string.settings_share)));
                break;
            case R.id.btn_rate_us:
                Intent intentRateUs = new Intent(Intent.ACTION_VIEW);
                intentRateUs.setData(Uri.parse("market://details?id=" + mContext.getPackageName()));
                mContext.startActivity(intentRateUs);
                break;
            case R.id.btn_about_us:
                aboutMyApp();
                break;
            case R.id.btn_privacy_policy:
                mContext.startActivity(new Intent(mContext, TOSActivity.class));
                break;
            case R.id.btn_more_app:
                Uri uri = Uri.parse("market://search?q=pub:" + "PA Production");
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    mContext.startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    mContext.startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/search?q=pub:" + "PA Production")));
                }
                break;

        }
    }

    public void openPanel() {
        mDrawerLayout.openDrawer(mNavigationView);
    }

    public void closePanel() {
        if (mDrawerLayout != null && mNavigationView != null && mDrawerLayout.isDrawerOpen(mNavigationView))
            mDrawerLayout.closeDrawer(mNavigationView);
    }

    public boolean isPanelOpen() {
        return mDrawerLayout.isDrawerOpen(mNavigationView);
    }


    private void fastInitItem(int btnId, int imgId, int txtId) {
        LinearLayout btn = (LinearLayout) mNavigationView.findViewById(btnId);
        if (btn != null) {
            btn.setOnClickListener(this);
            btn.setBackgroundResource(R.drawable.btn_transparent_dark);
        }

        TextView txt = (TextView) mNavigationView.findViewById(txtId);
        if (txt != null)
            txt.setTextColor(mContext.getResources().getColor(R.color.nv_text_theme1));
    }


    private void setViewStyle() {
        if (navigationViewContainer != null)
            navigationViewContainer.setBackgroundColor(mContext.getResources().getColor(R.color.nv_bg_theme1));
        if (navigationViewHeader != null)

             fastInitItem(R.id.btn_apphome, R.id.img_apphome, R.id.txt_apphome);
        fastInitItem(R.id.btn_qr_camera, R.id.img_qr_camera, R.id.txt_qr_camera);
        fastInitItem(R.id.btn_barcode_camera, R.id.img_barcode_camera, R.id.txt_barcode_camera);
        fastInitItem(R.id.btn_qr_file, R.id.img_qr_file, R.id.txt_qr_file);
        fastInitItem(R.id.btn_qr_generate, R.id.img_qr_generate, R.id.txt_qr_file);
        fastInitItem(R.id.btn_code_list, R.id.img_code_list, R.id.txt_code_list);
        fastInitItem(R.id.btn_settings, R.id.img_settings, R.id.txt_settings);
        fastInitItem(R.id.btn_rate_us, R.id.img_rate_us, R.id.txt_rate_us);
        fastInitItem(R.id.btn_share, R.id.img_share, R.id.txt_rate_us);
        fastInitItem(R.id.btn_about_us, R.id.img_about_us, R.id.txt_about_us);
        fastInitItem(R.id.btn_privacy_policy, R.id.img_privacy_policy, R.id.txt_privacy_policy);
        fastInitItem(R.id.btn_more_app, R.id.img_more_app, R.id.txt_more_app);
    }

    private void initView() {
        mNavigationView = (NavigationView) mDrawerLayout.findViewById(R.id.navigation_view);
        if (mNavigationView != null) {
            navigationViewHeader = (LinearLayout) mNavigationView.findViewById(R.id.navigation_view_header);
            navigationViewContainer = (LinearLayout) mNavigationView.findViewById(R.id.navigation_view_container);
        }

    }


    private void aboutMyApp() {

        MaterialDialog.Builder bulder = new MaterialDialog.Builder(mContext)
                .title(R.string.app_name)
                .customView(R.layout.activity_about, true)
                .backgroundColor(mContext.getResources().getColor(R.color.colorPrimary))
                .titleColorRes(android.R.color.white)
                .positiveText(R.string.moreapps)
                .positiveColor(mContext.getResources().getColor(android.R.color.white))
                .icon(mContext.getResources().getDrawable(R.mipmap.ic_launcher))
                .limitIconToDefaultSize()
                .onPositive((dialog, which) -> {
                    Uri uri = Uri.parse("market://search?q=pub:" + "PA Production");
                    Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                    try {
                        mContext.startActivity(goToMarket);
                    } catch (ActivityNotFoundException e) {
                        mContext.startActivity(new Intent(Intent.ACTION_VIEW,
                                Uri.parse("http://play.google.com/store/search?q=pub:" + "PA Production")));
                    }
                });

        MaterialDialog materialDialog = bulder.build();

        TextView versionCode = (TextView) materialDialog.findViewById(R.id.version_code);
        TextView versionName = (TextView) materialDialog.findViewById(R.id.version_name);
        versionCode.setText(String.valueOf("Version Code : " + BuildConfig.VERSION_CODE));
        versionName.setText(String.valueOf("Version Name : " + BuildConfig.VERSION_NAME));

        materialDialog.show();
    }
}
