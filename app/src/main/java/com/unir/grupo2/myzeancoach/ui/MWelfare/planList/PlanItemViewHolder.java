package com.unir.grupo2.myzeancoach.ui.MWelfare.planList;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by vaiojr on 15/03/2017.
 */

public class PlanItemViewHolder extends RecyclerView.ViewHolder {

   /*@BindView(R.id.plan_item_card) CardView cardView;
    @BindView(R.id.imgPlan_imageView) ImageButton positionImageButton;
    @BindView(R.id.plan_textView) TextView planTextView;
    @BindView(R.id.week_textView) TextView weekTextView;*/

    public PlanItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

}
