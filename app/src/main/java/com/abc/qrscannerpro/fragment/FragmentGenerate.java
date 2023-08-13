package com.abc.qrscannerpro.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.abc.qrscannerpro.R;
import com.abc.qrscannerpro.converse.BmpSave;
import com.abc.qrscannerpro.constant.Constants;
import com.abc.qrscannerpro.utils.SharedPreferencesUtils;

public class FragmentGenerate extends FragmentBase implements Constants {
    private View rootView;
    private final static int WHITE = 0xFFFFFFFF;
    private final static int BLACK = 0xFF000000;
    private int WIDTH = 800;
    private int HEIGHT = 800;

    private Bitmap bitmap;

    public static Fragment newInstance() {
        return new FragmentGenerate();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_qr_generate, container, false);
        setHasOptionsMenu(true);
        listenerActivity.visibleBackButton();
        initView();
        return rootView;
    }

    private void initView() {
        if (listenerActivity != null) {
            listenerActivity.setTitle(getString(R.string.header_qr_scanner));
        }

        WIDTH = SharedPreferencesUtils.getQRImgSize();
        HEIGHT = SharedPreferencesUtils.getQRImgSize();

        final ImageView imageView = (ImageView) rootView.findViewById(R.id.qr_img);
        final EditText qrEditText = (EditText) rootView.findViewById(R.id.qr_edit_text);
        final LinearLayout generateBtn = (LinearLayout) rootView.findViewById(R.id.generate_btn);
        final LinearLayout saveBtn = (LinearLayout) rootView.findViewById(R.id.save_btn);

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
                BmpSave.showSaveBmpDialog(listenerActivity, bitmap);
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


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }

}
