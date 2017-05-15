package com.unir.grupo2.myzeancoach.ui.MCooperativeSol;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.domain.MCooperativeSol.AddCommentUseCase;
import com.unir.grupo2.myzeancoach.domain.MCooperativeSol.AddProContUserCase;
import com.unir.grupo2.myzeancoach.domain.model.Comment;
import com.unir.grupo2.myzeancoach.domain.model.Cons;
import com.unir.grupo2.myzeancoach.domain.model.Dilemma;
import com.unir.grupo2.myzeancoach.domain.model.Pro;
import com.unir.grupo2.myzeancoach.domain.utils.DialogCustomFragment;
import com.unir.grupo2.myzeancoach.domain.utils.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscriber;

import static com.unir.grupo2.myzeancoach.domain.utils.Utils.isNewConnection;

/**
 * Created by Cesar on 31/03/2017.
 */

public class AddCommentActivity  extends AppCompatActivity implements DialogCustomFragment.OnStopLister{

    @BindView(R.id.content_layout) LinearLayout contentLayout;
    @BindView(R.id.loading_layout) LinearLayout loadingLinearLayout;
    @BindView(R.id.error_layout) LinearLayout errorLinearLayout;
    @BindView(R.id.comment_editText) EditText solutionEditText;
    @BindView(R.id.pros_editText) EditText prosEditText;
    @BindView(R.id.cons_editText) EditText consEditText;
    @BindView(R.id.save_solution_button) Button saveSolutionButton;

    private Dilemma dilemma;
    private String commentDate;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coop_sol_add_comment_activity);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Intent intent = getIntent();
        dilemma = intent.getParcelableExtra("DILEMMA");

        showContent();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    @Override
    protected void onStart(){
        super.onStart();
        if (isNewConnection()){
            Utils.launchConnectionUseCase(this);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Utils.closeSoftKeyboard(AddCommentActivity.this);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.save_solution_button)
    public void saveSolution(Button button) {
        Utils.closeSoftKeyboard(this);
        if (solutionEditText.getText().toString().trim().length() == 0 ||
                prosEditText.getText().toString().trim().length() == 0 ||
                consEditText.getText().toString().trim().length() == 0){
            showDialogFillOutFields();
        }else{
            showLoading();
            sendCommentData();
        }
    }
    private void sendCommentData(){
        showLoading();

        String userName = Utils.getUserFromPreference(this);
        String token = "Bearer " + Utils.getTokenFromPreference(this);
        commentDate = Utils.dateNowForBackend();


        String commentText = "{\n" +
                "\t\"username\": \"" + dilemma.getNickUser() + "\",\n" +
                "\t\"title\": \"" + dilemma.getTitle() + "\",\n" +
                "\t\"comment\": {\n" +
                "\t\t\"nick_user\": \"" + Utils.getUserFromPreference(this) + "\",\n" +
                "\t\t\"date\": \"" + commentDate + "\",\n" +
                "\t\t\"description\": \"" + solutionEditText.getText().toString().trim().replaceAll("\"","\\\\\"") + "\",\n" +
                "\t\t\"like\": false,\n" +
                "\t\t\"feedback\": \"\",\n" +
                "\t\t\"date_feedback\": \"\"\n" +
                "\t}\n" +
                "}\n";

        RequestBody commentbody = RequestBody.create(MediaType.parse("text/plain"), commentText);

        new AddCommentUseCase(userName,token, commentbody).execute(new AddCommentSubscriber());
    }

    private void sendProConData(){
        String userName = Utils.getUserFromPreference(this);
        String token = "Bearer " + Utils.getTokenFromPreference(this);
        commentDate = Utils.dateNowForBackend();

        String prosText = "{\n" +
                "\t\"nick_user\": \"" + Utils.getUserFromPreference(this) + "\",\n" +
                "\t\"description\": \"" + solutionEditText.getText().toString().trim().replaceAll("\"","\\\\\"") + "\",\n" +
                "\t\"pro\": {\n" +
                "\t\t\"description\": \"" + prosEditText.getText().toString().trim().replaceAll("\"","\\\\\"") + "\"\n" +
                "\t}\n" +
                "}\n";

        String consText = "{\n" +
                "\t\"nick_user\": \"" + Utils.getUserFromPreference(this) + "\",\n" +
                "\t\"description\": \"" + solutionEditText.getText().toString().trim().replaceAll("\"","\\\\\"") + "\",\n" +
                "\t\"con\": {\n" +
                "\t\t\"description\": \"" + consEditText.getText().toString().trim().replaceAll("\"","\\\\\"") + "\"\n" +
                "\t}\n" +
                "}\n";

        RequestBody prosbody = RequestBody.create(MediaType.parse("text/plain"), prosText);
        RequestBody consbody = RequestBody.create(MediaType.parse("text/plain"), consText);

        new AddProContUserCase(userName,token, prosbody, consbody).execute(new AddProConsSubscriber());
    }

    /**
     * Method used to show error view
     */
    public void showError() {
        errorLinearLayout.setVisibility(View.VISIBLE);
        contentLayout.setVisibility(View.GONE);
        loadingLinearLayout.setVisibility(View.GONE);
    }

    /**
     * Method used to show the loading view
     */
    public void showLoading() {
        loadingLinearLayout.setVisibility(View.VISIBLE);
        contentLayout.setVisibility(View.GONE);
        errorLinearLayout.setVisibility(View.GONE);
    }

    public void showContent() {
        contentLayout.setVisibility(View.VISIBLE);
        loadingLinearLayout.setVisibility(View.GONE);
        errorLinearLayout.setVisibility(View.GONE);
    }

    private void setReturnData(){
        Comment solution = new Comment();
        solution.setDescription(solutionEditText.getText().toString().trim());
        solution.setFeedback("");
        ArrayList<Pro> prosArray = new ArrayList<>();
        ArrayList<Cons> consArray = new ArrayList<>();
        Pro pro = new Pro();
        pro.setDescription(prosEditText.getText().toString().trim());
        Cons con = new Cons();
        con.setDescription(consEditText.getText().toString().trim());
        prosArray.add(pro);
        consArray.add(con);
        solution.setPros(prosArray);
        solution.setCons(consArray);
        solution.setLike(false);
        solution.setNickUser(Utils.getUserFromPreference(this));
        solution.setDate(commentDate);

        if (dilemma.getComments() != null){
            dilemma.getComments().add(solution);
        }else{
            ArrayList<Comment> solutionsList = new ArrayList<>();
            solutionsList.add(solution);
            dilemma.setComments(solutionsList);
        }

        Intent resultData = new Intent();
        resultData.putExtra("DILEMMA", dilemma);
        setResult(Activity.RESULT_OK, resultData);
    }

    private void showDialogFillOutFields(){
        new AlertDialog.Builder(this)
                .setMessage(getString(R.string.alert_fill_out_all_fields))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void showFinishDialog(){
        // close existing dialog fragments
        FragmentManager manager = getSupportFragmentManager();
        Fragment frag = manager.findFragmentByTag("fragment_dialog");
        if (frag != null) {
            manager.beginTransaction().remove(frag).commit();
        }
        DialogCustomFragment dialog = new DialogCustomFragment();
        Bundle args = new Bundle();
        args.putString("TITLE",getString(R.string.alert_title_solution_added));
        args.putString("DESCRIPTION",getString(R.string.alert_message_solution_added));
        dialog.setArguments(args);
        dialog.show(manager, "fragment_dialog");
    }

    @Override
    public void onStopDialog() {
        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private final class AddCommentSubscriber extends Subscriber<Void> {
        //3 callbacks

        @Override
        public void onCompleted() {

        }

        //Show the error
        @Override
        public void onError(Throwable e) {
            showError();
        }

        @Override
        public void onNext(Void aVoid) {
            sendProConData();
        }
    }

    private final class AddProConsSubscriber extends Subscriber<Void> {
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

        @Override
        public void onNext(Void aVoid) {
            showFinishDialog();
            setReturnData();
        }
    }
}


