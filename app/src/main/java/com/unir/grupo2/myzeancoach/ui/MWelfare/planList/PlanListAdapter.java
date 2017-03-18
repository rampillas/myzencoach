package com.unir.grupo2.myzeancoach.ui.MWelfare.planList;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unir.grupo2.myzeancoach.R;

import java.util.List;

/**
 * Created by vaiojr on 15/03/2017.
 */

public class PlanListAdapter extends RecyclerView.Adapter<PlanItemViewHolder> {

    Context context;
    List<PlanItem> planItemList;
    PlanListAdapter thisAdapter = this;

    public PlanListAdapter(Context context, List<PlanItem> planItemList) {
        this.context = context;
        this.planItemList = planItemList;
    }

    @Override
    public PlanItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.welfare_plan_item_card, parent, false);
        return new PlanItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlanItemViewHolder planItemViewHolder, final int position) {
        final PlanItem planItem = planItemList.get(position);
      /*  planItemViewHolder.weekTextView.setText(String.format(context.getString(R.string.week_info),planItem.getWeek()));
        planItemViewHolder.planTextView.setText(planItem.getNamePlan());*/
    }

    @Override
    public int getItemCount() {
        return planItemList.size();
    }
}
