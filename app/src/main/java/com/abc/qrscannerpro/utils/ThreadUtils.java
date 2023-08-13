package com.abc.qrscannerpro.utils;


import android.hardware.Camera;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

import com.abc.qrscannerpro.views.BarcodeScannerView;

// This code is mostly based on the top answer here: http://stackoverflow.com/questions/18149964/best-use-of-handlerthread-over-other-similar-classes
public class ThreadUtils extends HandlerThread {

    private BarcodeScannerView mScannerView;

    public ThreadUtils(BarcodeScannerView scannerView) {
        super("CameraHandlerThread");
        mScannerView = scannerView;
        start();
    }

    public void startCamera(final int cameraId) {
        Handler localHandler = new Handler(getLooper());
        localHandler.post(new Runnable() {
            @Override
            public void run() {
                final Camera camera = CameraUtils.getCameraInstance(cameraId);
                Handler mainHandler = new Handler(Looper.getMainLooper());
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mScannerView.setupCameraPreview(CameraWrapperUtils.getWrapper(camera, cameraId));
                    }
                });
            }
        });
    }
}
