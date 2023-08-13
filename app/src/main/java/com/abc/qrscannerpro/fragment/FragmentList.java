package com.abc.qrscannerpro.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.abc.qrscannerpro.R;
import com.abc.qrscannerpro.adapter.CodeAdapter;
import com.abc.qrscannerpro.helper.FactoryHelper;
import com.abc.qrscannerpro.db.QRCodeItem;
import com.abc.qrscannerpro.converse.ConfirmDelete;
import com.abc.qrscannerpro.helper.CodeHelper;
import com.abc.qrscannerpro.constant.Constants;
import com.abc.qrscannerpro.helper.PaintHelper;

import java.util.ArrayList;
import java.util.List;

import static com.abc.qrscannerpro.MainActivity.codeListFragmentAdapter;

public class FragmentList extends FragmentBase implements Constants, CodeAdapter.OnClickListener, ConfirmDelete.OnClickListener {
    private View rootView;
    private static RecyclerView recyclerView;
    private static RelativeLayout noRecordLayout;
    private List<QRCodeItem> callRecordList = new ArrayList<>();

    public static Fragment newInstance() {
        return new FragmentList();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_codelist, container, false);
        setHasOptionsMenu(true);
        listenerActivity.visibleBackButton();
        initView();
        setStyle();
        return rootView;
    }

    private void initView() {
        if (listenerActivity != null) {
            listenerActivity.setTitle(getString(R.string.header_code_list));
        }

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        noRecordLayout = (RelativeLayout) rootView.findViewById(R.id.no_record_call_layout);
        updateList();

    }

    private void updateList() {
        if (recyclerView != null) {
            try {
                callRecordList = FactoryHelper.getHelper().getQRCodeDAO().getAllItems();
                LinearLayoutManager layoutManager = new LinearLayoutManager(listenerActivity);
                recyclerView.setLayoutManager(layoutManager);
                codeListFragmentAdapter = new CodeAdapter(listenerActivity, FragmentList.this);
                recyclerView.setAdapter(codeListFragmentAdapter);
                codeListFragmentAdapter.setLists(callRecordList);
                codeListFragmentAdapter.notifyDataSetChanged();
                if (callRecordList.size() == 0) {
                    recyclerView.setVisibility(View.GONE);
                    (rootView.findViewById(R.id.no_record_call_layout)).setVisibility(View.VISIBLE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.delete_list_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                listenerActivity.showFragment(FragmentDelete.newInstance());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(QRCodeItem callRecordItem, View view, int position) {
        switch (view.getId()) {
            case R.id.dots_layout:
                ConfirmDelete.showDeleteCodeConfirmDialog(listenerActivity, callRecordItem, FragmentList.this);

                break;
            case R.id.root_item_layout:
                listenerActivity.showFragment(FragmentEdit.newInstance(callRecordItem.getId(), CODE_PREVIEW_LAST_FRAGMENT_MAIN));
                break;
            default:
                break;
        }
    }

    public static void visibleRecycler() {
        recyclerView.setVisibility(View.VISIBLE);
        noRecordLayout.setVisibility(View.GONE);
    }
    public static void visibleTextView() {
        recyclerView.setVisibility(View.GONE);
        noRecordLayout.setVisibility(View.VISIBLE);
    }

    private void setStyle() {
        PaintHelper.setIconsColors(listenerActivity, rootView, R.id.main_screen_folder_ic, R.drawable.main_screen_folder_ic);
        PaintHelper.setIconsColors(listenerActivity, rootView, R.id.main_screen_folder_ic, R.drawable.main_screen_folder_ic);
    }

    @Override
    public void onDeleteClick(QRCodeItem qrCodeItem) {
        try {
            FactoryHelper.getHelper().getQRCodeDAO().deleteItem(qrCodeItem.getId());
            CodeHelper.updateAllLists(listenerActivity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
