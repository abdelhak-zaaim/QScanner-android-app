package com.abc.qrscannerpro;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.abc.qrscannerpro.helper.FactoryHelper;
import com.abc.qrscannerpro.db.CodeFormatItem;
import com.abc.qrscannerpro.constant.Constants;
import com.abc.qrscannerpro.utils.SharedPreferencesUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class QRReaderActivity extends AppCompatActivity implements Constants {

    String path = "";
    private MultiFormatReader mMultiFormatReader;
    private List<BarcodeFormat> mFormats;
    public static final List<BarcodeFormat> ALL_FORMATS = new ArrayList<>();

    static {
        try {
            for (CodeFormatItem codeFormatItem : FactoryHelper.getHelper().getCodeFormatDAO().getAllItems()) {
                if (codeFormatItem.isActive()) {
                    ALL_FORMATS.add(BarcodeFormat.values()[codeFormatItem.getFormatNumber()]);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferencesUtils.initSharedReferences(QRReaderActivity.this);
        setContentView(R.layout.activity_img);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            path = bundle.getString(IMG_PATH);
        }

        initView();
    }

    private void initView() {
        if (path != "") {
            File file = new File(path);
            Bitmap bMap = BitmapFactory.decodeFile(file.getAbsolutePath());
            ((ImageView) findViewById(R.id.qr_img)).setImageBitmap(bMap);

            String decoded = scanQRImage(bMap);
            Toast.makeText(this, decoded,
                Toast.LENGTH_SHORT).show();
            Log.d("QrTest", "Decoded string=" + decoded);
        }
    }


    private void initMultiFormatReader() {
        Map<DecodeHintType,Object> hints = new EnumMap<>(DecodeHintType.class);
        hints.put(DecodeHintType.POSSIBLE_FORMATS, getFormats());
        mMultiFormatReader = new MultiFormatReader();
        mMultiFormatReader.setHints(hints);
    }

    public Collection<BarcodeFormat> getFormats() {
        if(mFormats == null) {
            return ALL_FORMATS;
        }
        return mFormats;
    }


    public String scanQRImage(Bitmap bMap) {
        String contents = null;

        int[] intArray = new int[bMap.getWidth() * bMap.getHeight()];

        bMap.getPixels(intArray, 0, bMap.getWidth(), 0, 0, bMap.getWidth(), bMap.getHeight());

        LuminanceSource source = new RGBLuminanceSource(bMap.getWidth(), bMap.getHeight(), intArray);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

        initMultiFormatReader();

        Result result = null;
        try {
            result = mMultiFormatReader.decodeWithState(bitmap);
            contents = result.getText();
        } catch (Exception e) {
            e.printStackTrace();
            contents = "I do not even see in the picture of the qr-code";
        } finally {
            mMultiFormatReader.reset();
        }

        if (result == null) {
            LuminanceSource invertedSource = source.invert();
            bitmap = new BinaryBitmap(new HybridBinarizer(invertedSource));
            try {
                result = mMultiFormatReader.decodeWithState(bitmap);
                contents = result.getText();
            } catch (Exception e) {
                e.printStackTrace();
                contents = "I do not even see in the picture of the qr-code";
            } finally {
                mMultiFormatReader.reset();
            }
        }

        return contents;
    }



}