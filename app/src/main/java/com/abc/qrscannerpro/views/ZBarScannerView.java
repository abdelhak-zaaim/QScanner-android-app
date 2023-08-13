package com.abc.qrscannerpro.views;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;

import com.abc.qrscannerpro.utils.ResultUtils;
import com.abc.qrscannerpro.utils.FormatUtils;
import com.abc.qrscannerpro.utils.DisplayUtils;

import net.sourceforge.zbar.Config;
import net.sourceforge.zbar.Image;
import net.sourceforge.zbar.ImageScanner;
import net.sourceforge.zbar.Symbol;
import net.sourceforge.zbar.SymbolSet;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;


public class ZBarScannerView extends BarcodeScannerView {
    private static final String TAG = "ZBarScannerView";

    public interface ResultHandler {
        public void handleResult(ResultUtils rawResult);
    }

    static {
        System.loadLibrary("iconv");
    }

    private ImageScanner mScanner;
    private List<FormatUtils> mFormats;
    private ResultHandler mResultHandler;

    public ZBarScannerView(Context context) {
        super(context);
        setupScanner();
    }

    public ZBarScannerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setupScanner();
    }

    public void setFormats(List<FormatUtils> formats) {
        mFormats = formats;
        setupScanner();
    }

    public void setResultHandler(ResultHandler resultHandler) {
        mResultHandler = resultHandler;
    }

    public Collection<FormatUtils> getFormats() {
        if(mFormats == null) {
            return FormatUtils.ALL_FORMATS;
        }
        return mFormats;
    }

    public void setupScanner() {
        mScanner = new ImageScanner();
        mScanner.setConfig(0, Config.X_DENSITY, 3);
        mScanner.setConfig(0, Config.Y_DENSITY, 3);

        mScanner.setConfig(Symbol.NONE, Config.ENABLE, 0);
        for(FormatUtils format : getFormats()) {
            mScanner.setConfig(format.getId(), Config.ENABLE, 1);
        }
    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        if(mResultHandler == null) {
            return;
        }

        try {
            Camera.Parameters parameters = camera.getParameters();
            Camera.Size size = parameters.getPreviewSize();
            int width = size.width;
            int height = size.height;

            if (DisplayUtils.getScreenOrientation(getContext()) == Configuration.ORIENTATION_PORTRAIT) {
                int rotationCount = getRotationCount();
                if (rotationCount == 1 || rotationCount == 3) {
                    int tmp = width;
                    width = height;
                    height = tmp;
                }
                data = getRotatedData(data, camera);
            }

            Rect rect = getFramingRectInPreview(width, height);
            Image barcode = new Image(width, height, "Y800");
            barcode.setData(data);
            barcode.setCrop(rect.left, rect.top, rect.width(), rect.height());

            int result = mScanner.scanImage(barcode);

            if (result != 0) {
                SymbolSet syms = mScanner.getResults();
                final ResultUtils rawResult = new ResultUtils();
                for (Symbol sym : syms) {

                    String symData;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                        symData = new String(sym.getDataBytes(), StandardCharsets.UTF_8);
                    } else {
                        symData = sym.getData();
                    }
                    if (!TextUtils.isEmpty(symData)) {
                        rawResult.setContents(symData);
                        rawResult.setBarcodeFormat(FormatUtils.getFormatById(sym.getType()));
                        break;
                    }
                }

                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        ResultHandler tmpResultHandler = mResultHandler;
                        mResultHandler = null;
                        
                        stopCameraPreview();
                        if (tmpResultHandler != null) {
                            tmpResultHandler.handleResult(rawResult);
                        }
                    }
                });
            } else {
                camera.setOneShotPreviewCallback(this);
            }
        } catch(RuntimeException e) {
            Log.e(TAG, e.toString(), e);
        }
    }

    public void resumeCameraPreview(ResultHandler resultHandler) {
        mResultHandler = resultHandler;
        super.resumeCameraPreview();
    }
}
