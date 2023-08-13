package com.abc.qrscannerpro.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abc.qrscannerpro.R;
import com.abc.qrscannerpro.converse.QRResult;
import com.abc.qrscannerpro.constant.Constants;
import com.abc.qrscannerpro.utils.SharedPreferencesUtils;
import com.abc.qrscannerpro.utils.ResultUtils;
import com.abc.qrscannerpro.views.ZBarScannerView;

public class FragmentBarcode extends FragmentBase implements ZBarScannerView.ResultHandler, QRResult.OnClickListener, Constants {
    private View rootView;
    private boolean isAutoFocus = true;
    private boolean isSquare = false;
    private ZBarScannerView mScannerView;

    public static Fragment newInstance() {
        return new FragmentBarcode();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_barcode, container, false);
        setHasOptionsMenu(true);
        listenerActivity.visibleBackButton();

        initView();
        return rootView;
    }


    private void initView() {
        if (listenerActivity != null) {
            listenerActivity.setTitle(getString(R.string.header_barcode_scanner));
        }
        SharedPreferencesUtils.setMainScreenType(MAIN_SCREEN_BARCODE);
        mScannerView = (ZBarScannerView) rootView.findViewById(R.id.zxing_scanner_view);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }


    @Override
    public void handleResult(ResultUtils rawResult) {
        stopScanner();
        QRResult.showQRResultDialog(listenerActivity, rawResult.getContents(), FragmentBarcode.this);

    }

    public void startScanner() {
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
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
