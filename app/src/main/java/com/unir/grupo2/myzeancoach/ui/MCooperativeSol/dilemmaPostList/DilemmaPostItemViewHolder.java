package com.unir.grupo2.myzeancoach.ui.MCooperativeSol.dilemmaPostList;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void bind(final DilemmaPost dilemmaPostItem,
                     final DilemmaPostListAdapter.OnDilemmaPostClickListener listener,
                     final boolean fromMyDilemma, Context context) {

        if (fromMyDilemma){
            nickTextView.setVisibility(View.GONE);
        }
        dateTextView.setText(dilemmaPostItem.getDate());
        nickTextView.setText(dilemmaPostItem.getNick());
        titleTextView.setText(dilemmaPostItem.getTitle());
        descriptionTextView.setText(dilemmaPostItem.getDescription());
        switch (dilemmaPostItem.getState()) {
            case "help_me":
                stateTextView.setText(context.getString(R.string.state_help_me));
                stateTextView.setTextColor(context.getColor(R.color.blueApp));
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
                stateTextView.setTextColor(context.getColor(R.color.redApp));
                break;
            case "completed":
                stateTextView.setText(context.getString(R.string.state_completed));
                stateTextView.setTextColor(context.getColor(R.color.greenApp));
                break;
            default:
                stateTextView.setText(context.getString(R.string.state_help_me));
                stateTextView.setTextColor(context.getColor(R.color.blueApp));
                break;
        }

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                listener.onItemDilemmaPostClick(dilemmaPostItem,fromMyDilemma);
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
