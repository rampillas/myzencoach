package com.unir.grupo2.myzeancoach.ui.MWelfare;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.domain.model.ExerciseWelfare;

/**
 * Created by Cesar on 19/03/2017.
 */

public class ExercisePlanTabsFragment extends Fragment {

    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static int int_items = 2;

    ExerciseWelfare exercise;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /**
         *Inflate tab_layout and setup Views.
         */
        View x = inflater.inflate(R.layout.tab_layout, null);
        tabLayout = (TabLayout) x.findViewById(R.id.tabs);
        viewPager = (ViewPager) x.findViewById(R.id.viewpager);

        /**
         *Set an Apater for the View Pager
         */
        viewPager.setAdapter(new ExercisePlanTabsFragment.MyAdapter(getChildFragmentManager()));

        /**
         * Now , this is a workaround ,
         * The setupWithViewPager dose't works without the runnable .
         * Maybe a Support Library Bug .
         */
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });

        Bundle bundle = getArguments();
        if (bundle != null) {
            exercise = bundle.getParcelable("EXERCISE");
        }else{
            exercise = null;
        }

        return x;

    }

    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Return fragment with respect to Position .
         */
        @Override
        public Fragment getItem(int position) {

            Bundle bundle = new Bundle();
            bundle.putParcelable("EXERCISE", exercise);

            switch (position) {
                case 0:
                    ExercisePlanFragment cPfragment = new ExercisePlanFragment();
                    cPfragment.setArguments(bundle);
                    return cPfragment;
                case 1:
                    RatePlanFragment rFragment = new RatePlanFragment();
                    rFragment.setArguments(bundle);
                    return rFragment;
            }
            return null;
        }

        @Override
        public int getCount() {

            return int_items;

        }

        /**
         * This method returns the title of the tab according to the position.
         */

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position) {
                case 0:
                    return getString(R.string.weekly_exercise);
                case 1:
                    return getString(R.string.rate);
            }
            return null;
        }
    }
}

