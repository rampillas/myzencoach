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

    public void bind(final InterestItem interestItem) {

        checkedTextView.setText(interestItem.getName());
        checkedTextView.setChecked(interestItem.isChecked());

        /*itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                listener.onItemPostClick(postItem);
            }
        });*/

        checkedTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkedTextView.isChecked())
                    checkedTextView.setChecked(false);
                else
                    checkedTextView.setChecked(true);
            }
        });
    }
}
