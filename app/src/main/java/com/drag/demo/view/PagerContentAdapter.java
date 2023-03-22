package com.drag.demo.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.drag.demo.R;

import java.util.List;

/**
 * created by macbook
 * on 2023/3/21
 * PagerContentAdapter
 */
public class PagerContentAdapter extends RecyclerView.Adapter<PagerContentAdapter.ViewHolder> {

    private final List<String> datas;
    private final LayoutInflater layoutInflater;

    public PagerContentAdapter(List<String> datas, Context context) {
        this.datas = datas;
        this.layoutInflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(layoutInflater.inflate(R.layout.item_test, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvName.setText(datas.get(position));
    }

    public List<String> getDatas() {
        return datas;
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
        }
    }
}
