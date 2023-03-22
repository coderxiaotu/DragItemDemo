package com.drag.demo.view;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.view.GestureDetectorCompat;
import androidx.recyclerview.widget.RecyclerView;

/**
 * created by macbook
 * on 2023/3/21
 * OnRecyclerItemClickListener
 */
public abstract class OnRecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
    private final GestureDetectorCompat gestureDetector;
    private final RecyclerView recyclerView;

    public OnRecyclerItemClickListener(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        gestureDetector = new GestureDetectorCompat(recyclerView.getContext(), new ItemTouchHelperGestureListener());
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        gestureDetector.onTouchEvent(e);
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        gestureDetector.onTouchEvent(e);
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    }

    private class ItemTouchHelperGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());
            if (childView != null) {
                RecyclerView.ViewHolder holder = recyclerView.getChildViewHolder(childView);
                onItemClick(holder);
            }
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());
            if (childView != null) {
                RecyclerView.ViewHolder holder = recyclerView.getChildViewHolder(childView);
                onItemLongClick(recyclerView, holder);
            }
        }
    }

    public abstract void onItemClick(RecyclerView.ViewHolder holder);

    public abstract void onItemLongClick(RecyclerView recyclerView, RecyclerView.ViewHolder holder);
}