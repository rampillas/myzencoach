package com.unir.grupo2.myzeancoach.ui.MLeisure;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.domain.MLeisure.CreateCommentUseCase;
import com.unir.grupo2.myzeancoach.domain.model.CommentEvent;
import com.unir.grupo2.myzeancoach.domain.model.Event;
import com.unir.grupo2.myzeancoach.domain.utils.Utils;
import com.unir.grupo2.myzeancoach.ui.MLeisure.commentList.CommentListAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscriber;

import static com.unir.grupo2.myzeancoach.domain.utils.Utils.isNewConnection;

/**
 * Created by Cesar on 13/03/2017.
 */

public class CommentActivity extends AppCompatActivity {

    @BindView(R.id.comment_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.content_relativeLayout)
    RelativeLayout contentRelativeLayout;
    @BindView(R.id.loading_layout)
    LinearLayout loadingLayout;
    @BindView(R.id.error_layout)
    LinearLayout errorLayout;
    @BindView(R.id.add_comment_editText)
    EditText addCommentEditText;

    private ArrayList<CommentEvent> commentItemList;
    private CommentListAdapter commentListAdapter;

    private Event event;
    private String comment;
    private String updateDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_comment_list_layout);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Intent intent = getIntent();
        event = (Event) intent.getParcelableExtra("EVENT");

        commentItemList = event.getComments();

        if (commentItemList != null && commentItemList.size() > 0) {
            setUpRecyclerView();
        }

        showContent();


        addCommentEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (addCommentEditText.getRight() - addCommentEditText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                        if (addCommentEditText.getText().toString().trim().length() == 0) {
                            Utils.closeSoftKeyboard(CommentActivity.this);
                            showDialogFillOutField();
                        } else {
                            Utils.closeSoftKeyboard(CommentActivity.this);
                            comment = addCommentEditText.getText().toString().trim();
                            addCommentData(comment);
                            addCommentEditText.setText("");
                        }

                        return true;
                    }
                }
                return false;
            }
        });

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
                Utils.closeSoftKeyboard(CommentActivity.this);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    private void showDialogFillOutField() {
        new AlertDialog.Builder(this)
                .setMessage(getString(R.string.alert_fill_out_comment))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void setUpRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        commentListAdapter = new CommentListAdapter(this, commentItemList);
        recyclerView.setAdapter(commentListAdapter);
    }

    private void setData(){
        event.setComments(commentItemList);
        Intent resultData = new Intent();
        resultData.putExtra("EVENT", event);
        setResult(Activity.RESULT_OK, resultData);
    }

    private void addCommentData(String comment) {
        showLoading();

        String userName = Utils.getUserFromPreference(this);
        String token = "Bearer " + Utils.getTokenFromPreference(this);

        updateDate = Utils.dateNowForBackend();

        String text = "{\n" +
                "\t\"user_owner\": \"" + event.getUser() + "\",\n" +
                "\t\"title\": \"" + event.getTitle() + "\",\n" +
                "\t\"date\": \"" + updateDate + "\",\n" +
                "\t\"description\": \"" + comment.replaceAll("\"","\\\\\"") + "\"\n" +
                "}\n";

        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), text);
        new CreateCommentUseCase(userName, token, body).execute(new CommentSubscriber());
    }

    private void updateListView() {
        CommentEvent newComment = new CommentEvent(Utils.getUserFromPreference(this),Utils.dateFormat(updateDate), comment);

        if (commentItemList != null && !commentItemList.isEmpty()) {
            commentItemList.add(newComment);
            commentListAdapter.notifyItemInserted(commentItemList.size() - 1);
        } else {
            commentItemList = new ArrayList<>();
            commentItemList.add(newComment);
            setUpRecyclerView();
        }
    }

    /**
     * Method used to show error view
     */
    public void showError() {
        errorLayout.setVisibility(View.VISIBLE);
        contentRelativeLayout.setVisibility(View.GONE);
        loadingLayout.setVisibility(View.GONE);
    }

    /**
     * Method used to show the loading view
     */
    public void showLoading() {
        loadingLayout.setVisibility(View.VISIBLE);
        contentRelativeLayout.setVisibility(View.GONE);
        errorLayout.setVisibility(View.GONE);
    }

    /**
     * Method used to show the listView
     */
    public void showContent() {
        contentRelativeLayout.setVisibility(View.VISIBLE);
        loadingLayout.setVisibility(View.GONE);
        errorLayout.setVisibility(View.GONE);
    }

    private final class CommentSubscriber extends Subscriber<Void> {
        //Show the listView
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
            updateListView();
            setData();
        }
    }

}