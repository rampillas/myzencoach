package com.unir.grupo2.myzeancoach.ui.MCooperativeSol;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.domain.MCooperativeSol.AddFeedbackCommentAndStatusUseCase;
import com.unir.grupo2.myzeancoach.domain.MCooperativeSol.LikeAndStatusDilemmaUseCase;
import com.unir.grupo2.myzeancoach.domain.MCooperativeSol.LikeDilemmaUseCase;
import com.unir.grupo2.myzeancoach.domain.model.Comment;
import com.unir.grupo2.myzeancoach.domain.model.Dilemma;
import com.unir.grupo2.myzeancoach.domain.utils.DialogCustomFragment;
import com.unir.grupo2.myzeancoach.domain.utils.Utils;
import com.unir.grupo2.myzeancoach.ui.MCooperativeSol.dilemmaCommentList.DilemmaCommentListAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscriber;

/**
 * Created by Cesar on 17/03/2017.
 */

public class DilemmaCommentActivity extends AppCompatActivity implements DilemmaCommentListAdapter.OnDilemmaClickListener, DialogCustomFragment.OnStopLister{

    private final static int DILEMMA_REQUEST = 1;

    @BindView(R.id.dilemma_comment_recycler_view) RecyclerView recyclerView;
    @BindView(R.id.floating_action_button) FloatingActionButton addCommentfloatingActionButton;
    @BindView(R.id.no_comments_layout) LinearLayout noCommentsLayout;
    @BindView(R.id.loading_layout) LinearLayout loadingLayout;
    @BindView(R.id.error_layout) LinearLayout errorLayout;

    private ArrayList<Comment> commentItemList;
    private DilemmaCommentListAdapter dilemmaCommentListAdapter;
    private Dilemma dilemma;

    private boolean isDilemmaUpdated;
    private int positionLiked;
    private int positionFeedback;
    private String feedback;
    private String dateUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coop_sol_comment_layout);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Intent intent = getIntent();
        dilemma = (Dilemma) intent.getParcelableExtra("DILEMMA");
        commentItemList = dilemma.getComments();
        boolean isFromDilemma = intent.getBooleanExtra("IS_FROM_MYDILEMMA", false);

        isDilemmaUpdated = false;

        if (dilemma.getComments() != null && dilemma.getComments().size() >0){
            setRecyclerView();
            showContent();
            if (dilemma.getNickUser().equals(Utils.getUserFromPreference(this))){
                addCommentfloatingActionButton.setVisibility(View.GONE);
            }
        }else{
            showNoComments();
            if (dilemma.getNickUser().equals(Utils.getUserFromPreference(this))){
                addCommentfloatingActionButton.setVisibility(View.GONE);
            }
        }

        if (dilemma.getState().equals("completed") || isFromDilemma){
            addCommentfloatingActionButton.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Utils.closeSoftKeyboard(DilemmaCommentActivity.this);
                if (isDilemmaUpdated){
                    setReturnData();
                }
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.floating_action_button)
    void addEvent(View view) {
        launchAddCommentActivity();
    }

    @Override
    public void onClickCheckBox(int position) {

        showLoading();

        positionLiked = position;
        Comment commentLiked = commentItemList.get(position);

        String userName = Utils.getUserFromPreference(this);
        String token = "Bearer " + Utils.getTokenFromPreference(this);
        dateUpdate = Utils.dateNowForBackend();

        String likeText = "{\n" +
                "\t\"title\": \"" + dilemma.getTitle() + "\",\n" +
                "\t\"description\": \"" + commentLiked.getDescription() + "\",\n" +
                "\"date\": \"" + dateUpdate + "\"\n" +
                "}\n";

        RequestBody likeBody = RequestBody.create(MediaType.parse("text/plain"), likeText);

        if (dilemma.getState().equals("acepted")){

            String statusText = "{\n" +
                    "\t\"username\": \"" + userName + "\",\n" +
                    "\t\"title\": \"" + dilemma.getTitle() + "\",\n" +
                    "\t\"state\": \"feedback\"\n" +
                    "}\n";

            RequestBody statusBody = RequestBody.create(MediaType.parse("text/plain"), statusText);
            new LikeAndStatusDilemmaUseCase(userName,token,likeBody,getStatusRequestBody(userName, "feedback")).execute(new LikeAndStatusDilemmaSubscriber

                    ());
        }else{
            new LikeDilemmaUseCase(userName,token,likeBody).execute(new LikeDilemmaSubscriber());
        }
    }

    @Override
    public void onClickFeedback(int position, String feedback) {
        Utils.closeSoftKeyboard(DilemmaCommentActivity.this);
        showLoading();

        this.feedback = feedback;
        positionFeedback = position;
        Comment commentFeedback = commentItemList.get(position);

        String userName = Utils.getUserFromPreference(this);
        String token = "Bearer " + Utils.getTokenFromPreference(this);
        dateUpdate = Utils.dateNowForBackend();

        String feedbackText = "{\n" +
                "\t\"username\": \"" + userName + "\",\n" +
                "\t\"title\": \"" + dilemma.getTitle() + "\",\n" +
                "\t\"description\": \"" + commentFeedback.getDescription() + "\",\n" +
                "\t\"comment\": {\n" +
                "\t\t\"feedback\": \"" + feedback + "\",\n" +
                "\t\t\"date_feedback\": \"" + dateUpdate + "\"\n" +
                "\t}\n" +
                "}\n";

        RequestBody feedbackBody = RequestBody.create(MediaType.parse("text/plain"), feedbackText);
        new AddFeedbackCommentAndStatusUseCase(userName,token,feedbackBody, getStatusRequestBody(userName,"completed"))
                .execute(new FeedbackCommentSubscriber());
    }

    private void setRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        dilemmaCommentListAdapter = new DilemmaCommentListAdapter(this, commentItemList,dilemma, this);
        recyclerView.setAdapter(dilemmaCommentListAdapter);
    }

    private RequestBody getStatusRequestBody(String userName, String status){
        String statusText = "{\n" +
                "\t\"username\": \"" + userName + "\",\n" +
                "\t\"title\": \"" + dilemma.getTitle() + "\",\n" +
                "\t\"state\": \"" + status + "\"\n" +
                "}\n";

        return  RequestBody.create(MediaType.parse("text/plain"), statusText);
    }

    private void launchAddCommentActivity(){
        Intent intent = new Intent(this, AddCommentActivity.class);
        intent.putExtra("DILEMMA",dilemma);
        startActivityForResult(intent, DILEMMA_REQUEST);
    }

    private void setReturnData(){
        setResult(Activity.RESULT_OK);
    }

    @Override
    public void onBackPressed() {
        if (isDilemmaUpdated){
            setReturnData();
        }
        finish();
    }

    private void showDialogFillOutField(){
        new AlertDialog.Builder(this)
                .setMessage(getText(R.string.alert_like))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void showFinishDialog(){
        FragmentManager manager = getSupportFragmentManager();
        Fragment frag = manager.findFragmentByTag("fragment_dialog");
        if (frag != null) {
            manager.beginTransaction().remove(frag).commit();
        }
        DialogCustomFragment dialog = new DialogCustomFragment();
        Bundle args = new Bundle();
        args.putString("TITLE",getString(R.string.alert_title_feedback_completed));
        args.putString("DESCRIPTION",getString(R.string.alert_description_feedback_completed));
        dialog.setArguments(args);
        dialog.show(manager, "fragment_dialog");
    }

    @Override
    public void onStopDialog() {
    }

    public void showNoComments() {
        noCommentsLayout.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        loadingLayout.setVisibility(View.GONE);
        errorLayout.setVisibility(View.GONE);
    }

    public void showContent() {
        recyclerView.setVisibility(View.VISIBLE);
        noCommentsLayout.setVisibility(View.GONE);
        loadingLayout.setVisibility(View.GONE);
        errorLayout.setVisibility(View.GONE);
    }

    /**
     * Method used to show error view
     */
    public void showError() {
        errorLayout.setVisibility(View.VISIBLE);
        loadingLayout.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        noCommentsLayout.setVisibility(View.GONE);
    }

    /**
     * Method used to show the loading view
     */
    public void showLoading() {
        loadingLayout.setVisibility(View.VISIBLE);
        errorLayout.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        noCommentsLayout.setVisibility(View.GONE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**************Module Essential information***************/
        if (requestCode == DILEMMA_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    dilemma = data.getParcelableExtra("DILEMMA");
                    commentItemList.add(dilemma.getComments().get(dilemma.getComments().size() - 1));
                    if (commentItemList.size() == 1){
                        setRecyclerView();
                        showContent();
                    }else{
                        dilemmaCommentListAdapter.notifyDataSetChanged();
                    }
                    isDilemmaUpdated = true;
                }
            }
        }
    }

    private final class LikeDilemmaSubscriber extends Subscriber<Void> {
        //3 callbacks

        @Override
        public void onCompleted() {
            showContent();
        }

        //Show the error
        @Override
        public void onError(Throwable e) {
            showError();
        }

        //Update listview datas
        @Override
        public void onNext(Void aVoid) {
           for (int i = 0; i < commentItemList.size(); i++){
               if (commentItemList.get(i).getLike() && i != positionLiked){
                   commentItemList.get(i).setLike(false);
                   break;
               }
           }
            commentItemList.get(positionLiked).setLike(true);
            commentItemList.get(positionLiked).setDateFeedback(dateUpdate);
            dilemma.setComments(commentItemList);
            dilemmaCommentListAdapter.notifyDataSetChanged();
            setReturnData();
            showDialogFillOutField();
        }
    }

    private final class LikeAndStatusDilemmaSubscriber extends rx.Subscriber<Void> {
        //3 callbacks

        @Override
        public void onCompleted() {
            showContent();
        }

        //Show the error
        @Override
        public void onError(Throwable e) {
            showError();
        }

        //Update listview datas
        @Override
        public void onNext(Void aVoid) {
            for (int i = 0; i < commentItemList.size(); i++){
                if (commentItemList.get(i).getLike() && i != positionLiked){
                    commentItemList.get(i).setLike(false);
                    break;
                }
            }
            commentItemList.get(positionLiked).setLike(true);
            commentItemList.get(positionLiked).setDateFeedback(dateUpdate);
            dilemma.setState("feedback");
            dilemma.setComments(commentItemList);
            dilemmaCommentListAdapter.notifyDataSetChanged();
            setReturnData();
            showDialogFillOutField();
        }
    }

    private final class FeedbackCommentSubscriber extends Subscriber<Void> {
        //3 callbacks

        @Override
        public void onCompleted() {
            showContent();
        }

        //Show the error
        @Override
        public void onError(Throwable e) {
            showError();
        }

        //Update listview datas
        @Override
        public void onNext(Void aVoid) {
            commentItemList.get(positionFeedback).setFeedback(feedback);
            dilemma.setState("completed");
            dilemma.setComments(commentItemList);
            dilemmaCommentListAdapter.notifyDataSetChanged();
            setReturnData();
            showFinishDialog();
        }
    }

}
