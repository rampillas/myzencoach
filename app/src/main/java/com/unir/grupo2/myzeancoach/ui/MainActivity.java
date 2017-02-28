package com.unir.grupo2.myzeancoach.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.ui.LoginAndUserData.LoginFragment;
import com.unir.grupo2.myzeancoach.ui.MCooperativeSol.MCooperativeSolFragment;
import com.unir.grupo2.myzeancoach.ui.MCustomizeFragment.MCustomizeFragment;
import com.unir.grupo2.myzeancoach.ui.MEssentialInfo.MEssentialInfoFragment;
import com.unir.grupo2.myzeancoach.ui.MEssentialInfo.TestActivity;
import com.unir.grupo2.myzeancoach.ui.MEssentialInfo.TestsFragment;
import com.unir.grupo2.myzeancoach.ui.MLeisure.MLeisureFragment;
import com.unir.grupo2.myzeancoach.ui.MWelfare.MWelfareFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements TestsFragment.OnItemSelectedListener{

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
         * Here , we are inflating the MCustomizeFragment as the first Fragment
         */
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_view,new MCustomizeFragment()).commit();


        //Setup click events on the Navigation View Items.
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                drawerLayout.closeDrawers();

                if (menuItem.getItemId() == R.id.nav_customise) {
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.container_view,new MCustomizeFragment()).commit();

                }

                if (menuItem.getItemId() == R.id.nav_essential_information) {
                    FragmentTransaction xfragmentTransaction = fragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.container_view,new MEssentialInfoFragment()).commit();
                }

                if (menuItem.getItemId() == R.id.nav_cooperative_solutions) {
                    FragmentTransaction xfragmentTransaction = fragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.container_view,new MCooperativeSolFragment()).commit();
                }

                if (menuItem.getItemId() == R.id.nav_welfare) {
                    FragmentTransaction xfragmentTransaction = fragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.container_view,new MWelfareFragment()).commit();
                }

                if (menuItem.getItemId() == R.id.nav_leisure) {
                    FragmentTransaction xfragmentTransaction = fragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.container_view,new MLeisureFragment()).commit();
                }
                if (menuItem.getItemId() == R.id.nav_login) {
                    FragmentTransaction xfragmentTransaction = fragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.container_view,new LoginFragment()).commit();
                }

                return false;
            }

        });


        //Setup Drawer Toggle of the Toolbar
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout, toolbar,R.string.open,
                R.string.close);

        drawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerToggle.syncState();

    }

    //Test item has been clicked
    @Override
    public void onItemSelected(String videoName) {
        Intent intent = new Intent(this, TestActivity.class);
        intent.putExtra("video_name",videoName);
        startActivity(intent);
    }
}
