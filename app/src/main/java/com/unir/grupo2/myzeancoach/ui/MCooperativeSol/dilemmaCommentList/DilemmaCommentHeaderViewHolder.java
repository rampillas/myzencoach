package com.unir.grupo2.myzeancoach.ui.MCooperativeSol.dilemmaCommentList;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.unir.grupo2.myzeancoach.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cesar on 26/02/2017.
 */


public class DilemmaCommentHeaderViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.title_textView) TextView titleTextView;
    @BindView(R.id.description_textView) TextView descriptionTextView;

    public DilemmaCommentHeaderViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
