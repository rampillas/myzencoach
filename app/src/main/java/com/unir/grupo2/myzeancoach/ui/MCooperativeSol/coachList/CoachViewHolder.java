package com.unir.grupo2.myzeancoach.ui.MCooperativeSol.coachList;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.domain.utils.Utils;
import com.unir.grupo2.myzeancoach.domain.model.Dilemma;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cesar on 11/03/2017.
 */

public class CoachViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.date_textView) TextView dateTextView;
    @BindView(R.id.state_textView) TextView stateTextView;
    @BindView(R.id.title_textView) TextView titleTextView;
    @BindView(R.id.description_textView) TextView descriptionTextView;

    public CoachViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void bind(final Dilemma dilemmaPostItem, Context context,
                     final CoachListAdapter.OnDilemmaCoachClickListener listener, final int position) {

        dateTextView.setText(Utils.dateFormat(dilemmaPostItem.getDate()));
        titleTextView.setText(dilemmaPostItem.getTitle());
        descriptionTextView.setText(dilemmaPostItem.getDescription());
        switch (dilemmaPostItem.getState()) {
            case "waitingForAnswer":
                stateTextView.setText(context.getString(R.string.state_waiting_for_answer));
                stateTextView.setTextColor(context.getColor(R.color.blueApp));
                break;
            case "refused":
                stateTextView.setText(context.getString(R.string.state_refused));
                stateTextView.setTextColor(context.getColor(R.color.redApp));
                break;
            default:
                stateTextView.setText(context.getString(R.string.state_waiting_for_answer));
                stateTextView.setTextColor(context.getColor(R.color.blueApp));
                break;
        }

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dilemmaPostItem.getState().equals("refused")){
                    listener.onItemCilemmaCoachClick(dilemmaPostItem, position);
                }
            }
        });
    }
}
