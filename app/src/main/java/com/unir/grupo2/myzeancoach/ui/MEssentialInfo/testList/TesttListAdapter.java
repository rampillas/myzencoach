package com.unir.grupo2.myzeancoach.ui.MEssentialInfo.testList;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unir.grupo2.myzeancoach.R;

import java.util.List;

/**
 * Created by Cesar on 24/02/2017.
 **/


public class TesttListAdapter extends RecyclerView.Adapter<TestItemViewHolder>{

    private Context context;
    private List<TestItem> testItemList;
    private TesttListAdapter thisAdapter = this;

    public interface OnItemClickListener{
        public void onItemClick(TestItem testItem);
    }

    private final OnItemClickListener listener;

    public TesttListAdapter(Context context, List<TestItem> testItemList, OnItemClickListener listener){
        this.context = context;
        this.testItemList = testItemList;
        this.listener = listener;
    }

    //We create the viewHolder
    @Override
    public TestItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.test_video_item_card_layout, parent, false);
        final TestItemViewHolder testItemViewHolder = new TestItemViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String videoName = testItemViewHolder.videoName.getText().toString();
                boolean isCOmpleted;
                if (testItemViewHolder.isCOmpleted.getText().toString().equals(context.getString(R.string.completed))){
                    isCOmpleted = true;
                }else{
                    isCOmpleted = false;
                }

                int score = (int) testItemViewHolder.scoreRatingBar.getRating();
                TestItem testItem = new TestItem(videoName,isCOmpleted,score);
                listener.onItemClick(testItem);
            }
        });

        return testItemViewHolder;
    }

    @Override
    public void onBindViewHolder(TestItemViewHolder testItemViewHolder, int position) {
        final TestItem testItem = testItemList.get(position);
        testItemViewHolder.videoName.setText(testItem.getVideoName());
        if (testItem.isCompleted()){
            testItemViewHolder.isCOmpleted.setTextColor(context.getResources().getColor(R.color.greenApp));
            testItemViewHolder.isCOmpleted.setText(R.string.completed);
            testItemViewHolder.scoreRatingBar.setVisibility(View.VISIBLE);
        }else{
            testItemViewHolder.isCOmpleted.setText(R.string.uncompleted);
        }
        testItemViewHolder.scoreRatingBar.setRating(testItem.getScore());
    }

    @Override
    public int getItemCount() {
        return testItemList.size();
    }
}
