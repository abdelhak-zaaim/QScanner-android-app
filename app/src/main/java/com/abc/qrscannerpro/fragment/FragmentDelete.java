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

import com.abc.qrscannerpro.R;
import com.abc.qrscannerpro.adapter.DeleteAdapter;
import com.abc.qrscannerpro.helper.FactoryHelper;
import com.abc.qrscannerpro.db.QRCodeItem;
import com.abc.qrscannerpro.converse.ConfirmDeleteCheck;
import com.abc.qrscannerpro.constant.Constants;

import java.util.ArrayList;
import java.util.List;

public class FragmentDelete extends FragmentBase implements Constants, DeleteAdapter.OnClickListener, View.OnClickListener, ConfirmDeleteCheck.OnClickListener {
    private View rootView;
    private RecyclerView recyclerView;
    private DeleteAdapter deleteCodeListFragmentAdapter;
    private List<QRCodeItem> callRecordList = new ArrayList<>();
    private boolean isChecked = true;

    public static Fragment newInstance() {
        return new FragmentDelete();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_delete, container, false);
        setHasOptionsMenu(true);
        listenerActivity.visibleBackButton();
        initView();
        setStyle();
        return rootView;
    }

    private void initView() {
        if (listenerActivity != null) {
            listenerActivity.setTitle(getString(R.string.header_code_delete_list));
        }

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        (rootView.findViewById(R.id.btn_delete)).setOnClickListener(FragmentDelete.this);
        updateList();

    }

    private void updateList() {
        if (recyclerView != null) {
            try {
                callRecordList = FactoryHelper.getHelper().getQRCodeDAO().getAllItems();
                LinearLayoutManager layoutManager = new LinearLayoutManager(listenerActivity);
                recyclerView.setLayoutManager(layoutManager);
                deleteCodeListFragmentAdapter = new DeleteAdapter(listenerActivity, FragmentDelete.this);
                recyclerView.setAdapter(deleteCodeListFragmentAdapter);
                deleteCodeListFragmentAdapter.setLists(callRecordList);
                deleteCodeListFragmentAdapter.notifyDataSetChanged();
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
        inflater.inflate(R.menu.delete_select_list_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_check_all:
                deleteCodeListFragmentAdapter.setCheckAllCheckBox(isChecked);
                isChecked = !isChecked;
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setStyle() {
    }

    @Override
    public void onClick(QRCodeItem callRecordItem, View view, int position) {
        switch (view.getId()) {
            case R.id.root_item_layout:
                listenerActivity.showFragment(FragmentEdit.newInstance(callRecordItem.getId(), CODE_PREVIEW_LAST_FRAGMENT_DELETE));
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_delete:
                List<QRCodeItem> itemsListReturned = deleteCodeListFragmentAdapter.getCheckedList();
                if (itemsListReturned != null && itemsListReturned.size() != 0)
                    ConfirmDeleteCheck.showDeleteCheckCodeConfirmDialog(listenerActivity, itemsListReturned, FragmentDelete.this);
                break;
        }
    }

    @Override
    public void onClick(List<QRCodeItem> callRecordItemList) {
        try {
            if (callRecordItemList != null && callRecordItemList.size() != 0) {
                for (QRCodeItem callRecordItem : callRecordItemList) {
                    FactoryHelper.getHelper().getQRCodeDAO().deleteItem(callRecordItem.getId());
                }
                updateAdapter();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateAdapter() {
        try {
            callRecordList.clear();
            callRecordList = FactoryHelper.getHelper().getQRCodeDAO().getAllItems();
            if (deleteCodeListFragmentAdapter == null) {
                LinearLayoutManager layoutManager = new LinearLayoutManager(listenerActivity);
                recyclerView.setLayoutManager(layoutManager);
                deleteCodeListFragmentAdapter = new DeleteAdapter(listenerActivity, FragmentDelete.this);
                recyclerView.setAdapter(deleteCodeListFragmentAdapter);
            }
            deleteCodeListFragmentAdapter.setLists(callRecordList);
            deleteCodeListFragmentAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
