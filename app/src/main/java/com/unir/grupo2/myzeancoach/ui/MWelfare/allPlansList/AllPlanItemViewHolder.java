package com.unir.grupo2.myzeancoach.ui.MWelfare.allPlansList;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.domain.model.PlanWelfare;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by vaiojr on 15/03/2017.
 */

public class AllPlanItemViewHolder extends RecyclerView.ViewHolder {

   @BindView(R.id.name_textView) TextView namePlanTextView;
   @BindView(R.id.isOngoing_textView) TextView isOngoingPlanTextView;

    public AllPlanItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(final PlanWelfare plan, final boolean isThisPlanOngling, final boolean isThereAnOngoing,
                          final AllPlanListAdapter.OnItemPlanClickListener listener, final Context context) {

        namePlanTextView.setText(plan.getDescription());
        if (isThisPlanOngling){
            isOngoingPlanTextView.setVisibility(View.VISIBLE);
        }

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isThisPlanOngling || !isThereAnOngoing){
                    listener.onItemPlanClick(plan);
                }else{
                    Toast.makeText(context, context.getString(R.string.choose_ongoing_one), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
