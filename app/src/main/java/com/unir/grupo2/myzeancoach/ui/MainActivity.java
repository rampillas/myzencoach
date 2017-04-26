package com.unir.grupo2.myzeancoach.ui;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.domain.model.ExerciseWelfare;
import com.unir.grupo2.myzeancoach.domain.model.PlanWelfare;
import com.unir.grupo2.myzeancoach.domain.utils.Constants;
import com.unir.grupo2.myzeancoach.domain.utils.Utils;
import com.unir.grupo2.myzeancoach.ui.LoginAndUserData.LoginActivity;
import com.unir.grupo2.myzeancoach.ui.MCooperativeSol.HomepageFragment;
import com.unir.grupo2.myzeancoach.ui.MCooperativeSol.MCooperativeSolFragment;
import com.unir.grupo2.myzeancoach.ui.MCustomize.AddObservationsActivity;
import com.unir.grupo2.myzeancoach.ui.MCustomize.AddRemainderActivity;
import com.unir.grupo2.myzeancoach.ui.MCustomize.AddStressQuestionActivity;
import com.unir.grupo2.myzeancoach.ui.MCustomize.MCustomizeFragment;
import com.unir.grupo2.myzeancoach.ui.MCustomize.RemaindersFragment;
import com.unir.grupo2.myzeancoach.ui.MCustomize.StressFragment;
import com.unir.grupo2.myzeancoach.ui.MCustomize.remaindersList.RemainderItemObject;
import com.unir.grupo2.myzeancoach.ui.MCustomize.stressQuestionsList.StressQuestionObject;
import com.unir.grupo2.myzeancoach.ui.MEssentialInfo.MEssentialInfoFragment;
import com.unir.grupo2.myzeancoach.ui.MEssentialInfo.VideosFragment;
import com.unir.grupo2.myzeancoach.ui.MLeisure.InterestsFragment;
import com.unir.grupo2.myzeancoach.ui.MLeisure.MLeisureFragment;
import com.unir.grupo2.myzeancoach.ui.MWelfare.CurrentPlanFragment;
import com.unir.grupo2.myzeancoach.ui.MWelfare.MainExerciseActivity;
import com.unir.grupo2.myzeancoach.ui.MWelfare.WelfareAllPlansFragment;
import com.unir.grupo2.myzeancoach.ui.profil.ProfilFragment;

import java.lang.reflect.Type;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.pushy.sdk.Pushy;
import rx.Subscriber;

public class MainActivity extends AppCompatActivity implements VideosFragment.UpdateDataEsentialInfoListener,
        InterestsFragment.UpdateEventsListener, HomepageFragment.UpdateDilemmaListener, RemaindersFragment.OnPostListener,
        WelfareAllPlansFragment.OnItemPlanSelectedListener,
        CurrentPlanFragment.OnItemExerciseSelectedListener, StressFragment.OnPostListener{

    static final int PLAN_EXERCISE_REQUEST = 4;

    // ui
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.navigation_view)
    NavigationView navigationView;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    private boolean fromLogin = false;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setLanguage();
        Pushy.listen(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // Request both READ_EXTERNAL_STORAGE and WRITE_EXTERNAL_STORAGE so that the
            // Pushy SDK will be able to persist the device token in the external storage
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }

        /**
         * Lets inflate the very first fragment
         */
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        Intent intent = getIntent();
        if (intent != null){
            //Check if the previous activity was LoginActivity to count one connection or not (traking)
            fromLogin = intent.getBooleanExtra("FROM_LOGIN", false);

            if (intent.getBooleanExtra("GO_TO_PROFILE",false)){
                //Once the app language has been changed, mainActivity is Refreshed and ProfilFragment is launched
                fragmentTransaction.replace(R.id.container_view, new ProfilFragment()).commit();
            }else{
                fragmentTransaction.replace(R.id.container_view, new MCustomizeFragment()).commit();
            }
        }

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

                Utils.closeSoftKeyboard(MainActivity.this);

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

    @Override
    protected void onStart(){
        super.onStart();
        if (isNewConnection()){
            Utils.launchConnectionUseCase(this);
        }
    }

    private boolean isNewConnection(){
        if (Utils.isNewConnection() && !fromLogin){
            return false;
        }else {
            if (fromLogin){
                fromLogin = false;
            }
            return true;
        }
    }


    private void setLanguage(){

        String languageSharedPregerence = Utils.getLanguageFromPreference(this);
        String languageRight = "";

        if(languageSharedPregerence != null && !equals("")){
            languageRight = languageSharedPregerence;
        }else{
            String localeLanguage = Locale.getDefault().getDisplayLanguage();
            switch (localeLanguage){
                case "English":
                    languageRight = Constants.ENGLISH;
                    break;
                case "español":
                    languageRight = Constants.SPANISH;
                    break;
                case "italiano":
                    languageRight = Constants.ITALIAN;
                    break;
                default:
                    languageRight = Constants.ITALIAN;
            }
            Utils.saveLanguagePreference(languageRight,this);
        }

        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        conf.locale = new Locale(languageRight);
        res.updateConfiguration(conf, dm);
    }

    private void lauchLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    /**Module Essential Info*/
    @Override
    public void updateDataEsentialInfo(int positionViewPager) {
        FragmentTransaction xfragmentTransaction = fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putInt("VIEW_PAGER_POSITION", positionViewPager);
        MEssentialInfoFragment fragment = new MEssentialInfoFragment();
        fragment.setArguments(bundle);
        xfragmentTransaction.replace(R.id.container_view, fragment).commit();
    }


    /**Module Leisure***/
    @Override
    public void updateEvents(int positionViewPager) {
        FragmentTransaction xfragmentTransaction = fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putInt("VIEW_PAGER_POSITION", positionViewPager);
        MLeisureFragment fragment = new MLeisureFragment();
        fragment.setArguments(bundle);
        xfragmentTransaction.replace(R.id.container_view, fragment).commit();
    }

    /***Module Welfare****/
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

    /****Module cooperative solutions *****/
    @Override
    public void updateDilemma(int position) {
        FragmentTransaction xfragmentTransaction = fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putInt("VIEW_PAGER_POSITION", position);
        MCooperativeSolFragment fragment = new MCooperativeSolFragment();
        fragment.setArguments(bundle);
        xfragmentTransaction.replace(R.id.container_view, fragment).commit();
    }

    /****All modules ***/
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /***Module Welfare**/
        if (requestCode == PLAN_EXERCISE_REQUEST) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    if (data.getBooleanExtra("IS_PLAN_COMPLETED", true)) {
                        WelfareAllPlansFragment welfareAllPlansFragment = new WelfareAllPlansFragment();
                        Bundle args = new Bundle();
                        args.putBoolean("IS_PLAN_FINISHED", true);
                        welfareAllPlansFragment.setArguments(args);
                        FragmentTransaction xfragmentTransaction = fragmentManager.beginTransaction();
                        xfragmentTransaction.replace(R.id.container_view, welfareAllPlansFragment).commit();
                    } else {
                        PlanWelfare plan = data.getParcelableExtra("PLAN");
                        launchCurrentPlanFragment(plan);
                    }
                }
            }
        }
    }

    /**Module Customize Fragment***/
    @Override
    public void onItemRemainderSelected(RemainderItemObject remainderItem) {
        FragmentTransaction xfragmentTransaction = fragmentManager.beginTransaction();
        xfragmentTransaction.replace(R.id.container_view, new MCustomizeFragment()).commit();
    }

    @Override
    public void onAddRemainderSelected() {
        Intent newRemainder = new Intent(this, AddRemainderActivity.class);
        startActivity(newRemainder);
    }

    @Override
    public void onCompletedRemainderSelected(RemainderItemObject remainderItem) {
        FragmentTransaction xfragmentTransaction = fragmentManager.beginTransaction();
        xfragmentTransaction.replace(R.id.container_view, new MCustomizeFragment()).commit();
    }

    @Override
    public void onAddObservations(RemainderItemObject remainderItem) {
        Intent newRemainder = new Intent(this, AddObservationsActivity.class);
        newRemainder.putExtra("REMAINDERTITLE",remainderItem.getTitle());
        startActivity(newRemainder);
    }

    @Override
    public void onNewQuestionSelected() {
        Intent newQuestion =new Intent(this, AddStressQuestionActivity.class);
        startActivity(newQuestion);

    }

    @Override
    public void onFinalButtonPressed() {
        //none
    }

    @Override
    public void onSendItemSelected(String answer, StressQuestionObject stressQuestionObject) {
        //FragmentTransaction xfragmentTransaction = fragmentManager.beginTransaction();
        //xfragmentTransaction.replace(R.id.container_view, new MCustomize()).commit();
    }

    private final class StartAppSubscriber extends Subscriber<Void> {
        //3 callbacks

        //Show the listView
        @Override
        public void onCompleted() {

        }

        //Show the error
        @Override
        public void onError(Throwable e) {
        }

        //Update listview datas
        @Override
        public void onNext(Void aVoid) {
        }
    }

}