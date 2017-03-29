package com.unir.grupo2.myzeancoach.ui.MCooperativeSol.coachList;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.domain.model.Dilemma;

import java.util.List;

/**
 * Created by Cesar on 11/03/2017.
 */

public class CoachListAdapter extends RecyclerView.Adapter<CoachViewHolder>  {

    private List<Dilemma> dilemmaPostItemList;
    private Context context;

    public CoachListAdapter(Context context, List<Dilemma> dilemmaPostItemList) {
        this.context = context;
        this.dilemmaPostItemList = dilemmaPostItemList;
    }

    @Override
    public CoachViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.coop_sol_coach_list_item, viewGroup, false);
        final CoachViewHolder dilemmaPostItemViewHolder = new CoachViewHolder(view);

        return dilemmaPostItemViewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(CoachViewHolder viewHolder, int position) {
        viewHolder.bind(dilemmaPostItemList.get(position), context);
    }

    @Override
    public int getItemCount() {
        return dilemmaPostItemList.size();
    }
}

