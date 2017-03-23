package com.unir.grupo2.myzeancoach.ui.MCustomizeFragment.remaindersList;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.unir.grupo2.myzeancoach.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cesar on 24/02/2017.
 */

public class RemaindersItemViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.title_textView)
    TextView remainderTitle;
    @BindView(R.id.observations_textView)
    TextView remainderObservations;
    @BindView(R.id.complete_button)
    Button remainderComplete;

    public RemaindersItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(final RemainderItemObject remainderItem, final RemaindersListAdapter.OnItemClickListener listener) {

        remainderTitle.setText(remainderItem.getTitle());
        remainderObservations.setText(remainderItem.getDescription());
        if (true ) { //TODO implementar mirar si es true
            remainderComplete.setText(R.string.completed);
            remainderComplete.setEnabled(false);
        }


        remainderComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(remainderItem);
            }
        });
    }
}
