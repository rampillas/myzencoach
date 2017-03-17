package com.unir.grupo2.myzeancoach.ui.MCooperativeSol.dilemmaPostList;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.unir.grupo2.myzeancoach.R;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

import static java.util.concurrent.TimeUnit.DAYS;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * Created by Cesar on 11/03/2017.
 */

public class DilemmaPostItemViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.date_textView) TextView dateTextView;
    @BindView(R.id.nick_textView) TextView nickTextView;
    @BindView(R.id.title_textView) TextView titleTextView;
    @BindView(R.id.description_textView) TextView descriptionTextView;
    @BindView(R.id.state_textView) TextView stateTextView;

    public DilemmaPostItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    public void bind(final DilemmaPost dilemmaPostItem,
                     final DilemmaPostListAdapter.OnDilemmaPostClickListener listener,
                     boolean showNick, Context context) {

        if (!showNick){
            nickTextView.setVisibility(View.GONE);
        }
        dateTextView.setText(dilemmaPostItem.getDate());
        nickTextView.setText(dilemmaPostItem.getNick());
        titleTextView.setText(dilemmaPostItem.getTitle());
        descriptionTextView.setText(dilemmaPostItem.getDescription());
        switch (dilemmaPostItem.getState()) {
            case "help_me":
                stateTextView.setText(context.getString(R.string.state_help_me));
                break;
            case "feedback":



                for(int i = 0; i < dilemmaPostItem.getComments().size(); i++){
                    if (dilemmaPostItem.getComments().get(i).isLike()){
                        if (isMore7days(dilemmaPostItem.getComments().get(i).getDateLike())){
                            stateTextView.setText(context.getString(R.string.state_feedback_2));
                        }else{
                            stateTextView.setText(context.getString(R.string.state_feedback_1));
                        }
                        break;
                    }
                }
                break;
            case "completed":
                stateTextView.setText(context.getString(R.string.state_completed));
                break;
            default:
                stateTextView.setText(context.getString(R.string.state_help_me));
                break;
        }
        if (dilemmaPostItem.getState().equals("completed")){

        }
        stateTextView.setText(dilemmaPostItem.getState());


        itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                listener.onItemDilemmaPostClick(dilemmaPostItem);
            }
        });
    }

    private boolean isMore7days(Date date){
        Date now = new Date();
        long MAX_DURATION = MILLISECONDS.convert(7, DAYS);

        long duration = now.getTime() - date.getTime();

        if (duration >= MAX_DURATION) {
            return true;
        }else{
            return false;
        }
    }
}
