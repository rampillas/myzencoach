package com.unir.grupo2.myzeancoach.ui.MCustomize.remaindersList;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.unir.grupo2.myzeancoach.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    @Nullable
    @BindView(R.id.addComent)
    Button addComent;
    RemaindersListAdapter.OnItemClickListener listener = null;
    RemainderItemObject remainderItem = null;

    public RemaindersItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(final RemainderItemObject remainderItemObject, final RemaindersListAdapter.OnItemClickListener listener) {
        this.listener = listener;
        this.remainderItem = remainderItemObject;
        remainderTitle.setText(remainderItemObject.getTitle());
        remainderObservations.setText(remainderItemObject.getDescription());
        remainderComplete.setEnabled(true);
        remainderComplete.setText(R.string.REMAINDERS_MASK_AS_DONE);
        remainderComplete.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.checked, 0, 0, 0);
        if (remainderItemObject.getCompleted()) {
            remainderComplete.setText(R.string.completed);
            remainderComplete.setEnabled(false);
        } else {
            remainderComplete.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.checked_no, 0, 0, 0);
        }
        if (remainderItemObject.isObservationsEnabled) {
            addComent.setVisibility(View.VISIBLE);
        }


    }

    @OnClick(R.id.addComent)
    public void addObservations() {
        listener.onAddObservations(remainderItem);
    }

    @OnClick(R.id.complete_button)
    public void pulsado() {
        remainderComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onCompleteClick(remainderItem);
            }
        });

    }
}
