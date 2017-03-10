package com.unir.grupo2.myzeancoach.ui.MEssentialInfo;

import android.content.Context;
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

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.domain.MEssentialInfo.TestsUseCase;
import com.unir.grupo2.myzeancoach.domain.model.Test;
import com.unir.grupo2.myzeancoach.ui.MEssentialInfo.testList.TesttListAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;

/**
 * Created by Cesar on 22/02/2017.
 */

public class TestsFragment extends Fragment implements TesttListAdapter.OnItemClickListener{

    List<Test> testItemList;
    TesttListAdapter testsListAdapter;

    @BindView(R.id.test_recycler_view) RecyclerView recyclerView;
    @BindView(R.id.loading_layout) LinearLayout loadingLayout;
    @BindView(R.id.error_layout) LinearLayout errorLayout;

    OnItemSelectedListener onItemSelectedListener;

    public interface OnItemSelectedListener{
        void onItemSelected(Test test);
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if (context instanceof OnItemSelectedListener){
            onItemSelectedListener = (OnItemSelectedListener) context;
        }else{
            throw new ClassCastException(context.toString() + " must implements TestListAdapter.onItemSelectedListener");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tests_layout,null);
        ButterKnife.bind(this, view);
        updateData();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        testsListAdapter = new TesttListAdapter(getContext(), testItemList, this);
        recyclerView.setAdapter(testsListAdapter);

        return view;
    }

    @Override
    public void onItemClick(Test testItem) {
        onItemSelectedListener.onItemSelected(testItem);
    }

    private void updateData() {
        showLoading();
        //we must pass a real token**
        new TestsUseCase("Bearer XID9TUxqU76zWc2wWDMqVFy2dFDdrK").execute(new TestsFragment.TestsSubscriber());
    }

    /**
     * Method used to show error view
     */
    public void showError() {
        recyclerView.setVisibility(View.GONE);
        loadingLayout.setVisibility(View.GONE);
        errorLayout.setVisibility(View.VISIBLE);
    }

    /**
     * Method used to show the loading view
     */
    public void showLoading() {
        loadingLayout.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        errorLayout.setVisibility(View.GONE);
    }

    /**
     * Method used to show the listView
     */
    public void showContent() {
        recyclerView.setVisibility(View.VISIBLE);
        loadingLayout.setVisibility(View.GONE);
        errorLayout.setVisibility(View.GONE);
    }


    private void updateList(List<Test> testList){
        testItemList = testList;
        testsListAdapter = new TesttListAdapter(getContext(),testItemList, this);
        recyclerView.setAdapter(testsListAdapter);
    }

    private final class TestsSubscriber extends Subscriber<List<Test>> {
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
        public void onNext(List<Test> testList) {
            updateList(testList);
        }
    }
}
