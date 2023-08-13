package com.abc.qrscannerpro.converse;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import androidx.appcompat.app.AlertDialog;
import android.util.Patterns;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.TextView;
import android.widget.Toast;

import com.abc.qrscannerpro.R;
import com.abc.qrscannerpro.BarActivity;
import com.abc.qrscannerpro.MainActivity;
import com.abc.qrscannerpro.QRActivity;
import com.abc.qrscannerpro.helper.FactoryHelper;
import com.abc.qrscannerpro.fragment.FragmentSave;
import com.abc.qrscannerpro.helper.CopyHelper;
import com.abc.qrscannerpro.constant.Constants;
import com.abc.qrscannerpro.utils.SharedPreferencesUtils;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;


public class QRResult implements Constants {
    private static View view;
    private static AlertDialog alert;
    private static String resultText = "";
    private static Class mClass;
    private static Activity mActivity;
    private static TextView openBrowserTv;
    private static TextView searchBrowserTv;
    private static OnClickListener mOnClick;

    public interface OnClickListener {
        void onClick();
    }


    public static void showQRResultDialog(final Context context, String result, OnClickListener onClick) {
        resultText = result;
        mOnClick = onClick;

        if (mClass == QRActivity.class) {
            mActivity = (QRActivity) context;
        } else if (mClass == BarActivity.class) {
            mActivity = (BarActivity) context;
        }

        alert = new AlertDialog.Builder(context, R.style.MyAlertDialogStyle)
                .setView(getView(context))
                .setPositiveButton(context.getString(R.string.apply_text), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mOnClick.onClick();
                        return;
                    }
                }).setCancelable(false)
                .create();

        alert.show();
    }

    private static View getView(final Context context) {

        view = View.inflate(context, R.layout.dialog_result, null);
        ((TextView) view.findViewById(R.id.result_tv)).setText(resultText);

        final InterstitialAd mInterstitial = new InterstitialAd(context);
        mInterstitial.setAdUnitId(context.getString(R.string.interstitial_ad_unit));
        mInterstitial.loadAd(new AdRequest.Builder().build());

        mInterstitial.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // TODO Auto-generated method stub
                super.onAdLoaded();
                if (mInterstitial.isLoaded()) {
                    mInterstitial.show();
                }
            }
        });

        openBrowserTv = (TextView) view.findViewById(R.id.open_browser_tv);
        searchBrowserTv = (TextView) view.findViewById(R.id.search_browser_tv);

        openBrowserTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent linkIntent = new Intent(Intent.ACTION_VIEW);
                linkIntent.setData(Uri.parse(resultText));
                context.startActivity(linkIntent);
            }
        });

        searchBrowserTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchIntent = new Intent(Intent.ACTION_WEB_SEARCH);
                searchIntent.putExtra(SearchManager.QUERY, resultText);
                context.startActivity(searchIntent);
            }
        });

        ((TextView) view.findViewById(R.id.copy_clipboard_tv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isCopy = CopyHelper.copyToClipboard(context, resultText);
                if (isCopy) {
                    Toast.makeText(context, "Text copied to clipboard",
                        Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Could not copy text to clipboard",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (SharedPreferencesUtils.isAutoSave()) {
            (view.findViewById(R.id.save_code_db_tv)).setVisibility(View.GONE);
            saveDb(context);
        }
        ((TextView) view.findViewById(R.id.save_code_db_tv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDb(context);
            }
        });

        if (URLUtil.isValidUrl(resultText)) {
            isLink();
        } else if (Patterns.WEB_URL.matcher(resultText).matches()) {
            isLink();
        } else {
            isNotLink();
        }



        return view;
    }

    private static void saveSettings(Context context) {
    }

    private static void isLink() {
        openBrowserTv.setVisibility(View.VISIBLE);
        searchBrowserTv.setVisibility(View.GONE);
    }

    private static void isNotLink() {
        openBrowserTv.setVisibility(View.GONE);
        searchBrowserTv.setVisibility(View.VISIBLE);
    }

    private static void saveDb(Context context) {
        try {
            String recordDbName = "Record_0";
            FactoryHelper.getHelper().getQRCodeDAO().getAllItems();
            if (FactoryHelper.getHelper().getQRCodeDAO().getAllItems().size() != 0) {
                recordDbName = "Record_" + String.valueOf(FactoryHelper.getHelper().getQRCodeDAO().getLastItem().get(0).getId());
            }
            FactoryHelper.getHelper().addQRCodeItemDB(SharedPreferencesUtils.isAutoSave(), context, 0, recordDbName, resultText);
            alert.dismiss();
            if (!SharedPreferencesUtils.isAutoSave())
                ((MainActivity) context).showFragment(FragmentSave.newInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
