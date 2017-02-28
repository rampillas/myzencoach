package com.unir.grupo2.myzeancoach.ui.MEssentialInfo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.ui.MEssentialInfo.testList.TestItem;
import com.unir.grupo2.myzeancoach.ui.MEssentialInfo.testList.TestListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cesar on 22/02/2017.
 */

public class TestsFragment extends Fragment implements TestListAdapter.OnItemClickListener{

    List<TestItem> testItemList;
    TestListAdapter testsListAdapter;
    @BindView(R.id.test_recycler_view) RecyclerView recyclerView;

    OnItemSelectedListener onItemSelectedListener;

    public interface OnItemSelectedListener{
        void onItemSelected(String videoName);
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tests_layout,null);
        ButterKnife.bind(this, view);
        testItemList = new ArrayList<>();

        TestItem testItem1 = new TestItem("El astronauta", false, 0);
        TestItem testItem2 = new TestItem("Corto: la mujer y el", true, 2);
        TestItem testItem3 = new TestItem("Pelicula: el profesor", true, 1);
        TestItem testItem4 = new TestItem("Corto: En busca de la felicidad", false, 0);
        TestItem testItem5 = new TestItem("Nosotros somos los mas fuertes", true, 4);
        TestItem testItem6 = new TestItem("Tranquilidad y sabiduria", true, 2);
        TestItem testItem7 = new TestItem("Corto: monjes, abuelas y dioses", true, 5);

        testItemList.add(testItem1);
        testItemList.add(testItem2);
        testItemList.add(testItem3);
        testItemList.add(testItem4);
        testItemList.add(testItem5);
        testItemList.add(testItem6);
        testItemList.add(testItem7);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        testsListAdapter = new TestListAdapter(getContext(), testItemList, this);
        recyclerView.setAdapter(testsListAdapter);

        return view;
    }

    @Override
    public void onItemClick(TestItem testItem) {
        onItemSelectedListener.onItemSelected(testItem.getVideoName());
    }
}
