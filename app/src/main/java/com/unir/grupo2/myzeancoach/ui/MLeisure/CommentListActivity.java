package com.unir.grupo2.myzeancoach.ui.MLeisure;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.unir.grupo2.myzeancoach.R;

import butterknife.ButterKnife;

/**
 * Created by Cesar on 12/03/2017.
 */

public class CommentListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_comment_list_layout);
        ButterKnife.bind(this);



    }
}