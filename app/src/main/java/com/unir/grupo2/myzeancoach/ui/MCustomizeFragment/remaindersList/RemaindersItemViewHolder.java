package com.unir.grupo2.myzeancoach.ui.MCustomizeFragment.remaindersList;

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
    RemaindersListAdapter.OnItemClickListener listener = null;
    RemainderItemObject remainderItem = null;

    public RemaindersItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(final RemainderItemObject remainderItemObject, final RemaindersListAdapter.OnItemClickListener listener) {
        this.listener = listener;
        remainderTitle.setText(remainderItemObject.getTitle());
        remainderObservations.setText(remainderItemObject.getDescription());
        if (remainderItemObject.getCompleted()) {
            remainderComplete.setText(R.string.completed);
            remainderComplete.setEnabled(false);
        }else {
            remainderComplete.setCompoundDrawablesWithIntrinsicBounds( R.mipmap.checked, 0, 0, 0);
        }
        if (remainderItemObject.getUser().toString().isEmpty()) {
            remainderComplete.setEnabled(false);
            remainderComplete.setVisibility(View.INVISIBLE);
        }


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
