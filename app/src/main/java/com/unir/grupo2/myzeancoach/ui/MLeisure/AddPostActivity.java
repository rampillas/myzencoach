package com.unir.grupo2.myzeancoach.ui.MLeisure;

/**
 * Created by Cesar on 11/03/2017.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.ui.MLeisure.commentList.CommentItem;
import com.unir.grupo2.myzeancoach.ui.MLeisure.postList.EventItem;
import com.unir.grupo2.myzeancoach.ui.MLeisure.postList.Like;

import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class AddPostActivity extends AppCompatActivity {

    // ui
    @BindView(R.id.title_editText) EditText titleEditText;
    @BindView(R.id.description_editText) EditText descriptionEditText;
    @BindView(R.id.add_event_button) Button addEventButton;
    @BindView(R.id.category_radioGroup) RadioGroup categoryRadioGroup;

    final String[] categories = new String[]{"viajes", "recnologia", "naturaleza", "deportes", "salud", "naval", "trabajo","tiempo"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_add_layout);
        ButterKnife.bind(this);

        final RadioButton[] rb = new RadioButton[8];
        for(int i=0; i<8; i++){
            rb[i]  = new RadioButton(this);
            rb[i].setText(categories[i]);
            rb[i].setId(i);
            categoryRadioGroup.addView(rb[i]);
        }
    }

    @OnClick(R.id.add_event_button)
    public void addEvent(View view) {
        int radioButtonID = categoryRadioGroup.getCheckedRadioButtonId();
        EventItem event = new EventItem(new Date().toString(), titleEditText.getText().toString(),
                categories[radioButtonID],descriptionEditText.getText().toString(), new ArrayList<Like>(),
                new ArrayList<CommentItem>());

        Intent resultData = new Intent();
        resultData.putExtra("EVENT_NEW",event);
        setResult(Activity.RESULT_OK, resultData);
        finish();

    }

}
