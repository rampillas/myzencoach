package com.unir.grupo2.myzeancoach.ui.LoginAndUserData;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unir.grupo2.myzeancoach.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditUserFragment extends Fragment {


    public EditUserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.edit_user, container, false);
    }

}
