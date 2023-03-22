package com.drag.demo.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.drag.demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * created by macbook
 * on 2023/3/21
 * ViewPagerAdapter
 */
public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.ViewHolder> {

    private final List<List<String>> datas;
    private final Context context;
    private final LayoutInflater layoutInflater;
    private final View.OnDragListener onDragListener;

    public ViewPagerAdapter(Context context, List<List<String>> datas, View.OnDragListener onDragListener) {
        this.datas = datas;
        this.context = context;
        this.onDragListener = onDragListener;
        this.layoutInflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(layoutInflater.inflate(R.layout.item_drag_page, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //TODO 一定要new ArrayList<>()，否则会抛ConcurrentModificationException
        PagerContentAdapter contentAdapter = new PagerContentAdapter(new ArrayList<>(datas.get(position)), context);
        holder.recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
        holder.recyclerView.setAdapter(contentAdapter);
        holder.recyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(holder.recyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
            }

            @Override
            public void onItemLongClick(RecyclerView recyclerView, RecyclerView.ViewHolder vh) {
                //长按item触发拖拽换位
                ViewDragHelper.startDrag(recyclerView, vh);
            }
        });
        holder.recyclerView.setOnDragListener(onDragListener);
    }


    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerView;

        public ViewHolder(View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.list_data);
        }
    }
}
