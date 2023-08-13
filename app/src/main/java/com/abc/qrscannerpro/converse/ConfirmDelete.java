package com.abc.qrscannerpro.converse;

import android.content.Context;
import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.abc.qrscannerpro.R;
import com.abc.qrscannerpro.db.QRCodeItem;

public class ConfirmDelete {
    private static View view;
    private static TextView textView;
    private static OnClickListener mOnClick;

    public interface OnClickListener {
        void onDeleteClick(QRCodeItem qrCodeItem);
    }

    public static void showDeleteCodeConfirmDialog(final Context context, final QRCodeItem ignoreContactItem, OnClickListener onClick) {
        mOnClick = onClick;
        AlertDialog alert = new AlertDialog.Builder(context, R.style.MyAlertDialogStyle)
                .setView(getView(context))
                .setPositiveButton(context.getString(R.string.apply_text), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mOnClick.onDeleteClick(ignoreContactItem);
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

    private static View getView(Context context) {
        view = View.inflate(context, R.layout.dialog_confirm, null);

        textView = (TextView) view.findViewById(R.id.edit_text_pass);
        textView.setText(context.getString(R.string.dialog_delete_code_confirm));
        return view;
    }

}
