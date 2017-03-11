package com.unir.grupo2.myzeancoach.ui.MLeisure;

/**
 * Created by Cesar on 11/03/2017.
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.unir.grupo2.myzeancoach.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AddPostActivity extends AppCompatActivity {

    // ui
    @BindView(R.id.title_editText) EditText titleEditText;
    @BindView(R.id.description_editText) EditText descriptionEditText;
    @BindView(R.id.add_post_button) Button addPostButton;
    @BindView(R.id.category_radioGroup) RadioGroup categoryRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_add_layout);
        ButterKnife.bind(this);

        final RadioButton[] rb = new RadioButton[8];
        final String[] categories = new String[]{"viajes", "recnologia", "naturaleza", "deportes", "salud", "naval", "trabajo","tiempo"};
        for(int i=0; i<8; i++){
            rb[i]  = new RadioButton(this);
            rb[i].setText(categories[i]);
            rb[i].setId(i + 100);
            categoryRadioGroup.addView(rb[i]);
        }
    }

}
