package com.abc.qrscannerpro.converse;

import android.content.Context;
import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.abc.qrscannerpro.R;
import com.abc.qrscannerpro.constant.Constants;


public class CodeNameSave implements Constants {
    private static View view;
    private static EditText fileNameEditText;
    private static OnClickListener mOnClick;
    private static String mName;

    public interface OnClickListener {
        void onClick(String name);
    }

    public static void showSaveCodeNameDialog(final Context context, final String name, OnClickListener onClick ) {
        mOnClick = onClick;
        mName = name;
        AlertDialog alert = new AlertDialog.Builder(context, R.style.MyAlertDialogStyle)
                .setView(getView(context))
                .setPositiveButton(context.getString(R.string.apply_text), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        saveSettings(context);
                    }
                }).setCancelable(false)

                .setNegativeButton(context.getString(R.string.cancel_text), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                }).create();

        alert.show();
    }

    private static View getView(final Context context) {
        view = View.inflate(context, R.layout.dialog_bmp, null);
        ((TextView) view.findViewById(R.id.label)).setText(context.getString(R.string.save_dialog_code_name));
        fileNameEditText = (EditText) view.findViewById(R.id.file_name_edit_text);
        fileNameEditText.setText(mName);
        fileNameEditText.requestFocus();

        return view;
    }

    private static void saveSettings(Context context) {
        if (fileNameEditText.getText() != null) {
            mOnClick.onClick(String.valueOf(fileNameEditText.getText()));
        }
    }

}
