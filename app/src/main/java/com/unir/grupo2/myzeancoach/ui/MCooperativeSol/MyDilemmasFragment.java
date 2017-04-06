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

public class MyDilemmasFragment extends Fragment implements DilemmaPostListAdapter.OnDilemmaPostClickListener{

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
        dilemmaPostItemList = bundle.getParcelableArrayList("MY_DILEMMAS");

        if (dilemmaPostItemList != null && !dilemmaPostItemList.isEmpty()){
            dilemmaPostListRecyclerView.setHasFixedSize(true);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            dilemmaPostListRecyclerView.setLayoutManager(linearLayoutManager);
            dilemmaPostListAdapter = new DilemmaPostListAdapter(getContext(),dilemmaPostItemList, this, true);
            dilemmaPostListRecyclerView.setAdapter(dilemmaPostListAdapter);
            showContent();
        }else{
            showNoDilemma();
        }
        return view;
    }

    @Override
    public void onItemDilemmaPostClick(Dilemma dilemmaPost) {
        Intent intent = new Intent(getActivity(), DilemmaCommentActivity.class);
        intent.putExtra("DILEMMA", dilemmaPost);
        intent.putExtra("IS_FROM_MYDILEMMA", true);
        startActivityForResult(intent, COMMENT_REQUEST);
    }

    public void showNoDilemma() {
        noDilemmaLayout.setVisibility(View.VISIBLE);
        dilemmaPostListRecyclerView.setVisibility(View.GONE);
        messageNoDielmmaTextView.setText(getString(R.string.message_no_my_dilemmas));
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
/*
    private void updateData() {
        showLoading();
        //we must pass a real token**
        new VideosUseCase("Bearer XID9TUxqU76zWc2wWDMqVFy2dFDdrK").execute(new VideosSubscriber());
    }

    /**
     * Method used to show error view
     */
  /*  public void showError() {
        postListRecyclerView.setVisibility(View.GONE);
        loadingLayout.setVisibility(View.GONE);
        errorLayout.setVisibility(View.VISIBLE);
    }

    /**
     * Method used to show the loading view
     */
  /*  public void showLoading() {
        loadingLayout.setVisibility(View.VISIBLE);
        postListRecyclerView.setVisibility(View.GONE);
        errorLayout.setVisibility(View.GONE);
    }

    /**
     * Method used to show the listView
     */
 /*   public void showContent() {
        postListRecyclerView.setVisibility(View.VISIBLE);
        loadingLayout.setVisibility(View.GONE);
        errorLayout.setVisibility(View.GONE);
    }


    private void updateList(List<PostItem> postList){
        postItemList = postList;
        postListAdapter = new VideoListAdapter(getContext(),postItemList, this);
        postListRecyclerView.setAdapter(postListAdapter);
    }

    private final class PostsSubscriber extends Subscriber<List<PostItem>> {
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
        public void onNext(List<PostItem> postList) {
            updateList(postList);
        }
    }*/
}

