package com.unir.grupo2.myzeancoach.ui;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.domain.model.Dilemma;
import com.unir.grupo2.myzeancoach.domain.model.ExerciseWelfare;
import com.unir.grupo2.myzeancoach.domain.model.PlanWelfare;
import com.unir.grupo2.myzeancoach.domain.model.Test;
import com.unir.grupo2.myzeancoach.ui.LoginAndUserData.LoginActivity;
import com.unir.grupo2.myzeancoach.ui.MCooperativeSol.AddDilemmaActivity;
import com.unir.grupo2.myzeancoach.ui.MCooperativeSol.AmendDilemmaActivity;
import com.unir.grupo2.myzeancoach.ui.MCooperativeSol.CoachFragment;
import com.unir.grupo2.myzeancoach.ui.MCooperativeSol.DilemmaCommentActivity;
import com.unir.grupo2.myzeancoach.ui.MCooperativeSol.HomepageFragment;
import com.unir.grupo2.myzeancoach.ui.MCooperativeSol.MCooperativeSolFragment;
import com.unir.grupo2.myzeancoach.ui.MCustomizeFragment.AddRemainderFragment;
import com.unir.grupo2.myzeancoach.ui.MCustomizeFragment.AddStressQuestionFragment;
import com.unir.grupo2.myzeancoach.ui.MCustomizeFragment.MCustomizeFragment;
import com.unir.grupo2.myzeancoach.ui.MCustomizeFragment.RemaindersFragment;
import com.unir.grupo2.myzeancoach.ui.MCustomizeFragment.StressFragment;
import com.unir.grupo2.myzeancoach.ui.MCustomizeFragment.remaindersList.RemainderItemObject;
import com.unir.grupo2.myzeancoach.ui.MCustomizeFragment.stressQuestionsList.StressQuestionObject;
import com.unir.grupo2.myzeancoach.ui.MEssentialInfo.MEssentialInfoFragment;
import com.unir.grupo2.myzeancoach.ui.MEssentialInfo.TestActivity;
import com.unir.grupo2.myzeancoach.ui.MEssentialInfo.TestsFragment;
import com.unir.grupo2.myzeancoach.ui.MEssentialInfo.VideoYoutubeActivity;
import com.unir.grupo2.myzeancoach.ui.MEssentialInfo.VideosFragment;
import com.unir.grupo2.myzeancoach.ui.MLeisure.AddPostActivity;
import com.unir.grupo2.myzeancoach.ui.MLeisure.CommentActivity;
import com.unir.grupo2.myzeancoach.ui.MLeisure.MLeisureFragment;
import com.unir.grupo2.myzeancoach.ui.MLeisure.PublicHomepageFragment;
import com.unir.grupo2.myzeancoach.ui.MLeisure.postList.EventItem;
import com.unir.grupo2.myzeancoach.ui.MWelfare.CurrentPlanFragment;
import com.unir.grupo2.myzeancoach.ui.MWelfare.MainExerciseActivity;
import com.unir.grupo2.myzeancoach.ui.MWelfare.WelfareAllPlansFragment;
import com.unir.grupo2.myzeancoach.ui.profil.ProfilFragment;

import java.lang.reflect.Type;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements TestsFragment.OnItemSelectedListener,
        VideosFragment.OnItemVideoSelectedListener, PublicHomepageFragment.OnPostListener, HomepageFragment.OnDilemmaPostListener, RemaindersFragment.OnPostListener,
        WelfareAllPlansFragment.OnItemPlanSelectedListener, CurrentPlanFragment.OnItemExerciseSelectedListener,StressFragment.OnPostListener, CoachFragment.OnDilemmaCoachListener {

    static final int VIDEO_YOUTUBE_REQUEST = 1;
    static final int VIDEO_TEST_REQUEST = 2;
    static final int DILEMMA_COMMENT_REQUEST = 3;
    static final int PLAN_EXERCISE_REQUEST = 4;
    static final int ADD_EVENT_REQUEST = 5;

    // ui
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.navigation_view)
    NavigationView navigationView;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        /**
         * Lets inflate the very first fragment
         */
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_view, new MCustomizeFragment()).commit();

        //Setup click events on the Navigation View Items.
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                drawerLayout.closeDrawers();

                if (menuItem.getItemId() == R.id.nav_customise) {
                    FragmentTransaction xfragmentTransaction = fragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.container_view, new MCustomizeFragment()).commit();

                }

                if (menuItem.getItemId() == R.id.nav_essential_information) {
                    FragmentTransaction xfragmentTransaction = fragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.container_view, new MEssentialInfoFragment()).commit();
                }

                if (menuItem.getItemId() == R.id.nav_cooperative_solutions) {
                    FragmentTransaction xfragmentTransaction = fragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.container_view, new MCooperativeSolFragment()).commit();
                }

                if (menuItem.getItemId() == R.id.nav_welfare) {
                    //check on SharedPreference
                    SharedPreferences sharedPref = getSharedPreferences(
                            getString(R.string.preference_file_key), Context.MODE_PRIVATE);

                    String savedPlan = sharedPref.getString(getString(R.string.PREFERENCES_CURRENT_PLAN_WELFARE), null);

                    if (savedPlan == null || savedPlan.equals("null")) {
                        FragmentTransaction xfragmentTransaction = fragmentManager.beginTransaction();
                        xfragmentTransaction.replace(R.id.container_view, new WelfareAllPlansFragment()).commit();
                    } else {
                        Type type = new TypeToken<PlanWelfare>() {
                        }.getType();
                        Gson gson = new Gson();
                        PlanWelfare plan = gson.fromJson(savedPlan, type);
                        launchCurrentPlanFragment(plan);
                    }
                }

                if (menuItem.getItemId() == R.id.nav_leisure) {
                    FragmentTransaction xfragmentTransaction = fragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.container_view, new MLeisureFragment()).commit();
                }
                if (menuItem.getItemId() == R.id.nav_profil) {
                    FragmentTransaction xfragmentTransaction = fragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.container_view, new ProfilFragment()).commit();
                }
                if (menuItem.getItemId() == R.id.nav_logout) {
                    SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(
                            getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.clear().commit();
                    //se muestra la pantalla de login
                    lauchLoginActivity();
                    finish();
                }

                closeSoftKeyboard();

                return false;
            }

        });


        //Setup Drawer Toggle of the Toolbar
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.opened,
                R.string.closed);

        drawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerToggle.syncState();

    }

    private void lauchLoginActivity(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void closeSoftKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /************Module Essential Info*******/

    //Test item has been clicked
    @Override
    public void onItemSelected(Test test) {
        Intent intent = new Intent(this, TestActivity.class);
        intent.putExtra("TEST", test);
        startActivityForResult(intent, VIDEO_TEST_REQUEST);
    }

    //Video item has been clicked
    @Override
    public void onItemVideoSelected(String urlName, String videoName, boolean isWatched) {
        Intent intent = new Intent(this, VideoYoutubeActivity.class);
        intent.putExtra("URL", urlName);
        intent.putExtra("VIDEO_NAME", videoName);
        intent.putExtra("IS_WATCHED", isWatched);
        startActivityForResult(intent, VIDEO_YOUTUBE_REQUEST);
    }

    /**************Module Leisure***************/
    /*****Homepage*****/
    //Add post button has been clicked
    @Override
    public void onAddEventSelected() {
        Intent intent = new Intent(this, AddPostActivity.class);
        startActivityForResult(intent, ADD_EVENT_REQUEST);
    }

    @Override
    public void onNumberLikePostSelected(EventItem post) {

    }

    @Override
    public void onCommentPostSelected(EventItem post) {
        Intent intent = new Intent(this, CommentActivity.class);
        intent.putExtra("POST", post);
        startActivity(intent);
    }

    @Override
    public void onNumberCommentPostSelected(EventItem post) {
        Intent intent = new Intent(this, CommentActivity.class);
        intent.putExtra("POST", post);
        startActivity(intent);
    }

    /**************Module Cooperative Solutions***************/

    /***Homepage***/
    @Override
    public void onDilemmaItemPostSelected(Dilemma dilemmaPost, boolean fromMyDilemma) {
        Intent intent = new Intent(this, DilemmaCommentActivity.class);
        intent.putExtra("DILEMMA", dilemmaPost);
        intent.putExtra("IS_FROM_MYDILEMMA", fromMyDilemma);
        startActivityForResult(intent, DILEMMA_COMMENT_REQUEST);
    }

    @Override
    public void onDilemmaCoachSelected(Dilemma coachDilemma) {
        Intent intent = new Intent(this, AmendDilemmaActivity.class);
        intent.putExtra("COACH_DILEMMA", coachDilemma);
        startActivity(intent);
    }

    @Override
    public void onAddDilemmaCoachButton() {
        Intent intent = new Intent(this, AddDilemmaActivity.class);
        startActivity(intent);
    }

    /*************Module Welfare**************************/
    private void launchMainExerciseActivity(ExerciseWelfare exerciseWelfare) {
        Intent intent = new Intent(this, MainExerciseActivity.class);
        intent.putExtra("EXERCISE", exerciseWelfare);
        startActivityForResult(intent, PLAN_EXERCISE_REQUEST);
    }

    private void launchCurrentPlanFragment(PlanWelfare plan) {
        CurrentPlanFragment fragment = new CurrentPlanFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("PLAN", plan);
        fragment.setArguments(bundle);

        FragmentTransaction xfragmentTransaction = fragmentManager.beginTransaction();
        xfragmentTransaction.replace(R.id.container_view, fragment).commit();
    }

    @Override
    public void onItemExerciseSelected(ExerciseWelfare exercise) {
        launchMainExerciseActivity(exercise);
    }

    @Override
    public void onItemPlanSelected(PlanWelfare plan) {
        launchCurrentPlanFragment(plan);
    }


    /****************All modules *******************/
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**************Module Essential information***************/
        if (requestCode == VIDEO_YOUTUBE_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    if (data.getBooleanExtra("IS_UPDATED", true)) {
                        FragmentTransaction xfragmentTransaction = fragmentManager.beginTransaction();
                        xfragmentTransaction.replace(R.id.container_view, new MEssentialInfoFragment()).commit();
                    }
                }
            }
        } else if (requestCode == VIDEO_TEST_REQUEST) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    if (data.getBooleanExtra("IS_UPDATED", true)) {
                        FragmentTransaction xfragmentTransaction = fragmentManager.beginTransaction();
                        xfragmentTransaction.replace(R.id.container_view, new MEssentialInfoFragment()).commit();
                    }
                }
            }
            /*************Module Welfare****************/
        } else if (requestCode == PLAN_EXERCISE_REQUEST) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    if (data.getBooleanExtra("IS_PLAN_COMPLETED", true)) {
                        FragmentTransaction xfragmentTransaction = fragmentManager.beginTransaction();
                        xfragmentTransaction.replace(R.id.container_view, new WelfareAllPlansFragment()).commit();
                    } else {
                        PlanWelfare plan = data.getParcelableExtra("PLAN");
                        launchCurrentPlanFragment(plan);
                    }
                }
            }
            /************Module Leisure******************/
        } else if (requestCode == ADD_EVENT_REQUEST) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    EventItem event = data.getParcelableExtra("EVENT_NEW");
                    if (event != null) {
                        FragmentTransaction xfragmentTransaction = fragmentManager.beginTransaction();
                        xfragmentTransaction.replace(R.id.container_view, new PublicHomepageFragment()).commit();
                    }
                }
            }
        }
    }

    /**************Module Customize Fragment***************/
    @Override
    public void onItemRemainderSelected(RemainderItemObject remainderItem) {
        FragmentTransaction xfragmentTransaction = fragmentManager.beginTransaction();
        xfragmentTransaction.replace(R.id.container_view, new MCustomizeFragment()).commit();
    }

    @Override
    public void onAddRemainderSelected() {
        FragmentTransaction xfragmentTransaction = fragmentManager.beginTransaction();
        xfragmentTransaction.replace(R.id.container_view, new AddRemainderFragment()).commit();
    }

    @Override
    public void onCompletedRemainderSelected(RemainderItemObject remainderItem) {
        FragmentTransaction xfragmentTransaction = fragmentManager.beginTransaction();
        xfragmentTransaction.replace(R.id.container_view, new MCustomizeFragment()).commit();
    }

    @Override
    public void onNewQuestionSelected() {
        FragmentTransaction xfragmentTransaction = fragmentManager.beginTransaction();
        xfragmentTransaction.replace(R.id.container_view, new AddStressQuestionFragment()).commit();

    }

    @Override
    public void onFinalButtonPressed() {
        //none
    }

    @Override
    public void onSendItemSelected(String answer, StressQuestionObject stressQuestionObject) {
        //FragmentTransaction xfragmentTransaction = fragmentManager.beginTransaction();
        //xfragmentTransaction.replace(R.id.container_view, new MCustomizeFragment()).commit();
    }

}
