package com.abc.qrscannerpro.converse;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Environment;
import androidx.appcompat.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.abc.qrscannerpro.R;
import com.abc.qrscannerpro.constant.Constants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class BmpSave implements Constants {
    private static View view;
    private static Bitmap bmp;
    private static EditText fileNameEditText;

    public static void showSaveBmpDialog(final Context context, Bitmap bitMap) {
        bmp = bitMap;

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
        fileNameEditText = (EditText) view.findViewById(R.id.file_name_edit_text);
        fileNameEditText.requestFocus();

        return view;
    }

    private static void saveSettings(Context context) {
        if (fileNameEditText.getText() != null && !String.valueOf(fileNameEditText.getText()).equals("")) {
            FileOutputStream out = null;
            try {
                String pathDir = Environment.getExternalStorageDirectory().getPath() + "/"+ context.getString(R.string.app_name);
                String fileName = String.valueOf(fileNameEditText.getText()) + ".png";
                File sampleDir = new File(pathDir);
                if (!sampleDir.exists()) {
                    sampleDir.mkdirs();
                }
                File file = new File(pathDir, fileName);
                out = new FileOutputStream(file.getAbsolutePath());
                bmp.compress(Bitmap.CompressFormat.PNG, 100, out);
                Toast.makeText(context, "File saved:" + file.getAbsolutePath(),
                    Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
