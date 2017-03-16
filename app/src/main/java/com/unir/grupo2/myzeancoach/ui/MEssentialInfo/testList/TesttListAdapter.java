package com.unir.grupo2.myzeancoach.ui.MEssentialInfo.testList;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.domain.model.Test;

import java.util.List;

/**
 * Created by Cesar on 24/02/2017.
 */


public class TesttListAdapter extends RecyclerView.Adapter<TestItemViewHolder>{

    private Context context;
    private List<Test> testItemList;
    private TesttListAdapter thisAdapter = this;

    public interface OnItemClickListener{
        public void onItemClick(Test testItem);
    }

    private final OnItemClickListener listener;

    public TesttListAdapter(Context context, List<Test> testItemList, OnItemClickListener listener){
        this.context = context;
        this.testItemList = testItemList;
        this.listener = listener;
    }

    //We create the viewHolder
    @Override
    public TestItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.test_video_item_card_layout, parent, false);
        final TestItemViewHolder testItemViewHolder = new TestItemViewHolder(view);

        return testItemViewHolder;
    }

    @Override
    public void onBindViewHolder(TestItemViewHolder testItemViewHolder, int position) {
        final Test testItem = testItemList.get(position);
        testItemViewHolder.bind(testItemList.get(position),listener);
    }

    @Override
    public int getItemCount() {
        return testItemList.size();
    }
}
