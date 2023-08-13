package com.abc.qrscannerpro;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.abc.qrscannerpro.constant.Constants;
import com.abc.qrscannerpro.utils.SharedPreferencesUtils;
import com.abc.qrscannerpro.utils.ResultUtils;
import com.abc.qrscannerpro.views.ZBarScannerView;


public class BarActivity extends AppCompatActivity implements ZBarScannerView.ResultHandler, Constants {

    private boolean isAutoFocus = true;
    private boolean isSquare = false;
    private ZBarScannerView mScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferencesUtils.initSharedReferences(BarActivity.this);
        mScannerView = new ZBarScannerView(this);
        setContentView(mScannerView);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            isAutoFocus = bundle.getBoolean(IS_AUTO_FOCUS);
            isSquare = bundle.getBoolean(IS_SQUARE);
        }

        initView();
    }


    private void initView() {

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
    public void handleResult(ResultUtils rawResult) {
        stopScanner();
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


}