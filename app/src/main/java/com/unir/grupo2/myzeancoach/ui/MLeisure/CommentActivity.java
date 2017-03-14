package com.unir.grupo2.myzeancoach.ui.MLeisure;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.ui.MLeisure.commentList.CommentItem;
import com.unir.grupo2.myzeancoach.ui.MLeisure.commentList.CommentListAdapter;
import com.unir.grupo2.myzeancoach.ui.MLeisure.postList.PostItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cesar on 13/03/2017.
 */

public class CommentActivity extends AppCompatActivity {

    @BindView(R.id.comment_recycler_view) RecyclerView recyclerView;
    private List<CommentItem> commentItemList;
    private CommentListAdapter commentListAdapter;

    @BindView(R.id.loading_layout) LinearLayout loadingLayout;
    @BindView(R.id.error_layout) LinearLayout errorLayout;
    @BindView(R.id.add_comment_editText) EditText addCommentEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_comment_list_layout);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        PostItem post = (PostItem) intent.getParcelableExtra("POST");

        commentItemList = post.getComments();

        if (commentItemList != null && commentItemList.size() >0){
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(linearLayoutManager);
            commentListAdapter = new CommentListAdapter(this, commentItemList);
            recyclerView.setAdapter(commentListAdapter);
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
                        Toast.makeText(getBaseContext(), "pulsado", Toast.LENGTH_LONG).show();

                        return true;
                    }
                }
                return false;
            }
        });

    }


}