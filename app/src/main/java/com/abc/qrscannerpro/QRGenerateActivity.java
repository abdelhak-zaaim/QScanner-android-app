package com.abc.qrscannerpro;

import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.abc.qrscannerpro.converse.BmpSave;
import com.abc.qrscannerpro.constant.Constants;
import com.abc.qrscannerpro.utils.SharedPreferencesUtils;

public class QRGenerateActivity extends AppCompatActivity implements Constants {

    private final static int WHITE = 0xFFFFFFFF;
    private final static int BLACK = 0xFF000000;
    private final static int WIDTH = 800;
    private final static int HEIGHT = 800;

    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferencesUtils.initSharedReferences(QRGenerateActivity.this);
        setContentView(R.layout.activity_qr_generate);

        initView();
    }

    private void initView() {
        final ImageView imageView = (ImageView) findViewById(R.id.qr_img);
        final EditText qrEditText = (EditText) findViewById(R.id.qr_edit_text);
        final LinearLayout generateBtn = (LinearLayout) findViewById(R.id.generate_btn);
        final LinearLayout saveBtn = (LinearLayout) findViewById(R.id.save_btn);

        qrEditText.requestFocus();

        generateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (qrEditText.getText() != null) {
                        bitmap = encodeAsBitmap(String.valueOf(qrEditText.getText()));
                        if (bitmap != null) {
                            imageView.setImageBitmap(bitmap);
                            qrEditText.clearFocus();
                            saveBtn.setVisibility(View.VISIBLE);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final InterstitialAd mInterstitial = new InterstitialAd(getApplicationContext());
                mInterstitial.setAdUnitId(getString(R.string.interstitial_ad_unit));
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
                BmpSave.showSaveBmpDialog(QRGenerateActivity.this, bitmap);
            }
        });

    }

    private Bitmap encodeAsBitmap(String str) throws WriterException {
        BitMatrix result;
        try {
            result = new MultiFormatWriter().encode(str, BarcodeFormat.QR_CODE, WIDTH, HEIGHT, null);
        } catch (IllegalArgumentException iae) {

            return null;
        }

        int width = result.getWidth();
        int height = result.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

}