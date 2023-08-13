package com.abc.qrscannerpro.converse;

import android.content.Context;
import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;
import android.view.View;

import com.abc.qrscannerpro.R;
import com.abc.qrscannerpro.helper.FactoryHelper;
import com.abc.qrscannerpro.db.QRCodeItem;
import com.abc.qrscannerpro.constant.Constants;


public class ConfirmSave implements Constants {
    private static View view;
    private static OnClickListener mOnClick;

    public interface OnClickListener {
        void onClick(boolean isDelete);
    }

    public static void showSaveConfirmDialog(final Context context, final QRCodeItem callRecordItem, final String name, final int type, OnClickListener onCLick) {
        mOnClick = onCLick;
        AlertDialog alert = new AlertDialog.Builder(context, R.style.MyAlertDialogStyle)
                .setView(getView(context))
                .setPositiveButton(context.getString(R.string.yes_text), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        saveDesc(context, callRecordItem, name, type);
                    }
                }).setCancelable(true)
                .setNegativeButton(context.getString(R.string.no_text), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mOnClick.onClick(true);
                    }
                }).create();

        alert.show();
    }

    private static View getView(Context context) {
        view = View.inflate(context, R.layout.dialog_confirm, null);
        return view;
    }

    private static void saveDesc(Context context, QRCodeItem callRecordItem, String name, int type) {
        try {
            callRecordItem.setRecordName(name);
            callRecordItem.setRecordType(type);
            try {
                FactoryHelper.getHelper().getQRCodeDAO().update(callRecordItem);
            } catch (Exception e) {
                e.printStackTrace();
            }
            mOnClick.onClick(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
