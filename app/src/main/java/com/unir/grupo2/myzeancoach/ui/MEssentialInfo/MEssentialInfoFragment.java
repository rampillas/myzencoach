package com.unir.grupo2.myzeancoach.ui.MEssentialInfo;

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
import android.widget.LinearLayout;

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.domain.MEssentialInfo.RankingUseCase;
import com.unir.grupo2.myzeancoach.domain.MEssentialInfo.TestsUseCase;
import com.unir.grupo2.myzeancoach.domain.MEssentialInfo.VideosUseCase;
import com.unir.grupo2.myzeancoach.domain.model.Ranking;
import com.unir.grupo2.myzeancoach.domain.model.Test;
import com.unir.grupo2.myzeancoach.domain.model.Video;
import com.unir.grupo2.myzeancoach.domain.utils.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;

/**
 * Created by Cesar on 22/02/2017.
 */

public class MEssentialInfoFragment extends Fragment {

    @BindView(R.id.content_linearLayout) LinearLayout contentLinearLayout;
    @BindView(R.id.loading_layout) LinearLayout loadingLayout;
    @BindView(R.id.error_layout) LinearLayout errorLayout;

    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static int int_items = 3 ;

    ArrayList<Video> videoItemList;
    ArrayList<Test> testItemList;
    //List of score elements where we save our rankings
    ArrayList<Ranking> rankingItemList;

    private int positionViewPager = -1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /**
         *Inflate tab_layout and setup Views.
         */
        View x = inflater.inflate(R.layout.tab_layout_loading, null);
        ButterKnife.bind(this, x);
        tabLayout = (TabLayout) x.findViewById(R.id.tabs);
        viewPager = (ViewPager) x.findViewById(R.id.viewpager);

        Bundle bundle = getArguments();
        if (bundle != null) {
            positionViewPager = bundle.getInt("VIEW_PAGER_POSITION");
        }

        getVideosData();

        return x;

    }

    private void getVideosData() {
        showLoading();
        String token = "Bearer " + Utils.getTokenFromPreference(getActivity());
        new VideosUseCase(token).execute(new VideosSubscriber());
    }

    private void getTestsData() {
        String token = "Bearer " + Utils.getTokenFromPreference(getActivity());
        new TestsUseCase(token).execute(new MEssentialInfoFragment.TestsSubscriber());
    }

    private void getRankingData() {
        String token = "Bearer " + Utils.getTokenFromPreference(getActivity());
        new RankingUseCase(token).execute(new RankingSubscriber());
    }

    private void startViewPager() {
        /**
         *Set an Apater for the View Pager
         */
        viewPager.setAdapter(new MEssentialInfoFragment.MyAdapter(getChildFragmentManager()));

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

        if (positionViewPager != -1){
            viewPager.setCurrentItem(positionViewPager);
        }
    }

    private void updateList(ArrayList<Test> testList) {

        this.testItemList = testList;

        //Taking only the surveys  whose videos have already been watched
        if (videoItemList != null && !videoItemList.isEmpty()){
            ArrayList<Test>  filtedTestList= new ArrayList<Test>();
            for (int i = 0; i < videoItemList.size(); i++){
                if (videoItemList.get(i).getIsWatched() && !videoItemList.get(i).getSurvey().isEmpty()){
                    filtedTestList.add(videoItemList.get(i).getSurvey().get(0));
                }
            }

            this.testItemList = filtedTestList;
        }
    }

    /**
     * Method used to show error view
     */
    public void showError() {
        contentLinearLayout.setVisibility(View.GONE);
        loadingLayout.setVisibility(View.GONE);
        errorLayout.setVisibility(View.VISIBLE);
    }

    /**
     * Method used to show the loading view
     */
    public void showLoading() {
        loadingLayout.setVisibility(View.VISIBLE);
        contentLinearLayout.setVisibility(View.GONE);
        errorLayout.setVisibility(View.GONE);
    }

    /**
     * Method used to show the listView
     */
    public void showContent() {
        contentLinearLayout.setVisibility(View.VISIBLE);
        loadingLayout.setVisibility(View.GONE);
        errorLayout.setVisibility(View.GONE);
    }

    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Return fragment with respect to Position .
         */
        @Override
        public Fragment getItem(int position)
        {
            switch (position){
                case 0 :
                    VideosFragment videoFragment = new VideosFragment();
                    Bundle args1 = new Bundle();
                    args1.putParcelableArrayList("VIDEOS", videoItemList);
                    videoFragment.setArguments(args1);
                    return videoFragment;
                case 1 :
                    TestsFragment testsFragment = new TestsFragment();
                    Bundle args2 = new Bundle();
                    args2.putParcelableArrayList("TESTS", testItemList);
                    testsFragment.setArguments(args2);
                    return testsFragment;
                case 2 :
                    RankingFragment rankingFragment = new RankingFragment();
                    Bundle args3 = new Bundle();
                    args3.putParcelableArrayList("RANKING", rankingItemList);
                    rankingFragment.setArguments(args3);
                    return rankingFragment;
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

            switch (position){
                case 0 :
                    return getString(R.string.videos);
                case 1 :
                    return getString(R.string.tests);
                case 2 :
                    return getString(R.string.ranking);
            }
            return null;
        }
    }

    private final class VideosSubscriber extends Subscriber<ArrayList<Video>> {
        //3 callbacks

        @Override public void onCompleted() {

        }

        //Show the error
        @Override public void onError(Throwable e) {
            showError();
        }

        //Update listview datas
        @Override
        public void onNext(ArrayList<Video> videoList) {
            videoItemList = videoList;
            getTestsData();
        }
    }

    private final class TestsSubscriber extends Subscriber<ArrayList<Test>> {
        //3 callbacks

        @Override
        public void onCompleted() {

        }

        //Show the error
        @Override
        public void onError(Throwable e) {
            showError();
        }

        //Update listview datas
        @Override
        public void onNext(ArrayList<Test> testList) {
            updateList(testList);
            getRankingData();
        }
    }

    private final class RankingSubscriber extends Subscriber<ArrayList<Ranking>> {
        //3 callbacks

        //Show the listView
        @Override public void onCompleted() {
            showContent();
        }

        //Show the error
        @Override public void onError(Throwable e) {
            showError();
        }

        //Update listview datas
        @Override
        public void onNext(ArrayList<Ranking> rankingList) {
            rankingItemList = rankingList;
            startViewPager();
        }
    }
}
