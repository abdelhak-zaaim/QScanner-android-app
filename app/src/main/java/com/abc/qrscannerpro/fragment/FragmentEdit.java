package com.abc.qrscannerpro.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.abc.qrscannerpro.R;
import com.abc.qrscannerpro.helper.FactoryHelper;
import com.abc.qrscannerpro.db.QRCodeItem;
import com.abc.qrscannerpro.converse.ConfirmDelete;
import com.abc.qrscannerpro.converse.QRResultSaved;
import com.abc.qrscannerpro.converse.CodeNameSave;
import com.abc.qrscannerpro.converse.ConfirmSave;
import com.abc.qrscannerpro.helper.IconHelper;
import com.abc.qrscannerpro.constant.Constants;
import com.abc.qrscannerpro.helper.PaintHelper;

import java.sql.SQLException;

public class FragmentEdit extends FragmentBase implements Constants, FragmentType.OnChangeType, ConfirmSave.OnClickListener,
        CodeNameSave.OnClickListener, ConfirmDelete.OnClickListener {
    private View rootView;
    private int mId;
    private int mLastFragment;
    private QRCodeItem qrCodeItem;
    private String mCodeName = "";
    private String mCodeResult = "";
    private int mCodeType = 0;
    private TextView codeTypeTV;
    private TextView codeNameTV;
    private boolean isSaved = false;

    public static Fragment newInstance(int id, int lastFragment) {
        Fragment fragment = new FragmentEdit();
        Bundle bundle = new Bundle();
        bundle.putInt(CODE_ID, id);
        bundle.putInt(CODE_PREVIEW_LAST_FRAGMENT, lastFragment);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_preview, container, false);
        setHasOptionsMenu(true);
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(CODE_ID) && bundle.containsKey(CODE_PREVIEW_LAST_FRAGMENT)) {
            mId = bundle.getInt(CODE_ID);
            mLastFragment = bundle.getInt(CODE_PREVIEW_LAST_FRAGMENT);
        }
        listenerActivity.visibleBackButton();
        initView();
        setStyle();
        return rootView;
    }

    private void initView() {
        if (listenerActivity != null) {
            listenerActivity.setTitle(getString(R.string.header_edit));
        }

        codeNameTV = (TextView) rootView.findViewById(R.id.preview_code_name_desc);
        codeTypeTV = (TextView) rootView.findViewById(R.id.preview_code_type_desc);
        final TextView codeResultTV = (TextView) rootView.findViewById(R.id.preview_code_result_desc);

        try {
            qrCodeItem = FactoryHelper.getHelper().getQRCodeDAO().getItem(mId);
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
                CodeNameSave.showSaveCodeNameDialog(listenerActivity, mCodeName, FragmentEdit.this);
            }
        });

        (rootView.findViewById(R.id.preview_code_type_layout)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenerActivity.showFragment(FragmentType.newInstance(mCodeType, CODE_PREVIEW_TYPE_BACK_EDIT, FragmentEdit.this));
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
                        listenerActivity.onBackPressed();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.code_edit_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                ConfirmDelete.showDeleteCodeConfirmDialog(listenerActivity, qrCodeItem, FragmentEdit.this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setStyle() {
        PaintHelper.setIconsColors(listenerActivity, rootView, R.id.preview_code_name_ic, R.drawable.preview_code_name_ic);
        PaintHelper.setIconsColors(listenerActivity, rootView, R.id.preview_code_result_ic, R.drawable.preview_code_result_ic);
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
        listenerActivity.onBackPressed();
    }

    public boolean isSaved() {
        return isSaved;
    }

    public void saveDialog() {
        if (codeNameTV != null && codeNameTV.getText() != null)
            ConfirmSave.showSaveConfirmDialog(listenerActivity, qrCodeItem, String.valueOf(codeNameTV.getText()), mCodeType, FragmentEdit.this);
    }

    @Override
    public void onClick(String name) {
        codeNameTV.setText(name);
        mCodeName = name;
    }

    @Override
    public void onDeleteClick(QRCodeItem qrCodeItem) {
        try {
            isSaved = true;
            FactoryHelper.getHelper().getQRCodeDAO().deleteItem(qrCodeItem.getId());
            listenerActivity.onBackPressed();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getmLastFragment() {
        return mLastFragment;
    }
}
