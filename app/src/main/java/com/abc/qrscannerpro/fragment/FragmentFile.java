package com.abc.qrscannerpro.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.abc.qrscannerpro.R;
import com.abc.qrscannerpro.helper.FactoryHelper;
import com.abc.qrscannerpro.db.CodeFormatItem;
import com.abc.qrscannerpro.db.QRCodeItem;
import com.abc.qrscannerpro.converse.QRResultSaved;
import com.abc.qrscannerpro.converse.CodeNameSave;
import com.abc.qrscannerpro.converse.ConfirmSave;
import com.abc.qrscannerpro.helper.IconHelper;
import com.abc.qrscannerpro.constant.Constants;
import com.abc.qrscannerpro.helper.PaintHelper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class FragmentFile extends FragmentBase implements Constants, FragmentType.OnChangeType, ConfirmSave.OnClickListener,
        CodeNameSave.OnClickListener {
    private View rootView;
    private int PICK_REQUEST_CODE = 0;
    String path = "";
    private MultiFormatReader mMultiFormatReader;
    private List<BarcodeFormat> mFormats;
    public static final List<BarcodeFormat> ALL_FORMATS = new ArrayList<>();

    private QRCodeItem qrCodeItem;
    private String mCodeName = "";
    private String mCodeResult = "";
    private int mCodeType = 0;
    private TextView codeTypeTV;
    private TextView codeNameTV;
    private boolean isSaved = false;

    private void setFormats() {
        try {
            mFormats = new ArrayList<>();
            for (CodeFormatItem codeFormatItem : FactoryHelper.getHelper().getCodeFormatDAO().getAllItems()) {
                if (codeFormatItem.isActive()) {
                    mFormats.add(BarcodeFormat.values()[codeFormatItem.getFormatNumber()]);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
//        ALL_FORMATS.add(BarcodeFormat.AZTEC);
//        ALL_FORMATS.add(BarcodeFormat.CODABAR);
//        ALL_FORMATS.add(BarcodeFormat.CODE_39);
//        ALL_FORMATS.add(BarcodeFormat.CODE_93);
//        ALL_FORMATS.add(BarcodeFormat.CODE_128);
//        ALL_FORMATS.add(BarcodeFormat.DATA_MATRIX);
//        ALL_FORMATS.add(BarcodeFormat.EAN_8);
//        ALL_FORMATS.add(BarcodeFormat.EAN_13);
//        ALL_FORMATS.add(BarcodeFormat.ITF);
//        ALL_FORMATS.add(BarcodeFormat.MAXICODE);
//        ALL_FORMATS.add(BarcodeFormat.PDF_417);
//        ALL_FORMATS.add(BarcodeFormat.QR_CODE);
//        ALL_FORMATS.add(BarcodeFormat.RSS_14);
//        ALL_FORMATS.add(BarcodeFormat.RSS_EXPANDED);
//        ALL_FORMATS.add(BarcodeFormat.UPC_A);
//        ALL_FORMATS.add(BarcodeFormat.UPC_E);
//        ALL_FORMATS.add(BarcodeFormat.UPC_EAN_EXTENSION);
    }
    /**
     * newInstance() - метод позволяющий создать новый объект фрагмента
     */
    public static Fragment newInstance(String path) {
        Fragment fragment = new FragmentFile();
        Bundle bundle = new Bundle();
        bundle.putString(IMG_PATH, path);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_file, container, false);
        setHasOptionsMenu(true);
        listenerActivity.visibleBackButton();
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(IMG_PATH)) {
            path = bundle.getString(IMG_PATH);
        }
        initView();
        setStyle();
        return rootView;
    }

    /**
     * initView() - метод инициализирующий используемые view
     */
    private void initView() {
        if (listenerActivity != null) {
            listenerActivity.setTitle(getString(R.string.header_qr_from_file));
        }

        setFormats();
        startReadImg();

    }

    private void startReadImg() {
        listenerActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (path != "") {
                    File file = new File(path);
                    Bitmap bMap = BitmapFactory.decodeFile(file.getAbsolutePath());
                    ((ImageView) rootView.findViewById(R.id.qr_img)).setImageBitmap(bMap);

                    String decoded = scanQRImage(bMap);
                    if (decoded != null) {
                        try {
                            String recordDbName = "Record_0";
                            FactoryHelper.getHelper().getQRCodeDAO().getAllItems();
                            if (FactoryHelper.getHelper().getQRCodeDAO().getAllItems().size() != 0) {
                                recordDbName = "Record_" + String.valueOf(FactoryHelper.getHelper().getQRCodeDAO().getLastItem().get(0).getId());
                            }
                            FactoryHelper.getHelper().addQRCodeItemDB(false, listenerActivity, 0, recordDbName, decoded);

                            initElements();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(listenerActivity, listenerActivity.getString(R.string.qr_from_file_error),
                                Toast.LENGTH_SHORT).show();
                        isSaved = true;
                        listenerActivity.onBackPressed();
                    }

                }
            }
        });


    }

    private void initElements() {
        codeNameTV = (TextView) rootView.findViewById(R.id.preview_code_name_desc);
        codeTypeTV = (TextView) rootView.findViewById(R.id.preview_code_type_desc);
        final TextView codeResultTV = (TextView) rootView.findViewById(R.id.preview_code_result_desc);

        try {
            qrCodeItem = FactoryHelper.getHelper().getQRCodeDAO().getLastItem().get(0);
            mCodeName = qrCodeItem.getRecordName();
            mCodeResult = qrCodeItem.getDesc();
            mCodeType = qrCodeItem.getRecordType();
            ((ImageView) rootView.findViewById(R.id.preview_code_type_ic)).setImageDrawable(IconHelper.getCodeTypeIcon(listenerActivity, qrCodeItem.getRecordType()));

            codeNameTV.setText(qrCodeItem.getRecordName());
            codeResultTV.setText(qrCodeItem.getDesc());
            switch (qrCodeItem.getRecordType()) {
                case CODE_PREVIEW_TYPE_TEXT:
                    codeTypeTV.setText(getString(R.string.code_type_text));
                    break;
                case CODE_PREVIEW_TYPE_BARCODE:
                    codeTypeTV.setText(getString(R.string.code_type_barcode));
                    break;
                case CODE_PREVIEW_TYPE_WIFI:
                    codeTypeTV.setText(getString(R.string.code_type_wifi));
                    break;
                case CODE_PREVIEW_TYPE_GEO:
                    codeTypeTV.setText(getString(R.string.code_type_geo));
                    break;
                case CODE_PREVIEW_TYPE_WEB_LINK:
                    codeTypeTV.setText(getString(R.string.code_type_web_link));
                    break;
                case CODE_PREVIEW_TYPE_CONTACT:
                    codeTypeTV.setText(getString(R.string.code_type_contact));
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        (rootView.findViewById(R.id.preview_code_name_layout)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CodeNameSave.showSaveCodeNameDialog(listenerActivity, mCodeName, FragmentFile.this);
            }
        });

        (rootView.findViewById(R.id.preview_code_type_layout)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenerActivity.showFragment(FragmentType.newInstance(mCodeType, CODE_PREVIEW_TYPE_BACK_FILE, FragmentFile.this));
            }
        });

        (rootView.findViewById(R.id.preview_code_result_layout)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QRResultSaved.showQRResultSavedDialog(listenerActivity, qrCodeItem.getDesc());
            }
        });

        LinearLayout saveBtn = (LinearLayout) rootView.findViewById(R.id.save_layout);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (codeNameTV != null && codeNameTV.getText() != null) {
                        qrCodeItem.setRecordName(String.valueOf(codeNameTV.getText()));
                    }
                    qrCodeItem.setRecordType(mCodeType);
                    FactoryHelper.getHelper().getQRCodeDAO().update(qrCodeItem);
                    isSaved = true;
                    Toast.makeText(listenerActivity, getString(R.string.db_save),
                            Toast.LENGTH_SHORT).show();
                    listenerActivity.onBackPressed();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setStyle() {
        PaintHelper.setIconsColors(listenerActivity, rootView, R.id.preview_code_name_ic, R.drawable.preview_code_name_ic);
        PaintHelper.setIconsColors(listenerActivity, rootView, R.id.preview_code_result_ic, R.drawable.preview_code_result_ic);
    }

    public byte[] BitmapToArray(Bitmap bmp){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 50, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
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
        //copy pixel data from the Bitmap into the 'intArray' array
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
            contents = null;
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
                contents = null;
                // continue
            } finally {
                mMultiFormatReader.reset();
            }
        }
        return contents;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }


    @Override
    public void onChange(int codeType, String codeTypeText) {
        mCodeType = codeType;
        codeTypeTV.setText(codeTypeText);
        ((ImageView) rootView.findViewById(R.id.preview_code_type_ic)).setImageDrawable(IconHelper.getCodeTypeIcon(listenerActivity, codeType));
    }

    @Override
    public void onClick(boolean isDelete) {
        isSaved = true;
        if (isDelete) {
            try {
                FactoryHelper.getHelper().getQRCodeDAO().deleteItem(qrCodeItem.getId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(listenerActivity, getString(R.string.db_save),
                    Toast.LENGTH_SHORT).show();
        }
        listenerActivity.onBackPressed();
    }

    public boolean isSaved() {
        return isSaved;
    }

    public void saveDialog() {
        if (codeNameTV != null && codeNameTV.getText() != null)
            ConfirmSave.showSaveConfirmDialog(listenerActivity, qrCodeItem, String.valueOf(codeNameTV.getText()), mCodeType, FragmentFile.this);
    }

    @Override
    public void onClick(String name) {
        codeNameTV.setText(name);
        mCodeName = name;
    }


}