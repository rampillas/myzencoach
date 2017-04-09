package com.unir.grupo2.myzeancoach.ui.MLeisure;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.domain.MLeisure.AddInterestsUseCase;
import com.unir.grupo2.myzeancoach.domain.MLeisure.DeleteAllInteresesUseCase;
import com.unir.grupo2.myzeancoach.domain.MLeisure.GetInterestsUseCase;
import com.unir.grupo2.myzeancoach.domain.model.Interest;
import com.unir.grupo2.myzeancoach.domain.utils.Utils;
import com.unir.grupo2.myzeancoach.ui.MEssentialInfo.VideosFragment;
import com.unir.grupo2.myzeancoach.ui.MLeisure.InterestList.InterestListAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscriber;

/**
 * Created by Cesar on 22/02/2017.
 */

public class InterestsFragment extends Fragment implements InterestListAdapter.OnItemInterestClickListener{

    @BindView(R.id.interest_recycler_view) RecyclerView interestReciclerView;

    @BindView(R.id.content_layout) LinearLayout contentLayout;
    @BindView(R.id.loading_layout) LinearLayout loadingLayout;
    @BindView(R.id.error_layout) LinearLayout errorLayout;

    ArrayList<Interest> interestsList;
    String[] categories;

    InterestsFragment.UpdateEventsListener listener;

    public interface UpdateEventsListener{
        public void updateEvents(int positionViewPagerLaunched);
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if (context instanceof VideosFragment.UpdateDataEsentialInfoListener){
            listener = (InterestsFragment.UpdateEventsListener) context;
        }else{
            throw new ClassCastException(context.toString() + " must implements InterestsFragment.UpdateEventsListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.interests_layout,null);
        ButterKnife.bind(this, view);


        categories = getResources().getStringArray(R.array.array_event_categories);

        getInterestsData();

        return view;
    }

    private void getInterestsData() {
        showLoading();

        String userName = Utils.getUserFromPreference(getActivity());
        String token = "Bearer " + Utils.getTokenFromPreference(getActivity());

        new GetInterestsUseCase(userName, token).execute(new InterestsSubscriber());
    }

    private void updateInterests(ArrayList<Interest> interests){
        InterestListAdapter interestListAdapter;
        this.interestsList = interests;

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        interestReciclerView.setLayoutManager(linearLayoutManager);
        interestListAdapter = new InterestListAdapter(getContext(), this.interestsList, categories, this);
        interestReciclerView.setAdapter(interestListAdapter);
    }

    @Override
    public void onItemInterestClick(int position, boolean isToAdd) {

        if (isToAdd){
            Interest interest = new Interest();
            interest.setName(Utils.getUserFromPreference(getContext()));
            interest.setName(categories[position]);
            interestsList.add(interest);
        }else{
            for (int i = 0; i < interestsList.size(); i++){
                if (interestsList.get(i).getName().equals(categories[position])){
                    interestsList.remove(i);
                    break;
                }
            }
        }
        sendInterest();
    }

    private void sendInterest(){
        showLoading();

        String userName = Utils.getUserFromPreference(getActivity());
        String token = "Bearer " + Utils.getTokenFromPreference(getActivity());

        String text = "";

        if (interestsList.isEmpty()){
            text = "{\n" +
                    "\t\"user_interests\": \"" + userName + "\"\n" +
                    "}\n";

            RequestBody body = RequestBody.create(MediaType.parse("text/plain"), text);
            new DeleteAllInteresesUseCase(userName, token, body).execute(new AddInterestSubscriber());

        }else{
            String categoriesText = "";
            for (int i = 0; i< interestsList.size(); i++){
                categoriesText = categoriesText + "{\"name\": \"" + interestsList.get(i).getName() + "\"}";
                if (i != interestsList.size() - 1) {
                    categoriesText = categoriesText + ",";
                }
            }
            text = "{\n" +
                    "\t\"interests\": [" + categoriesText + "]\n" +
                    "}\n";

            RequestBody body = RequestBody.create(MediaType.parse("text/plain"), text);
            new AddInterestsUseCase(userName, token, body).execute(new AddInterestSubscriber());
        }
    }

    //Save interests on Shared Preference to be used from addEventActivity
    private void saveInterstsSharedPreference(ArrayList<Interest> interestsList){
        Gson gson = new Gson();
        String jsonList = gson.toJson(interestsList);

        SharedPreferences sharedPref = getContext().getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.PREFERENCES_INTERESTS_LEISURE), jsonList);
        editor.commit();
    }

    /**
     * Method used to show error view
     */
    public void showError() {
        errorLayout.setVisibility(View.VISIBLE);
        contentLayout.setVisibility(View.GONE);
        loadingLayout.setVisibility(View.GONE);
    }

    /**
     * Method used to show the loading view
     */
    public void showLoading() {
        loadingLayout.setVisibility(View.VISIBLE);
        contentLayout.setVisibility(View.GONE);
        errorLayout.setVisibility(View.GONE);
    }

    /**
     * Method used to show the listView
     */
    public void showContent() {
        contentLayout.setVisibility(View.VISIBLE);
        loadingLayout.setVisibility(View.GONE);
        errorLayout.setVisibility(View.GONE);
    }

    private final class InterestsSubscriber extends Subscriber<ArrayList<Interest>> {
        //3 callbacks

        //Show the listView
        @Override
        public void onCompleted() {
            showContent();
        }

        //Show the error
        @Override
        public void onError(Throwable e) {
            showError();
        }

        //Update listview datas
        @Override
        public void onNext(ArrayList<Interest> interests) {
            saveInterstsSharedPreference(interests);
            updateInterests(interests);
        }
    }

    private final class AddInterestSubscriber extends Subscriber<Void> {
        //3 callbacks

        //Show the listView
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
        public void onNext(Void aVoid) {
            listener.updateEvents(1);
        }
    }

}
