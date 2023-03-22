package com.drag.demo.ui;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.drag.demo.R;
import com.drag.demo.view.RecycleViewItemOnDragListener;
import com.drag.demo.view.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * created by macbook
 * on 2023/3/21
 * MainActivity
 */
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager2 viewPager = findViewById(R.id.view_pager);

        //模拟数据源
        List<String> datas = new ArrayList<>();
        for (int index = 0; index < 14; index++) {
            datas.add("测试拖拽" + index);
        }
        //将数据源按指定长度分割未多个list
        List<List<String>> dataDetails = splitList(datas, 6);
        int count = datas.size();
        //可以根据行数，动态设置viewPager高度
        int lineNumber = count <= 6 ? (int) Math.ceil(((float) count / 2.0f)) : 3;
        int pageCount = (int) Math.ceil(count / 6.0);

        View.OnDragListener onDragListener = new RecycleViewItemOnDragListener(new RecycleViewItemOnDragListener.onPageChange() {
            @Override
            public void pageChange(String orientation) {
                switch (orientation) {
                    case "left":
                        //切换到上一个pager
                        if (viewPager.getCurrentItem() > 0) {
                            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1, true);
                        }
                        break;
                    case "right":
                        //切换到下一个pager
                        if (viewPager.getCurrentItem() < pageCount) {
                            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
                        }
                        break;
                }
            }
        });
        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(this, dataDetails, onDragListener);
        viewPager.setAdapter(pagerAdapter);
    }

    /**
     * 将list按指定长度分割为多个list
     *
     * @param dataSource 原list
     * @param groupSize  指定长度
     * @return 分割后的list
     */
    public List<List<String>> splitList(List<String> dataSource, int groupSize) {
        int length = dataSource.size();
        // 根据groupSize计算可以分成多少组
        int num = (length + groupSize - 1) / groupSize;
        List<List<String>> newList = new ArrayList<>(num);
        for (int i = 0; i < num; i++) {
            // 开始位置
            int fromIndex = i * groupSize;
            // 结束位置
            int toIndex = Math.min((i + 1) * groupSize, length);
            newList.add(dataSource.subList(fromIndex, toIndex));
        }
        return newList;
    }
}
