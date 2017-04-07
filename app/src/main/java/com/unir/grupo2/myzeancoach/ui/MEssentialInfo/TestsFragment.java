package com.unir.grupo2.myzeancoach.ui.MEssentialInfo;

import android.content.Context;
import android.content.Intent;
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
import android.widget.TextView;

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.domain.model.Test;
import com.unir.grupo2.myzeancoach.ui.MEssentialInfo.testList.TesttListAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;


/**
 * Created by Cesar on 22/02/2017.
 */

public class TestsFragment extends Fragment implements TesttListAdapter.OnItemClickListener {

    static final int VIDEO_TEST_REQUEST = 2;

    @BindView(R.id.test_recycler_view) RecyclerView recyclerView;
    @BindView(R.id.no_tests_layout) LinearLayout noTestLayout;
    @BindView(R.id.message_textView) TextView messageNoDielmmaTextView;

    VideosFragment.UpdateDataEsentialInfoListener updateDataListener;

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if (context instanceof VideosFragment.UpdateDataEsentialInfoListener){
            updateDataListener = (VideosFragment.UpdateDataEsentialInfoListener) context;
        }else{
            throw new ClassCastException(context.toString() + " must implements VideosFragment.OnItemVideoSelectedListener");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tests_layout, null);
        ButterKnife.bind(this, view);

        Bundle bundle = getArguments();
        ArrayList<Test> testItemList = bundle.getParcelableArrayList("TESTS");

        if (testItemList != null && !testItemList.isEmpty()){

            recyclerView.setHasFixedSize(true);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(linearLayoutManager);
            TesttListAdapter testsListAdapter = new TesttListAdapter(getContext(), testItemList, this);
            recyclerView.setAdapter(testsListAdapter);

            showContent();
        }else{
            showNoDilemma();
        }

        return view;
    }

    @Override
    public void onItemClick(Test testItem) {
        Intent intent = new Intent(getActivity(), TestActivity.class);
        intent.putExtra("TEST", testItem);
        startActivityForResult(intent, VIDEO_TEST_REQUEST);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**************Module Essential information***************/
        if (requestCode == VIDEO_TEST_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    if (data.getBooleanExtra("IS_UPDATED", true)) {
                        updateDataListener.updateDataEsentialInfo(1);
                    }
                }
            }
        }
    }

    public void showNoDilemma() {
        noTestLayout.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        messageNoDielmmaTextView.setText(getString(R.string.no_available_test));
    }

    public void showContent() {
        recyclerView.setVisibility(View.VISIBLE);
        noTestLayout.setVisibility(View.GONE);
    }

}
