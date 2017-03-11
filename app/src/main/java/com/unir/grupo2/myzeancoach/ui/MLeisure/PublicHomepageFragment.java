package com.unir.grupo2.myzeancoach.ui.MLeisure;

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
import com.unir.grupo2.myzeancoach.ui.MLeisure.postList.PostItem;
import com.unir.grupo2.myzeancoach.ui.MLeisure.postList.PostListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cesar on 22/02/2017.
 */

public class PublicHomepageFragment extends Fragment implements PostListAdapter.OnItemPostClickListener{


    List<PostItem> postItemList;
    PostListAdapter postListAdapter;
    private UseCase useCase;

    @BindView(R.id.post_recycler_view) RecyclerView postListRecyclerView;
    @BindView(R.id.loading_layout) LinearLayout loadingLayout;
    @BindView(R.id.error_layout) LinearLayout errorLayout;


    PublicHomepageFragment.OnItemPostSelectedListener onItemPostSelectedListener;

    public interface OnItemPostSelectedListener{
        void onItemPostSelected(PostItem post);
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if (context instanceof PublicHomepageFragment.OnItemPostSelectedListener){
            onItemPostSelectedListener = (PublicHomepageFragment.OnItemPostSelectedListener) context;
        }else{
            throw new ClassCastException(context.toString() + " must implements PublicHomepageFragment.OnItemPostSelectedListener");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.public_homepage_layout,null);
        ButterKnife.bind(this, view);

        //updateData();

        postListRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        postListRecyclerView.setLayoutManager(linearLayoutManager);

        postItemList = new ArrayList<PostItem>();
        PostItem post1 = new PostItem("Viaje a New York", "viajes", "voy a viajar a New York, ¿Que me recomendais?", 4, 6);
        PostItem post2 = new PostItem("Como el conocimiento de variso idiomas te hace cambir la manera en la que funciona tu cerebro", "idiomas", "a partir del 2 idioma aprendidio tu celebro cambia como procesa la inforamcion",52, 10);
        PostItem post3 = new PostItem("Me voy a casar","eventos", "¿Que iglesia me recomendais?", 70, 30);
        PostItem post4 = new PostItem("Aun no se con quien me voy a casar", "eventos", "Vladimir Kokorev, presunto testaferro de Teodoro Obiang, presidente de Guinea Ecuatorial, encarcelado por supuesto delito fiscal y blanqueo de capitales, contrató por 140.000 euros a una empresa de comunicación para limpiar la imagen de su familia e investigar a un abogado y a dos periodistas, uno de ellos de EL PAÍS, según se desprende de la documentación intervenida por la Policía en el registro de su domicilio en la madrileña calle Ferraz. La investigación sobre la familia Kokorev se inició hace 12 años al detectarse que había recibido más de 30 millones procedentes de la Tesorería General de Guinea Ecuatorial", 100, 3);
        PostItem post5 = new PostItem("Viaje al trabajo", "transporte", "¿Me recomendais venir a trabajar en metro?",54565, 123);
        PostItem post6 = new PostItem("voy a empezar a programar en Android", "tecnologia", "¿Es buena idea?", 0, 0);
        postItemList.add(post1);
        postItemList.add(post2);
        postItemList.add(post3);
        postItemList.add(post4);
        postItemList.add(post5);
        postItemList.add(post6);
        postListRecyclerView.setVisibility(View.VISIBLE);
        loadingLayout.setVisibility(View.GONE);
        errorLayout.setVisibility(View.GONE);
        postListAdapter = new PostListAdapter(getContext(),postItemList, this);
        postListRecyclerView.setAdapter(postListAdapter);

        return view;
    }

    @Override
    public void onItemPostClick(PostItem post) {
        onItemPostSelectedListener.onItemPostSelected(post);
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

