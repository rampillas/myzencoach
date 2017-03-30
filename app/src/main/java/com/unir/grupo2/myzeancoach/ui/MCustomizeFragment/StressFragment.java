package com.unir.grupo2.myzeancoach.ui.MCustomizeFragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.domain.MCustomize.Remainders.Stress.SetAnswerUseCase;
import com.unir.grupo2.myzeancoach.domain.MCustomize.Remainders.Stress.UpdateQuestionsListUseCase;
import com.unir.grupo2.myzeancoach.domain.model.StressQuestion;
import com.unir.grupo2.myzeancoach.ui.MCustomizeFragment.stressQuestionsList.StressListAdapter;
import com.unir.grupo2.myzeancoach.ui.MCustomizeFragment.stressQuestionsList.StressQuestionObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscriber;

/**
 * Created by Cesar on 22/02/2017.
 */

public class StressFragment extends Fragment implements StressListAdapter.OnItemClickListener {

    @BindView(R.id.estress_recycler_view)
    RecyclerView recyclerView;
    @Nullable
    @BindView(R.id.floatingActionButton)
    FloatingActionButton floatingActionButton;
    @Nullable
    @BindView(R.id.content)
    RelativeLayout content;
    @Nullable
    @BindView(R.id.loading_layout)
    LinearLayout loadingLayout;
    @Nullable
    @BindView(R.id.error_layout)
    LinearLayout errorLayout;
    StressListAdapter stressListAdapter;
    static String tokenActivo = "";
    static StressQuestionObject stressQuestionObjectInUse = null;
    //elements from database
    List<StressQuestion> stressQuestionsList;
    //local elements for view holder
    private List<StressQuestionObject> stressQuestionObjectList;

    StressFragment.OnPostListener postListener;
    StressQuestionObject stressQuestionActive = null;
    String personal_question_answer;

    @OnClick(R.id.floatingActionButton)
    public void openNewQuestion() {
        //call to open the new fragment
        postListener.onNewQuestionSelected();
    }

    @Override
    public void onSendClick(String answer, StressQuestionObject stressQuestionObject) {
        //call and send the data
        this.stressQuestionActive = stressQuestionObject;
        this.personal_question_answer = answer;
        String question_description="";
        SharedPreferences sharedPref = getContext().getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String token = sharedPref.getString(getString(R.string.PREFERENCES_TOKEN), null);
        String user = sharedPref.getString(getString(R.string.PREFERENCES_USER), null);
        //get the options and create the request body
        String bodyString = "{\n" +
                "\t\"description\": \""+stressQuestionActive.getDescription()+"\", \n" +
                "\t\"user_answer\": \"" + answer + "\"\n" +
                "}";
        RequestBody rb = RequestBody.create(MediaType.parse("text/plain"), bodyString);
        showLoading();
        new SetAnswerUseCase("Bearer " + token, rb, user).execute(new StressFragment.SendAnswer());
        postListener.onSendItemSelected(answer, stressQuestionObject);
    }

    @Override
    public void OnFinalButtonSelected() {
        //todo show mesages

    }

    //we create a interface to implement from activity witch reacts to item clicks
    public interface OnPostListener {
        void onNewQuestionSelected();
        void onFinalButtonPressed();

        void onSendItemSelected(String answer, StressQuestionObject stressQuestionObject);

    }

    @Override
    public void onAttach(Context context) {
        //give a context to the calls from other activities
        super.onAttach(context);
        if (context instanceof StressFragment.OnPostListener) {
            postListener = (StressFragment.OnPostListener) context;
        } else {
            throw new ClassCastException(context.toString() + " must implements StressFragment.OnPostListener");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.stress_layout, null);
        ButterKnife.bind(this, view);
        Log.d("StressFragment:", " created");
        updatedata();
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        return view;
    }

    private void updatedata() {
        showLoading();
        Context context = getActivity();
        SharedPreferences sharedPref = context.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String token = sharedPref.getString(getString(R.string.PREFERENCES_TOKEN), null);
        Log.d("Stress token: ", token);
        new UpdateQuestionsListUseCase("Bearer " + token).execute(new StressFragment.QuestionsSubscriber());

    }

    private final class QuestionsSubscriber extends Subscriber<List<StressQuestion>> {
        //3 callbacks
        //Show the listView
        @Override
        public void onCompleted() {
        }

        //Show the error
        @Override
        public void onError(Throwable e) {
            Log.e("ERROR REMINDERS ", e.toString());
            showError();
        }

        //Update listview datas
        @Override
        public void onNext(List<StressQuestion> stressQuestions) {
            updateList(stressQuestions);
        }
    }

    private final class SendAnswer extends Subscriber<Void> {
        //3 callbacks
        //Show the listView
        @Override
        public void onCompleted() {
        }

        //Show the error
        @Override
        public void onError(Throwable e) {
            Log.e("ERROR Answer ", e.toString());
            showError();
        }

        @Override
        public void onNext(Void aVoid) {
            //mostramos un alert dialog con el mensage
            showMsg();
        }

    }

    private void showMsg() {
        String mensage = "";
        //recuperamos el mensage correspondiente a la respuesta dada
        for (int i = 0; i < stressQuestionActive.getElementos().size(); i++) {
            if (stressQuestionActive.getElementos().get(i).getDescription().equalsIgnoreCase(personal_question_answer))
                mensage = stressQuestionActive.getElementos().get(i).getPopupMessage();
        }

        new AlertDialog.Builder(getContext())
                .setTitle("Coach response")
                .setMessage(mensage)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        showContent();
                        dialog.cancel();
                    }
                })
                .show();
    }

    private void updateList(List<StressQuestion> stressQuestions) {
        Log.d("updateList", "updateList1");
        this.stressQuestionsList = stressQuestions;

        stressQuestionObjectList = new ArrayList<StressQuestionObject>();
        if (stressQuestions != null && stressQuestions.size() > 0) {
            for (int i = 0; i < this.stressQuestionsList.size(); i++) {
                StressQuestion stressQuestionItem = stressQuestions.get(i);
                StressQuestionObject stressQuestionObject = new StressQuestionObject(stressQuestionItem.getDescription(), stressQuestionItem.getUserAnswer(), stressQuestionItem.getQuestions());
                stressQuestionObjectList.add(stressQuestionObject);
                if(i==this.stressQuestionsList.size()-1){
                    StressQuestionObject lastStressQuestionObject = new StressQuestionObject("ENDELEMENT", stressQuestionItem.getUserAnswer(), stressQuestionItem.getQuestions());
                }
            }
        }
        // use a linear layout manager
        stressListAdapter = new StressListAdapter(getContext(), stressQuestionObjectList, this);
        recyclerView.setAdapter(stressListAdapter);
        showContent();
    }

    /**
     * Method used to show error view
     */
    public void showError() {
        content.setVisibility(View.GONE);
        loadingLayout.setVisibility(View.GONE);
        errorLayout.setVisibility(View.VISIBLE);
    }

    /**
     * Method used to show the loading view
     */
    public void showLoading() {
        loadingLayout.setVisibility(View.VISIBLE);
        content.setVisibility(View.GONE);
        errorLayout.setVisibility(View.GONE);
    }

    /**
     * Method used to show the listView
     */
    public void showContent() {
        content.setVisibility(View.VISIBLE);
        loadingLayout.setVisibility(View.GONE);
        errorLayout.setVisibility(View.GONE);
    }
}
