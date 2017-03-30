package com.unir.grupo2.myzeancoach.ui.MCooperativeSol;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.unir.grupo2.myzeancoach.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cesar on 30/03/2017.
 */

public class AddDilemmaActivity extends AppCompatActivity {
    @BindView(R.id.title_editText) EditText titleEditText;
    @BindView(R.id.description_editText) EditText descriptionEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coop_sol_add_dilemma_activity);
        ButterKnife.bind(this);

    }

}
