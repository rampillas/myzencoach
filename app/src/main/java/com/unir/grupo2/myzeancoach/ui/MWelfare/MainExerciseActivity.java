package com.unir.grupo2.myzeancoach.ui.MWelfare;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.domain.utils.Utils;
import com.unir.grupo2.myzeancoach.domain.model.ExerciseWelfare;

import butterknife.ButterKnife;

/**
 * Created by Cesar on 19/03/2017.
 */

public class MainExerciseActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_exercise);
        ButterKnife.bind(this);

        Intent intent = getIntent();

        ExerciseWelfare exercise = (ExerciseWelfare) intent.getParcelableExtra("EXERCISE");

        /**
         * Lets inflate the very first fragment
         * Here , we are inflating the Login as the first Fragment
         */
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        ExercisePlanTabsFragment fragment = new ExercisePlanTabsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("EXERCISE", exercise);
        fragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.container_view,fragment).commit();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Utils.closeSoftKeyboard(MainExerciseActivity.this);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
