package com.unir.grupo2.myzeancoach.ui.MCooperativeSol.suggestionCoachList;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.domain.model.CommentsCoach;

import java.util.List;

/**
 * Created by Cesar on 11/03/2017.
 */

public class SuggestionListAdapter extends RecyclerView.Adapter<SuggestionItemViewHolder>  {

    private List<CommentsCoach> suggestionItemList;
    private Context context;

    public SuggestionListAdapter(Context context, List<CommentsCoach> suggestionItemList) {
        this.context = context;
        this.suggestionItemList = suggestionItemList;
    }

    @Override
    public SuggestionItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.coop_sol_amend_dilemma_list_item_layout, viewGroup, false);
        final SuggestionItemViewHolder suggestionViewHolder = new SuggestionItemViewHolder(view);

        return suggestionViewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(SuggestionItemViewHolder viewHolder, int position) {
        viewHolder.bind(suggestionItemList.get(position));
    }

    @Override
    public int getItemCount() {
        return suggestionItemList.size();
    }
}

