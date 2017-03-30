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

    public interface OnDilemmaCoachClickListener{
        public void onItemCilemmaCoachClick(Dilemma dilemma, int position);
    }

    CoachListAdapter.OnDilemmaCoachClickListener listener;

    public CoachListAdapter(Context context, List<Dilemma> dilemmaPostItemList, CoachListAdapter.OnDilemmaCoachClickListener listener) {
        this.context = context;
        this.dilemmaPostItemList = dilemmaPostItemList;
        this.listener = listener;
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
        viewHolder.bind(dilemmaPostItemList.get(position), context, listener, position);
    }

    @Override
    public int getItemCount() {
        return dilemmaPostItemList.size();
    }
}

