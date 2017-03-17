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

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.domain.UseCase;
import com.unir.grupo2.myzeancoach.ui.MCooperativeSol.dilemmaPostList.DilemmaComment;
import com.unir.grupo2.myzeancoach.ui.MCooperativeSol.dilemmaPostList.DilemmaPost;
import com.unir.grupo2.myzeancoach.ui.MCooperativeSol.dilemmaPostList.DilemmaPostListAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cesar on 22/02/2017.
 */

public class HomepageFragment extends Fragment implements DilemmaPostListAdapter.OnDilemmaPostClickListener{

    List<DilemmaPost> dilemmaPostItemList;
    DilemmaPostListAdapter dilemmaPostListAdapter;
    private UseCase useCase;

    @BindView(R.id.sol_coop_post_recycler_view) RecyclerView dilemmaPostListRecyclerView;
    @BindView(R.id.loading_layout) LinearLayout loadingLayout;
    @BindView(R.id.error_layout) LinearLayout errorLayout;

    HomepageFragment.OnDilemmaPostListener dilemmaPostListener;

    public interface OnDilemmaPostListener{
        void onDilemmaItemPostSelected(DilemmaPost dilemmaPost);
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

        //updateData();

        dilemmaPostListRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        dilemmaPostListRecyclerView.setLayoutManager(linearLayoutManager);


        ArrayList<String> pros = new ArrayList<String>() {{
            add("Es una rapida solucion");
            add("Lo recomienda los medicos");
        }};
        ArrayList<String> cons = new ArrayList<String>() {{
            add("A tu familia no le aprecera bien");
            add("Los viernes no podras ir al cine");
        }};

        //Ayudame a solucionarlo
        ArrayList<DilemmaComment> comments1 = new ArrayList<DilemmaComment>();

        DilemmaComment post1 = new DilemmaComment("Mary", "31-05-2017", "esto seria lo mejor", pros,cons, false, null, null);
        DilemmaComment post2 = new DilemmaComment("Merlu", "31-05-2017", "esto seria lo mejor", pros,cons, false, null, null);
        DilemmaComment post3 = new DilemmaComment("Day", "31-05-2017", "esto seria lo mejor", pros,cons, false, null, null);
        DilemmaComment post4 = new DilemmaComment("Mary", "31-05-2017", "esto seria lo mejor", pros,cons, false, null, null);
        DilemmaComment post5 = new DilemmaComment("Armin", "31-05-2017", "esto seria lo mejor", pros,cons, false, null, null);
        DilemmaComment post6 = new DilemmaComment("Karl", "31-05-2017", "esto seria lo mejor", pros,cons, false, null, null);

        comments1.add(post1);
        comments1.add(post2);
        comments1.add(post3);
        comments1.add(post4);
        comments1.add(post5);
        comments1.add(post6);

        //Feedback
        ArrayList<DilemmaComment> comments2 = new ArrayList<DilemmaComment>();
        long DAY_IN_MS = 1000 * 60 * 60 * 24;
        Date now = new Date();
        Date eightDaysAgo = new Date(now.getTime() - (8 * DAY_IN_MS));
        DilemmaComment post7 = new DilemmaComment("Mary", "31-05-2017", "esto seria lo mejor", pros,cons, true, null,eightDaysAgo);
        DilemmaComment post8 = new DilemmaComment("Merlu", "31-05-2017", "esto seria lo mejor", pros,cons, false, null, null);
        comments2.add(post7);
        comments2.add(post8);

        //Completado
        ArrayList<DilemmaComment> comments3 = new ArrayList<DilemmaComment>();
        DilemmaComment post9 = new DilemmaComment("Mary", "31-05-2017", "esto seria lo mejor", pros,cons, true, "Ha sido muy util, sobre todo al segunda semana",null);
        DilemmaComment post10 = new DilemmaComment("Merlu", "31-05-2017", "esto seria lo mejor", pros,cons, false, null,null);
        comments3.add(post9);
        comments3.add(post10);

        //De otro usuario
        ArrayList<DilemmaComment> comments4 = new ArrayList<DilemmaComment>();
        DilemmaComment post11 = new DilemmaComment("Mary", "31-05-2017", "esto seria lo mejor", pros,cons, false, null,null);
        DilemmaComment post12 = new DilemmaComment("Merlu", "31-05-2017", "esto seria lo mejor", pros,cons, false, null,null);
        comments4.add(post9);
        comments4.add(post10);


        DilemmaPost dilemmaPost1 = new DilemmaPost("12-12-2016", "ceo", "Title 1","asdkasdnkahdjahsdkjahdkjhakjsdhhasdhkjashdjkahsdkjas", "help_me me",comments1);
        DilemmaPost dilemmaPost2 = new DilemmaPost("05-10-2012", "ceo","Title 2","asdkasdnkahdjahsdkjahdkjhakjsdhhasdhkjashdjkahsdkjas", "feedback", comments2);
        DilemmaPost dilemmaPost3 = new DilemmaPost("12-12-2016", "Ceo","Title 3","asdkasdnkahdjahsdkjahdkjhakjsdhhasdhkjashdjkahsdkjas", "completed", comments3);
        DilemmaPost dilemmaPost4 = new DilemmaPost("12-12-2016", "Armin","Title 4","asdkasdnkahdjahsdkjahdkjhakjsdhhasdhkjashdjkahsdkjas", "help_me", comments4);
        DilemmaPost dilemmaPost5 = new DilemmaPost("12-12-2016", "Mateo","Titlo largo para ver como queda en la app movil y calorar","asdkasdnkahdjahsdkjahdkjhakjsdhhasdhkjashdjkahsdkjas", "feedback", null);
        DilemmaPost dilemmaPost6 = new DilemmaPost("12-12-2016", "Eki","mas titulos","asdkasdnkahdjahsdkjahdkjhakjsdhhasdhkjashdjkahsdkjas", "help_me", null);
        DilemmaPost dilemmaPost7 = new DilemmaPost("12-12-2016", "Ceo","el ultimo titulo","asdkasdnkahdjahsdkjahdkjhakjsdhhasdhkjashdjkahsdkjas", "help_me", null);

        dilemmaPostItemList = new ArrayList<DilemmaPost>();
        dilemmaPostItemList.add(dilemmaPost1);
        dilemmaPostItemList.add(dilemmaPost2);
        dilemmaPostItemList.add(dilemmaPost3);
        dilemmaPostItemList.add(dilemmaPost4);
        dilemmaPostItemList.add(dilemmaPost5);
        dilemmaPostItemList.add(dilemmaPost6);
        dilemmaPostItemList.add(dilemmaPost7);
        dilemmaPostItemList.add(dilemmaPost7);
        dilemmaPostItemList.add(dilemmaPost7);




        dilemmaPostListRecyclerView.setVisibility(View.VISIBLE);
        loadingLayout.setVisibility(View.GONE);
        errorLayout.setVisibility(View.GONE);
        dilemmaPostListAdapter = new DilemmaPostListAdapter(getContext(),dilemmaPostItemList, this, true);
        dilemmaPostListRecyclerView.setAdapter(dilemmaPostListAdapter);

        return view;
    }

    @Override
    public void onItemDilemmaPostClick(DilemmaPost dilemmaPost) {
        dilemmaPostListener.onDilemmaItemPostSelected(dilemmaPost);
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

