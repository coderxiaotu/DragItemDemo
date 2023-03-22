package com.drag.demo.view;

import android.util.Log;
import android.view.DragEvent;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

/**
 * created by macbook
 * on 2023/3/21
 * RecycleViewItemOnDragListener
 */
public class RecycleViewItemOnDragListener implements View.OnDragListener {
    private final onPageChange pageChange;
    private long lastChangeTime = 0;

    public RecycleViewItemOnDragListener(onPageChange pageChange) {
        this.pageChange = pageChange;
    }

    @Override
    public boolean onDrag(View view, DragEvent dragEvent) {
        //TODO
        Log.d("view拖拽", dragEvent.getAction() + "\n" + dragEvent.getX());
        switch (dragEvent.getAction()) {
            case DragEvent.ACTION_DRAG_LOCATION:
                //当拖拽时，触点在靠近屏幕边缘时自动切换到下一页
                //设置1000ms避免一直切换page
                if (System.currentTimeMillis() - lastChangeTime > 1000) {
                    if (dragEvent.getX() < 150) {//已经拖拽到靠左边，尝试滑动到前一页
                        pageChange.pageChange("left");
                    } else if (dragEvent.getX() > 860) {//已经拖拽到靠右边，尝试滑动到后一页
                        pageChange.pageChange("right");
                    }
                    lastChangeTime = System.currentTimeMillis();
                }
                break;
            case DragEvent.ACTION_DROP:
                dragDisplace(view, dragEvent);
                break;
        }
        return true;
    }

    /**
     * 置换拖拽的view
     *
     * @param view      拖拽的view
     * @param dragEvent 拖拽事件
     */
    private void dragDisplace(View view, DragEvent dragEvent) {
        RecyclerView toRecycleView = null;
        if (view instanceof RecyclerView) {
            toRecycleView = (RecyclerView) view;
        }
        if (toRecycleView == null) {
            return;
        }
        View item = toRecycleView.findChildViewUnder(dragEvent.getX(), dragEvent.getY());
        if (item == null) {
            return;
        }
        ViewDragHelper.DragModel dragModel = (ViewDragHelper.DragModel) dragEvent.getLocalState();
        int fromPosition = dragModel.holder.getAdapterPosition();
        int toPosition = toRecycleView.getChildLayoutPosition(item);

        PagerContentAdapter toAdapter = (PagerContentAdapter) toRecycleView.getAdapter();

        if (fromPosition < 0 || toPosition < 0) {
            return;
        }

        if (dragModel.recyclerView == toRecycleView) {//当前recycleView内部拖拽换位
            if (fromPosition == toPosition) {
                return;
            }
            try {
                List<String> dataSource = toAdapter.getDatas();
                if (fromPosition < toPosition) {
                    for (int i = fromPosition; i < toPosition; i++) {
                        Collections.swap(dataSource, i, i + 1);
                    }
                } else {
                    for (int i = fromPosition; i > toPosition; i--) {
                        Collections.swap(dataSource, i, i - 1);
                    }
                }
                toAdapter.notifyItemMoved(fromPosition, toPosition);

            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.d("recycleView内部拖拽", "fromPosition:" + fromPosition + "\n" + "toPosition:" + toPosition);
        } else {//跨跨recycleView拖拽换位
            PagerContentAdapter fromAdapter = (PagerContentAdapter) dragModel.recyclerView.getAdapter();

            String fData = fromAdapter.getDatas().remove(fromPosition);
            String tData = toAdapter.getDatas().remove(toPosition);

            fromAdapter.getDatas().add(fromPosition, tData);
            toAdapter.getDatas().add(toPosition, fData);

            fromAdapter.notifyItemChanged(fromPosition);
            toAdapter.notifyItemChanged(toPosition);

            Log.d("跨recycleView拖拽", "fromPosition:" + fromPosition + "\n" + "toPosition:" + toPosition);
        }
        //TODO 通知保存新的排序根据业务需求进行下一步操作
    }

    /**
     * 当拖拽dragEvent.getX()达到设置的边界时，viewpager自动切换到当前页的上一页或者下一页
     */
    public interface onPageChange {
        void pageChange(String orientation);
    }
}
