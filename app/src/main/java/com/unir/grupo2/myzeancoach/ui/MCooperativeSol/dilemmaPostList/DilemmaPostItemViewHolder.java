package com.unir.grupo2.myzeancoach.ui.MCooperativeSol.dilemmaPostList;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.domain.utils.Utils;
import com.unir.grupo2.myzeancoach.domain.model.Dilemma;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    @BindView(R.id.solution_textView) TextView solutionsNumberTextView;
    @BindView(R.id.state_textView) TextView stateTextView;

    public DilemmaPostItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void bind(final Dilemma dilemmaPostItem,
                     final DilemmaPostListAdapter.OnDilemmaPostClickListener listener,
                     final boolean fromMyDilemma, Context context) {

        if (dilemmaPostItem.getComments() != null && !dilemmaPostItem.getComments().isEmpty()){
            solutionsNumberTextView.setVisibility(View.VISIBLE);
            if (dilemmaPostItem.getComments().size() == 1){
                solutionsNumberTextView.setText(context.getString(R.string.number_solutions_singular));
            }else{
                solutionsNumberTextView.setText(context.getString(R.string.number_solutions_plural, dilemmaPostItem.getComments().size()));
            }
        }

        if (fromMyDilemma){
            nickTextView.setVisibility(View.GONE);
        }

        dateTextView.setText(Utils.dateFormat(dilemmaPostItem.getDate()));
        nickTextView.setText(dilemmaPostItem.getNickUser());
        titleTextView.setText(dilemmaPostItem.getTitle());
        descriptionTextView.setText(dilemmaPostItem.getDescription());
        switch (dilemmaPostItem.getState()) {
            case "acepted":
                stateTextView.setText(context.getString(R.string.state_acepted));
                stateTextView.setTextColor(context.getColor(R.color.blueApp));
                break;
            case "feedback":

                for(int i = 0; i < dilemmaPostItem.getComments().size(); i++){
                    if (dilemmaPostItem.getComments().get(i).getLike()){
                        /** CHANGE LIKE_DATE ************/
                        if (dilemmaPostItem.getComments().get(i).getDateFeedback() != null &&
                                !dilemmaPostItem.getComments().get(i).getDateFeedback().equals("")){
                            if (isMore7days(dilemmaPostItem.getComments().get(i).getDateFeedback())){
                                stateTextView.setText(context.getString(R.string.state_feedback_2));
                            }else{
                                stateTextView.setText(context.getString(R.string.state_feedback_1));
                            }
                            break;
                        }
                    }
                }
                stateTextView.setTextColor(context.getColor(R.color.redApp));
                break;
            case "completed":
                stateTextView.setText(context.getString(R.string.state_completed));
                stateTextView.setTextColor(context.getColor(R.color.greenApp));
                break;
            default:
                stateTextView.setText(context.getString(R.string.state_acepted));
                stateTextView.setTextColor(context.getColor(R.color.blueApp));
                break;
        }

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                listener.onItemDilemmaPostClick(dilemmaPostItem);
            }
        });
    }

    private boolean isMore7days(String dateString){
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        Date date = new Date();
        try {
            date = df.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

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
