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
import com.unir.grupo2.myzeancoach.ui.MCooperativeSol.dilemmaPostList.DilemmaPost;
import com.unir.grupo2.myzeancoach.ui.MCooperativeSol.dilemmaPostList.DilemmaPostListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cesar on 22/02/2017.
 */

public class MyDilemmasFragment extends Fragment implements DilemmaPostListAdapter.OnDilemmaPostClickListener{

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
            throw new ClassCastException(context.toString() + " must implements MyDilemmasFragment.OnDilemmaPostListener");
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

        DilemmaPost dilemmaPost1 = new DilemmaPost("12-12-2016", "ceo", "Title 1","asdkasdnkahdjahsdkjahdkjhakjsdhhasdhkjashdjkahsdkjas", "help_me",null);
        DilemmaPost dilemmaPost2 = new DilemmaPost("12-12-2016", "Ceo","Title 4","asdkasdnkahdjahsdkjahdkjhakjsdhhasdhkjashdjkahsdkjas", "feedback", null);
        DilemmaPost dilemmaPost3 = new DilemmaPost("12-12-2016", "Ceo","el ultimo titulo","asdkasdnkahdjahsdkjahdkjhakjsdhhasdhkjashdjkahsdkjas", "completed", null);

        dilemmaPostItemList = new ArrayList<DilemmaPost>();
        dilemmaPostItemList.add(dilemmaPost1);
        dilemmaPostItemList.add(dilemmaPost2);
        dilemmaPostItemList.add(dilemmaPost3);

        /*postItemList = new ArrayList<PostItem>();
        PostItem post1 = new PostItem("2-05-2016", "Viaje a New York", "viajes", "voy a viajar a New York, ¿Que me recomendais?", 4, 6,commentList);
        PostItem post2 = new PostItem("31-05-2017","Como el conocimiento de variso idiomas te hace cambir la manera en la que funciona tu cerebro", "idiomas", "a partir del 2 idioma aprendidio tu celebro cambia como procesa la inforamcion",52, 10,commentList);
        PostItem post3 = new PostItem("2-05-2016","Me voy a casar","eventos", "¿Que iglesia me recomendais?", 70, 30, commentList);
        PostItem post4 = new PostItem("2-05-2016","Aun no se con quien me voy a casar", "eventos", "Vladimir Kokorev, presunto testaferro de Teodoro Obiang, presidente de Guinea " +
                "Ecuatorial, encarcelado por supuesto delito fiscal y blanqueo de capitales, contrató por 140.000 euros a una empresa de comunicación para limpiar la imagen de su familia e investigar a un abogado y a dos periodistas, uno de ellos de EL PAÍS, según se desprende de la documentación intervenida por la Policía en el registro de su domicilio en la madrileña calle Ferraz. La investigación sobre la familia Kokorev se inició hace 12 años al detectarse que había recibido más de 30 millones procedentes de la Tesorería General de Guinea Ecuatorial", 100, 3,commentList);
        PostItem post5 = new PostItem("2-05-2016","Viaje al trabajo", "transporte", "¿Me recomendais venir a trabajar en metro?",54565, 123,null);
        PostItem post6 = new PostItem("2-05-2016","voy a empezar a programar en Android", "tecnologia", "¿Es buena idea?", 0, 0,null);
        postItemList.add(post1);
        postItemList.add(post2);
        postItemList.add(post3);
        postItemList.add(post4);
        postItemList.add(post5);
        postItemList.add(post6);*/

        dilemmaPostListRecyclerView.setVisibility(View.VISIBLE);
        loadingLayout.setVisibility(View.GONE);
        errorLayout.setVisibility(View.GONE);
        dilemmaPostListAdapter = new DilemmaPostListAdapter(getContext(),dilemmaPostItemList, this, false);
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

