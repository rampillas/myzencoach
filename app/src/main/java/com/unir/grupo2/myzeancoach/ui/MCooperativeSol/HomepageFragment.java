package com.unir.grupo2.myzeancoach.ui.MCooperativeSol;

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
import android.widget.TextView;

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.domain.model.Dilemma;
import com.unir.grupo2.myzeancoach.ui.MCooperativeSol.dilemmaPostList.DilemmaPostListAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cesar on 22/02/2017.
 */

public class HomepageFragment extends Fragment implements DilemmaPostListAdapter.OnDilemmaPostClickListener{

    List<Dilemma> dilemmaPostItemList;
    DilemmaPostListAdapter dilemmaPostListAdapter;

    @BindView(R.id.sol_coop_post_recycler_view) RecyclerView dilemmaPostListRecyclerView;
    @BindView(R.id.no_dilemma_layout) LinearLayout noDilemmaLayout;
    @BindView(R.id.message_textView) TextView messageNoDielmmaTextView;

    HomepageFragment.OnDilemmaPostListener dilemmaPostListener;

    public interface OnDilemmaPostListener{
        void onDilemmaItemPostSelected(Dilemma dilemmaPost, boolean fromMyDilemma);
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if (context instanceof HomepageFragment.OnDilemmaPostListener){
            dilemmaPostListener = (HomepageFragment.OnDilemmaPostListener) context;
        }else{
            throw new ClassCastException(context.toString() + " must implements HomepageFragment.OnDilemmaPostListener");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.coop_sol_homepage_layout,null);
        ButterKnife.bind(this, view);

        Bundle bundle = getArguments();
        dilemmaPostItemList = bundle.getParcelableArrayList("MURO_DILEMMAS");

        if (dilemmaPostItemList != null && !dilemmaPostItemList.isEmpty()){

            dilemmaPostListRecyclerView.setHasFixedSize(true);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            dilemmaPostListRecyclerView.setLayoutManager(linearLayoutManager);
            dilemmaPostListAdapter = new DilemmaPostListAdapter(getContext(),dilemmaPostItemList, this, false);
            dilemmaPostListRecyclerView.setAdapter(dilemmaPostListAdapter);
            showContent();
        }else{
            showNoDilemma();
        }

        return view;
    }

    @Override
    public void onItemDilemmaPostClick(Dilemma dilemma, boolean fromMyDilemma) {
        dilemmaPostListener.onDilemmaItemPostSelected(dilemma, fromMyDilemma);
    }

    public void showNoDilemma() {
        noDilemmaLayout.setVisibility(View.VISIBLE);
        dilemmaPostListRecyclerView.setVisibility(View.GONE);
        messageNoDielmmaTextView.setText(getString(R.string.message_no_muro_dilemmas));
    }

    public void showContent() {
        dilemmaPostListRecyclerView.setVisibility(View.VISIBLE);
        noDilemmaLayout.setVisibility(View.GONE);
    }

}

