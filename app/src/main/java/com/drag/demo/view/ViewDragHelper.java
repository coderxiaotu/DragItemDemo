package com.drag.demo.view;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * created by macbook
 * on 2023/3/21
 * ViewDragHelper
 */
public class ViewDragHelper {

    /**
     * 触发拖拽换位
     *
     * @param recyclerView RecyclerView
     * @param holder           ViewHolder
     */
    public static void startDrag(RecyclerView recyclerView, RecyclerView.ViewHolder holder) {
        View.DragShadowBuilder dragShadowBuilder = new View.DragShadowBuilder(holder.itemView);
        holder.itemView.startDragAndDrop(null, dragShadowBuilder, new DragModel(recyclerView, holder), 0);
    }



    public static class DragModel {
        public RecyclerView recyclerView;
        public RecyclerView.ViewHolder holder;

        public DragModel(RecyclerView recyclerView, RecyclerView.ViewHolder holder) {
            this.recyclerView = recyclerView;
            this.holder = holder;
        }
    }

}
