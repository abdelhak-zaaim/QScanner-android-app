package com.abc.qrscannerpro.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.zxing.Result;
import com.abc.qrscannerpro.R;
import com.abc.qrscannerpro.converse.QRResult;
import com.abc.qrscannerpro.constant.Constants;
import com.abc.qrscannerpro.utils.SharedPreferencesUtils;
import com.abc.qrscannerpro.views.ZXingScannerView;

public class FragmentQRcode extends FragmentBase implements ZXingScannerView.ResultHandler, QRResult.OnClickListener, Constants {
    private View rootView;
    private boolean isAutoFocus = true;
    private boolean isSquare = false;
    private ZXingScannerView mScannerView;
    public static Fragment newInstance() {
        return new FragmentQRcode();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_qr, container, false);
        setHasOptionsMenu(true);
        listenerActivity.visibleBackButton();
        initView();
        return rootView;
    }

    private void initView() {
        if (listenerActivity != null) {
            listenerActivity.setTitle(getString(R.string.header_qr_scanner));
        }
        SharedPreferencesUtils.setMainScreenType(MAIN_SCREEN_QR);
        mScannerView = (ZXingScannerView) rootView.findViewById(R.id.zxing_scanner_view);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }


    @Override
    public void handleResult(Result rawResult) {
        stopScanner();
        QRResult.showQRResultDialog(listenerActivity, rawResult.getText(), FragmentQRcode.this);
    }

    public void startScanner() {
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
        mScannerView.setFormats();
        mScannerView.setAutoFocus(isAutoFocus);
        mScannerView.setSquareViewFinder(isSquare);
    }

    public void stopScanner() {
        mScannerView.stopCamera();
    }

    public void resumeScanner() {
        mScannerView.resumeCameraPreview(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        startScanner();
    }

    @Override
    public void onPause() {
        super.onPause();
        stopScanner();
    }

    @Override
    public void onClick() {
        startScanner();
    }
}
