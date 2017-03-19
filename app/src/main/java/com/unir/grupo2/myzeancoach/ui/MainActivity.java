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
import android.widget.Toast;

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.domain.model.Test;
import com.unir.grupo2.myzeancoach.ui.LoginAndUserData.LoginFragment;
import com.unir.grupo2.myzeancoach.ui.MCooperativeSol.DilemmaCommentActivity;
import com.unir.grupo2.myzeancoach.ui.MCooperativeSol.HomepageFragment;
import com.unir.grupo2.myzeancoach.ui.MCooperativeSol.MCooperativeSolFragment;
import com.unir.grupo2.myzeancoach.ui.MCooperativeSol.dilemmaPostList.DilemmaPost;
import com.unir.grupo2.myzeancoach.ui.MCustomizeFragment.AddRemainderActivity;
import com.unir.grupo2.myzeancoach.ui.MCustomizeFragment.MCustomizeFragment;
import com.unir.grupo2.myzeancoach.ui.MCustomizeFragment.RemaindersFragment;
import com.unir.grupo2.myzeancoach.ui.MCustomizeFragment.remaindersList.RemainderItem;
import com.unir.grupo2.myzeancoach.ui.MEssentialInfo.MEssentialInfoFragment;
import com.unir.grupo2.myzeancoach.ui.MEssentialInfo.TestActivity;
import com.unir.grupo2.myzeancoach.ui.MEssentialInfo.TestsFragment;
import com.unir.grupo2.myzeancoach.ui.MEssentialInfo.VideoYoutubeActivity;
import com.unir.grupo2.myzeancoach.ui.MEssentialInfo.VideosFragment;
import com.unir.grupo2.myzeancoach.ui.MLeisure.AddPostActivity;
import com.unir.grupo2.myzeancoach.ui.MLeisure.CommentActivity;
import com.unir.grupo2.myzeancoach.ui.MLeisure.MLeisureFragment;
import com.unir.grupo2.myzeancoach.ui.MLeisure.PublicHomepageFragment;
import com.unir.grupo2.myzeancoach.ui.MLeisure.postList.PostItem;
import com.unir.grupo2.myzeancoach.ui.MWelfare.MWelfareFragment;
import com.unir.grupo2.myzeancoach.ui.profil.ProfilFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements TestsFragment.OnItemSelectedListener,
        VideosFragment.OnItemVideoSelectedListener, PublicHomepageFragment.OnPostListener, HomepageFragment.OnDilemmaPostListener,RemaindersFragment.OnPostListener {

    static final int VIDEO_YOUTUBE_REQUEST = 1;
    static final int VIDEO_TEST_REQUEST = 2;
    static final int DILEMMA_COMMENT_REQUEST = 3;

    // ui
    @BindView(R.id.drawer_layout) DrawerLayout drawerLayout;
    @BindView(R.id.navigation_view) NavigationView navigationView;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        /**
         * Lets inflate the very first fragment
         * Here , we are inflating the Login as the first Fragment
         */
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_view, new LoginFragment()).commit();


        //Setup click events on the Navigation View Items.
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                drawerLayout.closeDrawers();

                if (menuItem.getItemId() == R.id.nav_customise) {
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.container_view, new MCustomizeFragment()).commit();

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
                    FragmentTransaction xfragmentTransaction = fragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.container_view, new MWelfareFragment()).commit();
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
                    FragmentTransaction xfragmentTransaction = fragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.container_view, new LoginFragment()).commit();
                    Toast.makeText(getBaseContext(), "log out", Toast.LENGTH_LONG).show();
                }
                if (menuItem.getItemId() == R.id.nav_login) {
                    FragmentTransaction xfragmentTransaction = fragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.container_view, new LoginFragment()).commit();
                }

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
    //Post item has been clicked
    @Override
    public void onItemPostSelected(PostItem post) {
        Toast.makeText(this, "item has been clicked", Toast.LENGTH_LONG).show();
        /*Intent intent = new Intent(this, VideoYoutubeActivity.class);
        intent.putExtra("URL",urlName);
        startActivity(intent);*/
    }


    //Add post button has been clicked
    @Override
    public void onAddPostSelected() {
        Intent intent = new Intent(this, AddPostActivity.class);
        startActivity(intent);
    }


    @Override
    public void onLikePostSelected(PostItem post) {

    }

    @Override
    public void onNumberLikePostSelected(PostItem post) {

    }

    @Override
    public void onCommentPostSelected(PostItem post) {
        Intent intent = new Intent(this, CommentActivity.class);
        intent.putExtra("POST", post);
        startActivity(intent);
    }

    @Override
    public void onNumberCommentPostSelected(PostItem post) {
        Intent intent = new Intent(this, CommentActivity.class);
        intent.putExtra("POST", post);
        startActivity(intent);
    }

    /**************Module Cooperative Solutions***************/

    /***Homepage***/
    @Override
    public void onDilemmaItemPostSelected(DilemmaPost dilemmaPost, boolean fromMyDilemma) {
        Intent intent = new Intent(this, DilemmaCommentActivity.class);
        intent.putExtra("DILEMMA", dilemmaPost);
        intent.putExtra("IS_FROM_MYDILEMMA", fromMyDilemma);
        startActivityForResult(intent, DILEMMA_COMMENT_REQUEST);
    }

    /****************All modules *******************/
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**************Module Essential information***************/
        if (requestCode == VIDEO_YOUTUBE_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
               if (data != null){
                   if (data.getBooleanExtra("IS_UPDATED", true)){
                       FragmentTransaction xfragmentTransaction = fragmentManager.beginTransaction();
                       xfragmentTransaction.replace(R.id.container_view, new MEssentialInfoFragment()).commit();
                   }
                }
            }
        }else if (requestCode == VIDEO_TEST_REQUEST){
            if (resultCode == RESULT_OK) {
                if (data != null){
                    if (data.getBooleanExtra("IS_UPDATED", true)){
                        FragmentTransaction xfragmentTransaction = fragmentManager.beginTransaction();
                        xfragmentTransaction.replace(R.id.container_view, new MEssentialInfoFragment()).commit();
                    }
                }
            }
        }
    }

    /**************Module Customize Fragment***************/
    @Override
    public void onItemRemainderSelected(RemainderItem remainderItem) {

    }

    @Override
    public void onAddRemainderSelected() {
        Intent intent = new Intent(this, AddRemainderActivity.class);
        startActivity(intent);
    }

    @Override
    public void onCompletedRemainderSelected(RemainderItem remainderItem) {

    }
}
