package com.unir.grupo2.myzeancoach.ui.MLeisure;

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
import android.widget.Toast;

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.domain.model.CommentEvent;
import com.unir.grupo2.myzeancoach.domain.model.Event;
import com.unir.grupo2.myzeancoach.domain.utils.Utils;
import com.unir.grupo2.myzeancoach.ui.MLeisure.commentList.CommentListAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.unir.grupo2.myzeancoach.R.string.post;

/**
 * Created by Cesar on 13/03/2017.
 */

public class CommentActivity extends AppCompatActivity {

    @BindView(R.id.comment_recycler_view) RecyclerView recyclerView;
    @BindView(R.id.loading_layout) LinearLayout loadingLayout;
    @BindView(R.id.error_layout) LinearLayout errorLayout;
    @BindView(R.id.add_comment_editText) EditText addCommentEditText;

    private List<CommentEvent> commentItemList;
    private CommentListAdapter commentListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_comment_list_layout);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Intent intent = getIntent();
        Event event = (Event) intent.getParcelableExtra("POST");

        commentItemList = event.getComments();

        if (commentItemList != null && commentItemList.size() >0){
            setUpRecyclerView();
        }

        recyclerView.setVisibility(View.VISIBLE);
        loadingLayout.setVisibility(View.GONE);
        errorLayout.setVisibility(View.GONE);

        addCommentEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (addCommentEditText.getRight() - addCommentEditText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                       /* if(addCommentEditText.getText().toString().trim().length() == 0){
                            showDialogFillOutField();
                        }else{
                            CommentEvent newComment = new CommentEvent(new Date().toString(), addCommentEditText.getText().toString());

                            if (commentItemList != null){
                                commentItemList.add(newComment);
                                commentListAdapter.notifyItemInserted(commentItemList.size() - 1);
                            }else{
                                commentItemList = new ArrayList<CommentEvent>();
                                commentItemList.add(newComment);
                                setUpRecyclerView();
                            }

                            Toast.makeText(getBaseContext(), "gracias por añadir un comentario", Toast.LENGTH_LONG).show();
                            addCommentEditText.setText("");
                        }*/

                        Utils.closeSoftKeyboard(CommentActivity.this);

                        return true;
                    }
                }
                return false;
            }
        });

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

    private void showDialogFillOutField(){
        new AlertDialog.Builder(this)
                .setMessage(getString(R.string.alert_fill_out_comment))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void setUpRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        commentListAdapter = new CommentListAdapter(this, commentItemList);
        recyclerView.setAdapter(commentListAdapter);
    }

}