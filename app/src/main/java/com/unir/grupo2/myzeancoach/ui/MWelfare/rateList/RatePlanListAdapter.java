package com.unir.grupo2.myzeancoach.ui.MWelfare.rateList;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.RatingBar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.domain.model.ExerciseWelfare;
import com.unir.grupo2.myzeancoach.domain.model.PlanWelfare;
import com.unir.grupo2.myzeancoach.domain.model.QuestionExerciseWelfare;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cesar on 26/02/2017.
 */

public class RatePlanListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<QuestionExerciseWelfare> questionItemList;
    private ExerciseWelfare exercise;
    private ArrayList<String> userAnswers;
    private float feedbackScore = 0;
    private String feedbackString = "";

    private class VIEWS_TYPES {
        public static final int Normal = 1;
        public static final int Footer = 2;
    }

    boolean isFooter = false;

    public interface OnButtonClickListener {
        public void onButtonClick(PlanWelfare planAmended, ExerciseWelfare exerciseAmended);
    }

    private final RatePlanListAdapter.OnButtonClickListener listener;

    public RatePlanListAdapter(Context context, ExerciseWelfare exercise,
                               OnButtonClickListener listener) {
        this.context = context;
        this.questionItemList = exercise.getQuestionExercises();
        this.exercise = exercise;
        this.listener = listener;
        this.userAnswers = new ArrayList<String>();
}

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == VIEWS_TYPES.Normal) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.welfare_rate_item_list_layout, parent, false);
            return new RatePlanItemViewHolder(view);
        } else if (viewType == VIEWS_TYPES.Footer) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.welfare_rate_list_footer_layout, parent, false);

            return new RatePlanFooterViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof RatePlanFooterViewHolder) {
            RatePlanFooterViewHolder footerHolder = (RatePlanFooterViewHolder) holder;

            footerHolder.rateRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    feedbackScore = rating;
                }
            });

            footerHolder.rateEditText.addTextChangedListener(new TextWatcher() {

                public void afterTextChanged(Editable s) {}

                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {
                }

                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    feedbackString = s.toString();
                }
            });

            footerHolder.sendButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //hide soft keyboard
                    InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                    boolean userAnswersUncompleted = false;

                    if (userAnswers.size() < questionItemList.size()){
                        userAnswersUncompleted = true;
                    }else{
                        for (int i = 0; i < userAnswers.size(); i++){
                            if (userAnswers.get(i).equals("")){
                                userAnswersUncompleted = true;
                            }
                        }
                    }

                    if (userAnswersUncompleted){

                        new AlertDialog.Builder(context)
                                .setTitle(context.getString(R.string.alert_check_fields_empty))
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }else{

                        PlanWelfare plan = getPlanFromPreference();
                        ExerciseWelfare exerciseAmended = null;

                        for (int i = 0;  i < plan.getExercises().size(); i++){
                            if (plan.getExercises().get(i).equals(exercise)){
                                for (int j = 0;  j < plan.getExercises().get(i).getQuestionExercises().size(); j++){
                                    plan.getExercises().get(i).getQuestionExercises().get(j).setResponse(userAnswers.get(j));
                                }
                                plan.getExercises().get(i).setAppreciation(Float.toString(feedbackScore));
                                plan.getExercises().get(i).setFeedback(feedbackString);
                                exerciseAmended =  plan.getExercises().get(i);
                                break;
                            }
                        }
                        listener.onButtonClick(plan, exerciseAmended);
                    }
                }
            });

        } else if (holder instanceof RatePlanItemViewHolder) {
            final QuestionExerciseWelfare questionItem = questionItemList.get(position);
            RatePlanItemViewHolder itemHolder = (RatePlanItemViewHolder) holder;

            itemHolder.questionTextView.setText(questionItem.getQuestion());

            itemHolder.answerEditText.addTextChangedListener(new TextWatcher() {

                public void afterTextChanged(Editable s) {}

                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {
                }

                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (userAnswers.size() >= position + 1){
                        userAnswers.set(position, s.toString());
                    }else {
                        userAnswers.add(s.toString());
                    }
                }
            });

        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionFooter(position)) {
            return VIEWS_TYPES.Footer;
        }
        return VIEWS_TYPES.Normal;

    }

    @Override
    public int getItemCount() {
        return questionItemList.size() + 1;
    }


    private boolean isPositionFooter(int position) {
        if (position == questionItemList.size()) {
            return true;
        } else {
            return false;
        }
    }

    private PlanWelfare getPlanFromPreference(){
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        String savedPlan = sharedPref.getString(context.getString(R.string.PREFERENCES_CURRENT_PLAN_WELFARE), null);
        Type type = new TypeToken<PlanWelfare>() {}.getType();
        Gson gson = new Gson();
        PlanWelfare plan = gson.fromJson(savedPlan, type);

        return plan;
    }

}
