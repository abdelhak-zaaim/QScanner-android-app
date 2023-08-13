package com.abc.qrscannerpro.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abc.qrscannerpro.R;
import com.abc.qrscannerpro.adapter.ListAdapter;
import com.abc.qrscannerpro.helper.FactoryHelper;
import com.abc.qrscannerpro.db.CodeFormatItem;
import com.abc.qrscannerpro.constant.Constants;

import java.util.ArrayList;
import java.util.List;

public class FragmentCode extends FragmentBase implements Constants, ListAdapter.OnClickListener {
    private View rootView;
    private RecyclerView recyclerView;
    private ListAdapter codeFormatsListFragmentAdapter;
    private List<CodeFormatItem> callRecordList = new ArrayList<>();

    public static Fragment newInstance() {
        return new FragmentCode();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_list, container, false);
        setHasOptionsMenu(true);
        listenerActivity.visibleBackButton();
        initView();
        setStyle();
        return rootView;
    }

    private void initView() {
        if (listenerActivity != null) {
            listenerActivity.setTitle(getString(R.string.header_code_formats_list));
        }

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        updateList();

    }

    private void updateList() {
        if (recyclerView != null) {
            try {
                callRecordList = FactoryHelper.getHelper().getCodeFormatDAO().getAllItems();
                LinearLayoutManager layoutManager = new LinearLayoutManager(listenerActivity);
                recyclerView.setLayoutManager(layoutManager);
                codeFormatsListFragmentAdapter = new ListAdapter(listenerActivity, FragmentCode.this);
                recyclerView.setAdapter(codeFormatsListFragmentAdapter);
                codeFormatsListFragmentAdapter.setLists(callRecordList);
                codeFormatsListFragmentAdapter.notifyDataSetChanged();
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
    }

    private void setStyle() {
    }

    @Override
    public void onClick(CodeFormatItem codeFormatItem, View view, int position) {
    }
}
