package com.unir.grupo2.myzeancoach.ui.MCooperativeSol.suggestionCoachList;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.domain.utils.Utils;
import com.unir.grupo2.myzeancoach.domain.model.CommentsCoach;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cesar on 11/03/2017.
 */

public class SuggestionItemViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.date_textView) TextView dateTextView;
    @BindView(R.id.description_textView) TextView descriptionTextView;

    public SuggestionItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void bind(final CommentsCoach suggestionItem) {
        dateTextView.setText(Utils.dateFormat(suggestionItem.getDate()));
        descriptionTextView.setText(suggestionItem.getDescription());
    }

}
