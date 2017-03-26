package com.unir.grupo2.myzeancoach.ui.MLeisure;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.ui.MLeisure.commentList.CommentItem;
import com.unir.grupo2.myzeancoach.ui.MLeisure.commentList.CommentListAdapter;
import com.unir.grupo2.myzeancoach.ui.MLeisure.postList.EventItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cesar on 13/03/2017.
 */

public class CommentActivity extends AppCompatActivity {

    @BindView(R.id.comment_recycler_view) RecyclerView recyclerView;
    @BindView(R.id.loading_layout) LinearLayout loadingLayout;
    @BindView(R.id.error_layout) LinearLayout errorLayout;
    @BindView(R.id.add_comment_editText) EditText addCommentEditText;

    private List<CommentItem> commentItemList;
    private CommentListAdapter commentListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_comment_list_layout);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        EventItem post = (EventItem) intent.getParcelableExtra("POST");

        commentItemList = post.getComments();

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
                        CommentItem newComment = new CommentItem(new Date().toString(), addCommentEditText.getText().toString());

                        if (commentItemList != null){
                            commentItemList.add(newComment);
                            commentListAdapter.notifyItemInserted(commentItemList.size() - 1);
                        }else{
                            commentItemList = new ArrayList<CommentItem>();
                            commentItemList.add(newComment);
                            setUpRecyclerView();
                        }

                        Toast.makeText(getBaseContext(), "gracias por añadir un comentario", Toast.LENGTH_LONG).show();
                        addCommentEditText.setText("");

                        View view = getCurrentFocus();
                        if (view != null) {
                            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        }

                        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                        return true;
                    }
                }
                return false;
            }
        });

    }

    private void setUpRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        commentListAdapter = new CommentListAdapter(this, commentItemList);
        recyclerView.setAdapter(commentListAdapter);
    }

}