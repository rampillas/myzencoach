package com.unir.grupo2.myzeancoach.ui.MCooperativeSol;

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
import com.unir.grupo2.myzeancoach.domain.MCooperativeSol.DilemmasUseCase;
import com.unir.grupo2.myzeancoach.domain.Utils;
import com.unir.grupo2.myzeancoach.domain.model.Dilemma;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscriber;

import static com.unir.grupo2.myzeancoach.domain.Utils.getUserFromPreference;

/**
 * Created by Cesar on 22/02/2017.
 */

public class MCooperativeSolFragment extends Fragment {

    @BindView(R.id.content_linearLayout) LinearLayout contentLinearLayout;
    @BindView(R.id.loading_layout) LinearLayout loadingLayout;
    @BindView(R.id.error_layout) LinearLayout errorLayout;

    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static int int_items = 3 ;

    private ArrayList<Dilemma> myAceptedDilemmas = new ArrayList<Dilemma>();
    private ArrayList<Dilemma> coachDilemmas = new ArrayList<Dilemma>();
    private ArrayList<Dilemma> muroDilemmas = new ArrayList<Dilemma>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /**
         *Inflate tab_layout and setup Views.
         */
        View x =  inflater.inflate(R.layout.tab_layout_loading,null);
        ButterKnife.bind(this, x);
        tabLayout = (TabLayout) x.findViewById(R.id.tabs);
        viewPager = (ViewPager) x.findViewById(R.id.viewpager);

        updateData();

        /**
         *Set an Apater for the View Pager
         */

        return x;

    }

    private void updateData() {
        showLoading();

        String userName = getUserFromPreference(getActivity());
        String token = "Bearer " + Utils.getTokenFromPreference(getActivity());

        String text = "{\n" +
                "\t\"username\": \"" + userName + "\"\n" +
                "}\n";

        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), text);

        new DilemmasUseCase(userName, token, body).execute(new DilemmasSubscriber());
    }

    private void startViewPager(){
        viewPager.setAdapter(new MCooperativeSolFragment.MyAdapter(getChildFragmentManager()));

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
                    MyDilemmasFragment myDilemmasFragment = new MyDilemmasFragment();
                    Bundle args = new Bundle();
                    args.putParcelableArrayList("MY_DILEMMAS", myAceptedDilemmas);
                    myDilemmasFragment.setArguments(args);
                    return myDilemmasFragment;
                case 1 :
                    HomepageFragment muroFragment = new HomepageFragment();
                    Bundle args2 = new Bundle();
                    args2.putParcelableArrayList("MURO_DILEMMAS", muroDilemmas);
                    muroFragment.setArguments(args2);
                    return muroFragment;
                case 2 :
                    CoachFragment coachFragment = new CoachFragment();
                    Bundle args3 = new Bundle();
                    args3.putParcelableArrayList("COACH_DILEMMAS", coachDilemmas);
                    coachFragment.setArguments(args3);
                    return coachFragment;
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
                    return getString(R.string.my_dilemmas);
                case 1 :
                    return getString(R.string.homepage);
                case 2 :
                    return getString(R.string.coach);
            }
            return null;
        }
    }

    private final class DilemmasSubscriber extends Subscriber<ArrayList<Dilemma>> {
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
        public void onNext(ArrayList<Dilemma> allDilemmas) {

            String userName = Utils.getUserFromPreference(getActivity());

            for (int i = 0; i< allDilemmas.size(); i++){
                if (allDilemmas.get(i).getNickUser().equals(userName)){
                    if (allDilemmas.get(i).getState().equals("waitingForAnswer") || allDilemmas.get(i).getState().equals("refused")){
                        coachDilemmas.add(allDilemmas.get(i));
                    }else{
                        myAceptedDilemmas.add(allDilemmas.get(i));
                        muroDilemmas.add(allDilemmas.get(i));
                    }
                }else{
                    muroDilemmas.add(allDilemmas.get(i));
                }
            }
            startViewPager();
        }
    }
}
