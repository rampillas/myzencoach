package com.unir.grupo2.myzeancoach.domain.utils;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.unir.grupo2.myzeancoach.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Cesar on 30/03/2017.
 */

public class DialogCustomFragment extends android.support.v4.app.DialogFragment {
    @BindView(R.id.ok_button)
    Button okButton;
    @BindView(R.id.title_textView)
    TextView titleTextView;
    @BindView(R.id.description_textView)
    TextView descriptionTextView;

    private String description;
    private String title;

    public interface OnStopLister {
        public void onStopDialog();
    }

    private DialogCustomFragment.OnStopLister listener;

    // Empty constructor required for DialogFragment
    public DialogCustomFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnStopLister) {
            listener = (OnStopLister) context;
        } else {
            throw new ClassCastException(context.toString() + " must implement DialogCustomFragment.OnStopLister");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_costum_fargment, container);
        ButterKnife.bind(this, view);

        Bundle bundle = getArguments();
        title = bundle.getString("TITLE");
        description = bundle.getString("DESCRIPTION");

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            if (title != null && !title.equals("")) {
                titleTextView.setText(title);
            }
            if (description != null && !description.equals("")) {
                descriptionTextView.setText(description);
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        listener.onStopDialog();
    }

    @OnClick(R.id.ok_button)
    public void submit() {
        this.dismiss();
    }
}

