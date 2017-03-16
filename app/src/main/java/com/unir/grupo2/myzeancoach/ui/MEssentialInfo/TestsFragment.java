package com.unir.grupo2.myzeancoach.ui.MEssentialInfo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.domain.MEssentialInfo.TestsUseCase;
import com.unir.grupo2.myzeancoach.domain.model.Test;
import com.unir.grupo2.myzeancoach.domain.model.Video;
import com.unir.grupo2.myzeancoach.ui.MEssentialInfo.testList.TesttListAdapter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;


/**
 * Created by Cesar on 22/02/2017.
 */

public class TestsFragment extends Fragment implements TesttListAdapter.OnItemClickListener {

    List<Test> testItemList;
    TesttListAdapter testsListAdapter;

    @BindView(R.id.test_recycler_view) RecyclerView recyclerView;
    @BindView(R.id.loading_layout) LinearLayout loadingLayout;
    @BindView(R.id.error_layout) LinearLayout errorLayout;
    @BindView(R.id.no_tests_layout) LinearLayout noTestLayout;

    OnItemSelectedListener onItemSelectedListener;

    public interface OnItemSelectedListener {
        void onItemSelected(Test test);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnItemSelectedListener) {
            onItemSelectedListener = (OnItemSelectedListener) context;
        } else {
            throw new ClassCastException(context.toString() + " must implements TestListAdapter.onItemSelectedListener");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tests_layout, null);
        ButterKnife.bind(this, view);
        updateData();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        return view;
    }

    @Override
    public void onItemClick(Test testItem) {
        onItemSelectedListener.onItemSelected(testItem);
    }

    private void updateData() {
        showLoading();
        //we must pass a real token**
        new TestsUseCase().execute(new TestsFragment.TestsSubscriber());
    }

    /**
     * Method used to show error view
     */
    public void showError() {
        recyclerView.setVisibility(View.GONE);
        loadingLayout.setVisibility(View.GONE);
        noTestLayout.setVisibility(View.GONE);
        errorLayout.setVisibility(View.VISIBLE);
    }

    /**
     * Method used to show the loading view
     */
    public void showLoading() {
        loadingLayout.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        errorLayout.setVisibility(View.GONE);
        noTestLayout.setVisibility(View.GONE);
    }

    /**
     * Method used to show the listView
     */
    public void showContent() {
        recyclerView.setVisibility(View.VISIBLE);
        loadingLayout.setVisibility(View.GONE);
        errorLayout.setVisibility(View.GONE);
        noTestLayout.setVisibility(View.GONE);
    }

    /**
     * Method used to show the no test available view
     */
    public void showNoTestAvailable(){
        noTestLayout.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        loadingLayout.setVisibility(View.GONE);
        errorLayout.setVisibility(View.GONE);
    }


    private void updateList(List<Test> testList) {

        this.testItemList = testList;

        SharedPreferences sharedPref = getContext().getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String savedList = sharedPref.getString(getString(R.string.PREFERENCES_VIDEOS), "default");

        Type type = new TypeToken<List<Video>>() {}.getType();
        Gson gson = new Gson();
        List<Video> objects = gson.fromJson(savedList, type);

        //Taking only the surveys  whose videos have already been watched
        if (objects != null && !objects.isEmpty()){
            List<Test>  filtedTestList= new ArrayList<Test>();
            for (int i = 0; i < objects.size(); i++){
                if (objects.get(i).getIsWatched() && !objects.get(i).getSurvey().isEmpty()){
                    filtedTestList.add(objects.get(i).getSurvey().get(0));
                }
            }

            this.testItemList = filtedTestList;
        }

        if (this.testItemList.isEmpty()){
            showNoTestAvailable();
        }else{
            showContent();
            testsListAdapter = new TesttListAdapter(getContext(), this.testItemList, this);
            recyclerView.setAdapter(testsListAdapter);
        }
    }

    private final class TestsSubscriber extends Subscriber<List<Test>> {
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
        public void onNext(List<Test> testList) {
            updateList(testList);
        }
    }
}
