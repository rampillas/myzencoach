package com.unir.grupo2.myzeancoach.ui.MWelfare;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.domain.MWelfare.FinishPlanUseCase;
import com.unir.grupo2.myzeancoach.domain.MWelfare.UpdateExerciseUseCase;
import com.unir.grupo2.myzeancoach.domain.model.ExerciseWelfare;
import com.unir.grupo2.myzeancoach.domain.model.PlanWelfare;
import com.unir.grupo2.myzeancoach.domain.utils.Constants;
import com.unir.grupo2.myzeancoach.domain.utils.Utils;
import com.unir.grupo2.myzeancoach.ui.MWelfare.rateList.RatePlanListAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscriber;

/**
 * Created by Cesar on 19/03/2017.
 */

public class RatePlanFragment extends Fragment implements RatePlanListAdapter.OnButtonClickListener {

    @BindView(R.id.question_recycler_view)
    RecyclerView questioRecyclerView;
    @BindView(R.id.loading_layout)
    LinearLayout loadingLayout;
    @BindView(R.id.error_layout)
    LinearLayout errorLayout;

    private RatePlanListAdapter questionListAdapter;
    private PlanWelfare planAmended;
    private boolean isAnyExerciseLeft;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.welfare_rate_layout, null);
        ButterKnife.bind(this, view);

        ExerciseWelfare exercise;
        Bundle bundle = getArguments();
        if (bundle != null) {
            exercise = bundle.getParcelable("EXERCISE");
        } else {
            exercise = null;
        }

        questioRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        questioRecyclerView.setLayoutManager(layoutManager);
        questionListAdapter = new RatePlanListAdapter(getActivity(), exercise, this);
        questioRecyclerView.setAdapter(questionListAdapter);

        showContent();

        return view;
    }


    @Override
    public void onButtonClick(PlanWelfare planAmended, ExerciseWelfare exerciseAmended) {
        showLoading();

        for (int i = 0; i < exerciseAmended.getQuestionExercises().size(); i++) {
            exerciseAmended.getQuestionExercises().get(i).setIsAnswered(true);
        }

        for (int i = 0; i < planAmended.getExercises().size(); i++) {
            if (planAmended.getExercises().get(i).equals(exerciseAmended)) {
                planAmended.getExercises().set(i, exerciseAmended);
            }
        }

        this.planAmended = planAmended;

        //Checking if the current plan has been finished checking if the rest of exercises are over.
        isAnyExerciseLeft = false;
        for (int i = 0; i < planAmended.getExercises().size(); i++) {
            for (int j = 0; j < planAmended.getExercises().get(i).getQuestionExercises().size(); j++) {
                if (!planAmended.getExercises().get(i).getQuestionExercises().get(j).getIsAnswered()) {
                    isAnyExerciseLeft = true;
                    break;
                }
            }
        }

        String questionsString = "";

        String question = "";
        String response = "";

        String middle = "\t\t},{\n";
        String end = "\t\t}]\n";

        for (int i = 0; i < exerciseAmended.getQuestionExercises().size(); i++) {
            question = exerciseAmended.getQuestionExercises().get(i).getQuestion();
            response = exerciseAmended.getQuestionExercises().get(i).getResponse();

            questionsString = questionsString +
                    "\t\t\t\"question\": \"" + question + "\",\n" +
                    "\t\t\t\"response\": \"" + response + "\"\n";

            if (i != exerciseAmended.getQuestionExercises().size() - 1) {
                questionsString = questionsString + middle;
            } else {
                questionsString = questionsString + end;
            }
        }

        String text = "{\n" +
                "\t\"description_plan\": \"" + planAmended.getDescription() + "\",\n" +
                "\t\"exercises\": [{\n" +
                "\t\t\"description\": \"" + exerciseAmended.getDescription() + "\",\n" +
                "\t\t\"feedback\": \"" + exerciseAmended.getFeedback() + "\",\n" +
                "\t\t\"appreciation\": \"" + exerciseAmended.getAppreciation() + "\",\n" +
                "\t\t\"questions\": [{\n" +
                questionsString +
                "\t}]\n" +
                "}\n";

        RequestBody body =
                RequestBody.create(MediaType.parse("text/plain"), text);

        new UpdateExerciseUseCase(body,Constants.PRE_TOKEN +  Utils.getTokenFromPreference(getActivity())).execute(new ExerciseSubscriber());
    }

    /**
     * Method used to show error view
     */
    public void showError() {
        questioRecyclerView.setVisibility(View.GONE);
        loadingLayout.setVisibility(View.GONE);
        errorLayout.setVisibility(View.VISIBLE);
    }

    /**
     * Method used to show the loading view
     */
    public void showLoading() {
        loadingLayout.setVisibility(View.VISIBLE);
        questioRecyclerView.setVisibility(View.GONE);
        errorLayout.setVisibility(View.GONE);
    }

    /**
     * Method used to show the listView
     */
    public void showContent() {
        questioRecyclerView.setVisibility(View.VISIBLE);
        loadingLayout.setVisibility(View.GONE);
        errorLayout.setVisibility(View.GONE);
    }

    private void savePlanOnSharedPreference() {
        Gson gson = new Gson();
        String jsonPlan = gson.toJson(planAmended);

        SharedPreferences sharedPref = getContext().getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.PREFERENCES_CURRENT_PLAN_WELFARE), jsonPlan);
        editor.commit();
    }

    private void deletePlanOnSharedPreference(){
        SharedPreferences sharedPref = getContext().getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove(getString(R.string.PREFERENCES_CURRENT_PLAN_WELFARE));
        editor.apply();
    }

    private void showErrorData() {
        showError();
        Toast.makeText(getContext(),
                getString(R.string.error_update_survey),
                Toast.LENGTH_SHORT).show();
    }

    private void showDialogExerciseOver(String title, String message) {
        new AlertDialog.Builder(getContext())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //getActivity().finish();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();

    }

    /*******************WRONG -> USAR USERNAME REAL*****************/
    private void launchFinishPlanUseCase() {
        String text = "{\n" +
                "\t\"description_plan\": \"" + planAmended.getDescription() + "\"\n" +
                "}\n";

        RequestBody body =
                RequestBody.create(MediaType.parse("text/plain"), text);

        new FinishPlanUseCase("ceo", Constants.PRE_TOKEN + Utils.getTokenFromPreference(getActivity()), body).execute(new FinishPlanSubscriber());
    }

    private void setReturnData(boolean isPlanOver) {
        Intent resultData = new Intent();
        resultData.putExtra("IS_PLAN_COMPLETED", isPlanOver);
        resultData.putExtra("PLAN", planAmended);
        getActivity().setResult(Activity.RESULT_OK, resultData);
    }

    private final class ExerciseSubscriber extends Subscriber<Void> {
        //3 callbacks

        //Show the listView
        @Override
        public void onCompleted() {
            if (isAnyExerciseLeft) {
                showContent();
            }
        }

        //Show the error
        @Override
        public void onError(Throwable e) {
            showErrorData();
        }

        @Override
        public void onNext(Void aVoid) {
            savePlanOnSharedPreference();

            if (isAnyExerciseLeft) {
                setReturnData(false);
                showDialogExerciseOver(getString(R.string.dialog_title_survey_sent),
                        getString(R.string.dialog_message_survey_sent));
            } else {
                launchFinishPlanUseCase();
            }
        }
    }

    private final class FinishPlanSubscriber extends Subscriber<Void> {
        //3 callbacks

        //Show the listView
        @Override
        public void onCompleted() {
            showContent();
        }

        //Show the error
        @Override
        public void onError(Throwable e) {
            showErrorData();
        }

        @Override
        public void onNext(Void aVoid) {
            setReturnData(true);
            deletePlanOnSharedPreference();
            showDialogExerciseOver(getString(R.string.dialog_title_survey_sent_plan_over),
                    getString(R.string.dialog_message_survey_sent_over));
        }
    }
}
