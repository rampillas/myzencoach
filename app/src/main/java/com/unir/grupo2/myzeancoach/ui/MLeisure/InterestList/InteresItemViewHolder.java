package com.unir.grupo2.myzeancoach.ui.MLeisure.InterestList;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckedTextView;

import com.unir.grupo2.myzeancoach.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cesar on 11/03/2017.
 */

public class InteresItemViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.checked_textView) CheckedTextView checkedTextView;

    public InteresItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

}
