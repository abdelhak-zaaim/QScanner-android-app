package com.abc.qrscannerpro.helper;

import android.content.Context;

import com.abc.qrscannerpro.db.QRCodeItem;
import com.abc.qrscannerpro.fragment.FragmentList;
import com.abc.qrscannerpro.constant.Constants;
import com.abc.qrscannerpro.utils.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.List;

import static com.abc.qrscannerpro.MainActivity.codeListFragmentAdapter;

public class CodeHelper implements Constants {
    private static List<QRCodeItem> callRecordList = new ArrayList<>();

    public static void updateAllLists(Context context) {
        SharedPreferencesUtils.initSharedReferences(context);
        try {
            callRecordList.clear();

            if (codeListFragmentAdapter != null) {
                callRecordList = FactoryHelper.getHelper().getQRCodeDAO().getAllItems();
                codeListFragmentAdapter.setLists(callRecordList);
                codeListFragmentAdapter.notifyDataSetChanged();

                if (callRecordList.size() != 0)
                    FragmentList.visibleRecycler();
                else
                    FragmentList.visibleTextView();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
