package com.abc.qrscannerpro.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.abc.qrscannerpro.R;
import com.abc.qrscannerpro.db.QRCodeItem;
import com.abc.qrscannerpro.helper.IconHelper;
import com.abc.qrscannerpro.constant.Constants;
import com.abc.qrscannerpro.constant.ConstantsColor;
import com.abc.qrscannerpro.utils.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.List;

public class DeleteAdapter extends RecyclerView.Adapter<DeleteAdapter.ViewHolder> implements Constants, ConstantsColor {
    private final OnClickListener onclick;
    private final int displayHeight;
    private List<QRCodeItem> itemsList;
    private Context context;
    private int displayWidth;

    boolean[] checked;


    public interface OnClickListener{
        void onClick(QRCodeItem callRecordItem, View view, int position);
    }

    public DeleteAdapter(Context context, OnClickListener onclcik) {
        this.onclick = onclcik;
        this.itemsList = new ArrayList<>();
        this.context = context;
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        displayWidth = display.getWidth();
        displayHeight = display.getHeight();
    }

    public void setLists(List<QRCodeItem> itemsList){
        this.itemsList = new ArrayList<>();
        this.itemsList.addAll(itemsList);
        checked = new boolean[itemsList.size()];
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_delete, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        SharedPreferencesUtils.initSharedReferences(context);
        final View view = holder.view;
        final TextView callName = (TextView) view.findViewById(R.id.call_name);
        final TextView callTime = (TextView) view.findViewById(R.id.call_time);
        final ImageView callContactPhoto = (ImageView) view.findViewById(R.id.img_call_contact_photo);

        callName.setTextColor(context.getResources().getColor(R.color.color_list_text_color));
        callTime.setTextColor(context.getResources().getColor(R.color.color_list_text_desc_color));

        callName.setText(itemsList.get(position).getRecordName());
        callTime.setText(itemsList.get(position).getDesc());

        callContactPhoto.setImageDrawable(IconHelper.getCodeTypeIcon(context, itemsList.get(position).getRecordType()));

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclick.onClick(itemsList.get(position), view, position);
            }
        });

        holder.mCheckBox.setChecked(checked[position]);
        holder.mCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checked[position] = !checked[position];
            }
        });

        (view.findViewById(R.id.root_item_layout)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclick.onClick(itemsList.get(position), view.findViewById(R.id.root_item_layout), position);
            }
        });
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        if (itemsList != null)
            return itemsList.size();
        else
            return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final View view;
        private CheckBox mCheckBox;

        public ViewHolder(View v) {
            super(v);
            view = v;
            mCheckBox = (CheckBox) v.findViewById(R.id.delete_checkbox);
        }
    }

    public boolean[] getChecked() {
        return checked;
    }

    public List<QRCodeItem> getCheckedList() {
        List<QRCodeItem> itemsListReturned = new ArrayList<>();
        for (int i = 0; i < itemsList.size(); i++) {
            if (checked[i])
                itemsListReturned.add(itemsList.get(i));
        }

        return itemsListReturned;
    }

    public void setCheckAllCheckBox(boolean isChecked) {
        for (int i = 0; i < itemsList.size(); i++) {
            checked[i] = isChecked;
        }
        notifyDataSetChanged();
    }

}

