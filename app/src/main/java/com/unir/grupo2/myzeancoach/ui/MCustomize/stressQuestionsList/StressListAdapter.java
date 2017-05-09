package com.unir.grupo2.myzeancoach.ui.MCustomize.stressQuestionsList;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.domain.utils.FooterSpaceViewHolder;


import java.util.List;

/**
 * Created by andres on 16/03/2017.
 */

public class StressListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<StressQuestionObject> stressItemList;
    private StressListAdapter thisAdapter = this;

    public interface OnItemClickListener {
        public void onSendClick(String answer, StressQuestionObject stressQuestionObject);

        public void OnFinalButtonSelected();
    }

    private final StressListAdapter.OnItemClickListener listener;

    private class VIEWS_TYPES {
        public static final int Normal = 0;
        public static final int Footer = 1;
    }

    public StressListAdapter(Context context, List<StressQuestionObject> questionsStressItemList, StressListAdapter.OnItemClickListener listener) {
        this.context = context;
        this.stressItemList = questionsStressItemList;
        this.listener = listener;
    }

    //We create the viewHolder
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == VIEWS_TYPES.Footer) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_space_for_floating_button_layout, parent, false);
            return new FooterSpaceViewHolder(view);
        }else{
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stress_questions_card, parent, false);
            final StressItemViewHolder stressItemViewHolder = new StressItemViewHolder(view);
            return stressItemViewHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof  StressItemViewHolder){
            final StressQuestionObject questionsStress = stressItemList.get(position);
            ((StressItemViewHolder) holder).bind(stressItemList.get(position), listener);
        }else{

        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionFooter(position)) {
            return VIEWS_TYPES.Footer;
        }
        return VIEWS_TYPES.Normal;
    }

    private boolean isPositionFooter (int position) {
        if (position == stressItemList.size()){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return stressItemList.size() + 1;
    }
}
