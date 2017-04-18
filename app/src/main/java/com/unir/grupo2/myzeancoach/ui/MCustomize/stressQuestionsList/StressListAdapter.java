package com.unir.grupo2.myzeancoach.ui.MCustomize.stressQuestionsList;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unir.grupo2.myzeancoach.R;

import java.util.List;

/**
 * Created by andres on 16/03/2017.
 */

public class StressListAdapter extends RecyclerView.Adapter<StressItemViewHolder> {
    private Context context;
    private List<StressQuestionObject> stressItemList;
    private StressListAdapter thisAdapter = this;

    public interface OnItemClickListener {
        public void onSendClick(String answer, StressQuestionObject stressQuestionObject);

        public void OnFinalButtonSelected();
    }

    private final StressListAdapter.OnItemClickListener listener;

    public StressListAdapter(Context context, List<StressQuestionObject> questionsStressItemList, StressListAdapter.OnItemClickListener listener) {
        this.context = context;
        this.stressItemList = questionsStressItemList;
        this.listener = listener;
    }

    //We create the viewHolder
    @Override
    public StressItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stress_questions_card, parent, false);
        final StressItemViewHolder stressItemViewHolder = new StressItemViewHolder(view);

        return stressItemViewHolder;
    }


    @Override
    public void onBindViewHolder(StressItemViewHolder stressItemViewHolder, int position) {
        final StressQuestionObject questionsStress = stressItemList.get(position);
        stressItemViewHolder.bind(stressItemList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return stressItemList.size();
    }
}
