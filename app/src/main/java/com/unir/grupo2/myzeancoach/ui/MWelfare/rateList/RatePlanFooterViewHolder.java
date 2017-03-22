package com.unir.grupo2.myzeancoach.ui.MWelfare.rateList;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.unir.grupo2.myzeancoach.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cesar on 26/02/2017.
 */

public class RatePlanFooterViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.rate_ratingBar) RatingBar rateRatingBar;
    @BindView(R.id.rate_editText) EditText rateEditText;
    @BindView(R.id.send__button) Button sendButton;

    public RatePlanFooterViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
