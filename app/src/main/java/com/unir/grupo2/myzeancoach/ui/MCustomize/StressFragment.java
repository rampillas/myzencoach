package com.unir.grupo2.myzeancoach.ui.MCustomize;

import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.domain.MCustomize.Remainders.Stress.GetCoachResponseUseCase;
import com.unir.grupo2.myzeancoach.domain.MCustomize.Remainders.Stress.SetAnswerUseCase;
import com.unir.grupo2.myzeancoach.domain.MCustomize.Remainders.Stress.UpdateQuestionsListUseCase;
import com.unir.grupo2.myzeancoach.domain.model.StressCoachResponse;
import com.unir.grupo2.myzeancoach.domain.model.StressQuestion;
import com.unir.grupo2.myzeancoach.domain.model.StressQuestionsListPojo;
import com.unir.grupo2.myzeancoach.domain.utils.Utils;
import com.unir.grupo2.myzeancoach.ui.MCustomize.stressQuestionsList.StressListAdapter;
import com.unir.grupo2.myzeancoach.ui.MCustomize.stressQuestionsList.StressQuestionObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscriber;

import static com.unir.grupo2.myzeancoach.domain.utils.Constants.BASE_URL_CUSTOMIZE_STRESS;
import static com.unir.grupo2.myzeancoach.domain.utils.Constants.URL_SERVER;

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
    @Nullable
    @BindView(R.id.noplan)
    LinearLayout noPlan;
    //elements from database
    List<StressQuestion> stressQuestionsList;
    //local elements for view holder
    private List<StressQuestionObject> stressQuestionObjectList;
    // Variables for scroll listener
    private boolean userScrolled = true;
    private int pastVisiblesItems, visibleItemCount, totalItemCount;
    private String nextData = null;
    StressListAdapter stressListAdapter;
    StressQuestionsListPojo stressQuestionsListPojo = null;
    //local elements for view holder
    private List<StressQuestionObject> stressQuestions;
    List<StressQuestion> stressQuestionList = null;
    LinearLayoutManager layoutManager;
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
        String token = Utils.getTokenFromPreference(getContext());
        String user = Utils.getUserFromPreference(getContext());
        //get the options and create the request body
        String bodyString = "{\n" +
                "\t\"description\": \"" + stressQuestionActive.getDescription() + "\", \n" +
                "\t\"user_answer\": \"" + answer + "\"\n" +
                "}";
        RequestBody rb = RequestBody.create(MediaType.parse("text/plain"), bodyString);
        showLoading();
        new SetAnswerUseCase("Bearer " + token, rb, user).execute(new StressFragment.SendAnswer());
        postListener.onSendItemSelected(answer, stressQuestionObject);
    }

    @Override
    public void OnFinalButtonSelected() {
        showLoading();
        String token = Utils.getTokenFromPreference(getContext());
        new GetCoachResponseUseCase("Bearer " + token).execute(new StressFragment.CoachResponseSubscriber());

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
        updatedata(URL_SERVER + BASE_URL_CUSTOMIZE_STRESS);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        return view;
    }

    private void updatedata(String url) {
        showLoading();
        String token = Utils.getTokenFromPreference(getContext());
        Log.d("Stress token: ", token);
        new UpdateQuestionsListUseCase(url, "Bearer " + token).execute(new StressFragment.QuestionsSubscriber());

    }

    private final class QuestionsSubscriber extends Subscriber<StressQuestionsListPojo> {
        //3 callbacks
        //Show the listView
        @Override
        public void onCompleted() {
        }

        //Show the error
        @Override
        public void onError(Throwable e) {
            Log.e("ERROR STRESS ", e.toString());
            e.printStackTrace();
            showError();
        }

        //Update listview datas
        @Override
        public void onNext(StressQuestionsListPojo stressQuestions) {
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

    private final class CoachResponseSubscriber extends Subscriber<List<StressCoachResponse>> {
        //3 callbacks
        //Show the listView
        @Override
        public void onCompleted() {
        }

        //Show the error
        @Override
        public void onError(Throwable e) {
            Log.e("ERROR Coach RESPONSE ", e.toString());
            showError();
        }

        //Update listview datas
        @Override
        public void onNext(List<StressCoachResponse> stressCoachResponses) {
            showListMsg(stressCoachResponses);
        }
    }

    private void showListMsg(List<StressCoachResponse> stressCoachResponses) {
        String mensaje = "";
        for (int i = 0; i < stressCoachResponses.size(); i++) {
            if (stressCoachResponses.get(i).getActive() == 1)
                mensaje = mensaje + stressCoachResponses.get(i).getDescription() + "\r\n";
        }
        new AlertDialog.Builder(getContext())
                .setTitle("Coach response")
                .setMessage(mensaje)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        showContent();
                        dialog.cancel();
                    }
                })
                .show();
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

    private void updateList(StressQuestionsListPojo stressQuestionsListPojo) {
        Log.d("updateList", "updateList1");
        this.stressQuestionsListPojo = stressQuestionsListPojo;
        // use a linear layout manager

        if (stressQuestionsListPojo == null || stressQuestionsListPojo.getCount() <= 0) {

            showNoPlan();

        } else {

            nextData = stressQuestionsListPojo.getNext();

            List<StressQuestion> stressQuestions = stressQuestionsListPojo.getResults();
            Log.d("NUMBER Stress: ", String.valueOf(stressQuestions.size()));
            //Check if there some plan has already been finished
            List<StressQuestion> listNoFinished = new ArrayList<>();
            for (StressQuestion question : stressQuestions) {
                //se añaden todos
                if (true) {
                    listNoFinished.add(question);
                    Log.d("AÑADIDO", question.getDescription());
                }
            }
            //First time data are requested
            if (stressQuestionList == null) {
                stressQuestionList = listNoFinished;
                stressQuestionObjectList = new ArrayList<>();
                if (!listNoFinished.isEmpty()) {
                    this.stressQuestions = new ArrayList<StressQuestionObject>();
                    for (int i = 0; i < listNoFinished.size(); i++) {
                        StressQuestion stressQuestionItem = listNoFinished.get(i);
                        StressQuestionObject stressQuestionObject = new StressQuestionObject(stressQuestionItem.getDescription(), stressQuestionItem.getUserAnswer(), stressQuestionItem.getQuestions());
                        stressQuestionObjectList.add(stressQuestionObject);
                        if (i == listNoFinished.size() - 1) {
                            StressQuestionObject lastStressQuestionObject = new StressQuestionObject("ENDELEMENT", stressQuestionItem.getUserAnswer(), stressQuestionItem.getQuestions());
                            stressQuestionObjectList.add(lastStressQuestionObject);
                        }
                    }
                    stressListAdapter = new StressListAdapter(getContext(), stressQuestionObjectList, this);
                    recyclerView.setAdapter(stressListAdapter);
                    showContent();
                    implementScrollListener();
                } else {
                    showNoPlan();
                }
                //No first time
            } else {
                for (StressQuestion question : listNoFinished) {
                    stressQuestionList.add(question);
                    StressQuestionObject rio = new StressQuestionObject(question.getDescription(), question.getUserAnswer(), question.getQuestions());
                    this.stressQuestions.add(rio);
                }
                stressListAdapter.notifyDataSetChanged();
            }
        }
    }

    private void implementScrollListener() {
        recyclerView
                .addOnScrollListener(new RecyclerView.OnScrollListener() {

                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView,
                                                     int newState) {

                        super.onScrollStateChanged(recyclerView, newState);

                        // If scroll state is touch scroll then set userScrolled
                        // true
                        if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                            userScrolled = true;
                        }
                    }

                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx,
                                           int dy) {

                        super.onScrolled(recyclerView, dx, dy);
                        // Here get the child count, item count and visibleitems
                        // from layout manager

                        visibleItemCount = layoutManager.getChildCount();
                        totalItemCount = layoutManager.getItemCount();
                        pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();

                        // Now check if userScrolled is true and also check if
                        // the item is end then update recycler view and set
                        // userScrolled to false
                        if (userScrolled
                                && (visibleItemCount + pastVisiblesItems) == totalItemCount) {
                            userScrolled = false;

                            if (nextData != null) {
                                updatedata(nextData);
                            }
                        }
                    }

                });
    }

    /**
     * Method used to show error view
     */
    public void showError() {
        noPlan.setVisibility(View.GONE);
        content.setVisibility(View.GONE);
        loadingLayout.setVisibility(View.GONE);
        errorLayout.setVisibility(View.VISIBLE);

    }

    /**
     * Method used to show the loading view
     */
    public void showLoading() {
        noPlan.setVisibility(View.GONE);
        loadingLayout.setVisibility(View.VISIBLE);
        content.setVisibility(View.GONE);
        errorLayout.setVisibility(View.GONE);
    }

    /**
     * Method used to show the listView
     */
    public void showContent() {
        noPlan.setVisibility(View.GONE);
        content.setVisibility(View.VISIBLE);
        loadingLayout.setVisibility(View.GONE);
        errorLayout.setVisibility(View.GONE);
    }

    /**
     * Method used to show error view
     */
    public void showNoPlan() {
        noPlan.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        errorLayout.setVisibility(View.GONE);
        loadingLayout.setVisibility(View.GONE);
    }
}
