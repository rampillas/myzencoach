package com.unir.grupo2.myzeancoach.ui.MWelfare;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unir.grupo2.myzeancoach.R;

import butterknife.ButterKnife;

/**
 * Created by Cesar on 22/02/2017.
 */


public class PlanFragment extends Fragment {


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.plan_layout, container, false);
        ButterKnife.bind(this, view);


        return view;

    }
}

