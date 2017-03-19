package com.unir.grupo2.myzeancoach.ui.MCustomizeFragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.ui.MCustomizeFragment.remaindersList.RemainderItem;
import com.unir.grupo2.myzeancoach.ui.MCustomizeFragment.remaindersList.RemaindersListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Cesar on 22/02/2017.
 */

public class RemaindersFragment extends Fragment implements RemaindersListAdapter.OnItemClickListener {
    @BindView(R.id.taskList)
    RecyclerView taskList;
    @Nullable
    @BindView(R.id.floating_action_button)
    FloatingActionButton floatingActionButton;

    @OnClick(R.id.floatingActionButton)
    public void openNewRemainder() {
        postListener.onAddRemainderSelected();
    }

    RemaindersListAdapter remaindersListAdapter;

    private List<RemainderItem> remainders;

    RemaindersFragment.OnPostListener postListener;

    public interface OnPostListener {
        void onItemRemainderSelected(RemainderItem remainderItem);

        void onAddRemainderSelected();

        void onCompletedRemainderSelected(RemainderItem remainderItem);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof RemaindersFragment.OnPostListener) {
            postListener = (RemaindersFragment.OnPostListener) context;
        } else {
            throw new ClassCastException(context.toString() + " must implements RemainderFragment.OnPostListener");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.remainders_layout, null);
        ButterKnife.bind(this, view);
        taskList.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        taskList.setLayoutManager(mLayoutManager);

        RemainderItem p1 = new RemainderItem("titulo", "obs", true);
        RemainderItem p2 = new RemainderItem("titulo", "obs", true);
        RemainderItem p3 = new RemainderItem("titulo", "obs", true);
        RemainderItem p4 = new RemainderItem("titulo", "obs", true);
        RemainderItem p5 = new RemainderItem("titulo", "obs", true);
        remainders = new ArrayList<RemainderItem>();
        remainders.add(p1);
        remainders.add(p2);
        remainders.add(p3);
        remainders.add(p4);
        remainders.add(p5);


        // specify an adapter (see also next example)
        remaindersListAdapter = new RemaindersListAdapter(getContext(), remainders, this);
        taskList.setAdapter(remaindersListAdapter);
        return view;
    }

    @Override
    public void onItemClick(RemainderItem remaindersItem) {
        postListener.onItemRemainderSelected(remaindersItem);
    }

    @Override
    public void onCompleteClick(RemainderItem remainderItem) {
        postListener.onCompletedRemainderSelected(remainderItem);
    }

    @Override
    public void onAddClick(RemainderItem remainderItem) {
        postListener.onAddRemainderSelected();
    }
}
