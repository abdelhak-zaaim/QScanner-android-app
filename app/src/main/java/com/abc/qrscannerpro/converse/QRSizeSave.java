package com.abc.qrscannerpro.converse;

import android.content.Context;
import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.abc.qrscannerpro.R;
import com.abc.qrscannerpro.constant.Constants;
import com.abc.qrscannerpro.utils.SharedPreferencesUtils;


public class QRSizeSave implements Constants {
    private static View view;
    private static EditText fileNameEditText;
    private static OnClickListener mOnClick;

    public interface OnClickListener {
        void onClick(int size);
    }

    public static void showSaveQRSizeDialog(final Context context, OnClickListener onClick ) {
        mOnClick = onClick;
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
        ((TextView) view.findViewById(R.id.label)).setText(context.getString(R.string.save_dialog_qr_size));
        fileNameEditText = (EditText) view.findViewById(R.id.file_name_edit_text);
        fileNameEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        fileNameEditText.setText(String.valueOf(SharedPreferencesUtils.getQRImgSize()));
        fileNameEditText.requestFocus();

        return view;
    }

    private static void saveSettings(Context context) {
        if (fileNameEditText.getText() != null && !String.valueOf(fileNameEditText.getText()).equals("")) {
            String value = fileNameEditText.getText().toString();
            int qrSize = Integer.parseInt(value);
            if (qrSize >= 100 && qrSize <= 1600) {
                mOnClick.onClick(qrSize);
            } else
                Toast.makeText(context, context.getString(R.string.dialog_qr_size_error),
                        Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, context.getString(R.string.dialog_qr_size_error),
                    Toast.LENGTH_SHORT).show();
        }
    }

}
