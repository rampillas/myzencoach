package com.unir.grupo2.myzeancoach.ui.MEssentialInfo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.ui.MEssentialInfo.questionList.QuestionItem;
import com.unir.grupo2.myzeancoach.ui.MEssentialInfo.questionList.QuestionListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestActivity extends AppCompatActivity {

    @BindView(R.id.test_recycler_view) RecyclerView recyclerView;
    private List<QuestionItem> questionItemList;
    private QuestionListAdapter questionListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity);
        ButterKnife.bind(this);

        String videoName = getIntent().getStringExtra("video_name");

        QuestionItem questionItem1 = new QuestionItem(1, "¿Como sería la vida del protagonista en tu ciudad?",
                "mejor", "peor", "igual");
        QuestionItem questionItem2 = new QuestionItem(12, "¿Como se llama el hijo pequeño?",
                "Carlos", "Sergio", "Rodrigo");
        QuestionItem questionItem3 = new QuestionItem(3, "¿Como se llama su ciudad?",
                "Murcia", "Paris", "Granada");
        QuestionItem questionItem4 = new QuestionItem(4, "¿Tiene algun temos el protagonista?",
                "Si", "No", "No, pero si su padre");
        QuestionItem questionItem5 = new QuestionItem(5, "¿En que año se narra la historia?",
                "1998", "1852", "2001");

        questionItemList = new ArrayList<>();

        questionItemList.add(questionItem1);
        questionItemList.add(questionItem2);
        questionItemList.add(questionItem3);
        questionItemList.add(questionItem4);
        questionItemList.add(questionItem5);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        questionListAdapter = new QuestionListAdapter(this, questionItemList,videoName);
        recyclerView.setAdapter(questionListAdapter);

    }
}
