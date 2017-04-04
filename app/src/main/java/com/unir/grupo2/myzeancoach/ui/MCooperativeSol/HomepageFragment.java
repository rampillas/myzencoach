package com.unir.grupo2.myzeancoach.ui.MCooperativeSol;

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
import com.unir.grupo2.myzeancoach.domain.model.Dilemma;
import com.unir.grupo2.myzeancoach.ui.MCooperativeSol.dilemmaPostList.DilemmaPostListAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Cesar on 22/02/2017.
 */

public class HomepageFragment extends Fragment implements DilemmaPostListAdapter.OnDilemmaPostClickListener{

    private final static int COMMENT_REQUEST = 1;

    List<Dilemma> dilemmaPostItemList;
    DilemmaPostListAdapter dilemmaPostListAdapter;

    @BindView(R.id.sol_coop_post_recycler_view) RecyclerView dilemmaPostListRecyclerView;
    @BindView(R.id.no_dilemma_layout) LinearLayout noDilemmaLayout;
    @BindView(R.id.message_textView) TextView messageNoDielmmaTextView;

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
    public void onItemDilemmaPostClick(Dilemma dilemma) {
        Intent intent = new Intent(getActivity(), DilemmaCommentActivity.class);
        intent.putExtra("DILEMMA", dilemma);
        intent.putExtra("IS_FROM_MYDILEMMA", false);
        startActivityForResult(intent, COMMENT_REQUEST);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /**************Module Essential information***************/
        if (requestCode == COMMENT_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    Dilemma dilemmaUpdated = data.getParcelableExtra("DILEMMA");
                    for (int i = 0; i < dilemmaPostItemList.size(); i++){
                        if (dilemmaPostItemList.get(i).getTitle().equals(dilemmaUpdated.getTitle())){
                            dilemmaPostItemList.set(i, dilemmaUpdated);
                            dilemmaPostListAdapter.notifyDataSetChanged();
                            break;
                        }
                    }
                }
            }
        }
    }
}

