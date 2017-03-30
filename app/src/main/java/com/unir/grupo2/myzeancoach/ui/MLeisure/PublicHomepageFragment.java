package com.unir.grupo2.myzeancoach.ui.MLeisure;

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
import android.widget.LinearLayout;

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.domain.utils.Utils;
import com.unir.grupo2.myzeancoach.domain.model.User;
import com.unir.grupo2.myzeancoach.ui.MLeisure.commentList.CommentItem;
import com.unir.grupo2.myzeancoach.ui.MLeisure.postList.Like;
import com.unir.grupo2.myzeancoach.ui.MLeisure.postList.EventItem;
import com.unir.grupo2.myzeancoach.ui.MLeisure.postList.EventListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Cesar on 22/02/2017.
 */

public class PublicHomepageFragment extends Fragment implements EventListAdapter.OnEventClickListener {


    List<EventItem> postItemList;
    EventListAdapter postListAdapter;

    @BindView(R.id.post_recycler_view) RecyclerView postListRecyclerView;
    @BindView(R.id.loading_layout) LinearLayout loadingLayout;
    @BindView(R.id.error_layout) LinearLayout errorLayout;
    @BindView(R.id.floating_action_button) FloatingActionButton floatingActionButton;

    PublicHomepageFragment.OnPostListener postListener;

    public interface OnPostListener {

        void onAddEventSelected();

        void onNumberLikePostSelected(EventItem post);

        void onCommentPostSelected(EventItem post);

        void onNumberCommentPostSelected(EventItem post);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof PublicHomepageFragment.OnPostListener) {
            postListener = (PublicHomepageFragment.OnPostListener) context;
        } else {
            throw new ClassCastException(context.toString() + " must implements PublicHomepageFragment.OnPostListener");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.public_homepage_layout, null);
        ButterKnife.bind(this, view);

        //updateData();

        postListRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        postListRecyclerView.setLayoutManager(linearLayoutManager);

        CommentItem commentItem1 = new CommentItem("12-12-2016", "primer comentario de prueba");
        CommentItem commentItem2 = new CommentItem("05-10-2012", "segundo comentario de prueba");
        CommentItem commentItem3 = new CommentItem("12-12-2016", "terceo comentario de prueba");
        CommentItem commentItem4 = new CommentItem("12-12-2016", "cuarto comentario de prueba");
        CommentItem commentItem5 = new CommentItem("12-12-2016", "quinto comentario de prueba");
        CommentItem commentItem6 = new CommentItem("12-12-2016", "sexto comentario de prueba");
        CommentItem commentItem7 = new CommentItem("12-12-2016", "septimo comentario de prueba");

        ArrayList<CommentItem> commentList = new ArrayList<CommentItem>();
        commentList.add(commentItem1);
        commentList.add(commentItem2);
        commentList.add(commentItem3);
        commentList.add(commentItem4);
        commentList.add(commentItem5);
        commentList.add(commentItem6);
        commentList.add(commentItem7);
        commentList.add(commentItem7);
        commentList.add(commentItem7);

        ArrayList<Like> likeslist1 = new ArrayList<>();
        User userMerlu = new User();
        userMerlu.setUsername("merlu");
        User userCeo = new User();
        userCeo.setUsername("ceo");
        User userArmin = new User();
        userArmin.setUsername("armin");
        Like like = new Like(userMerlu);
        Like like2 = new Like(userCeo);
        Like like3= new Like(userArmin);

        likeslist1.add(like);
        likeslist1.add(like2);
        likeslist1.add(like3);

        ArrayList<Like> likeslist2 = new ArrayList<>();
        likeslist2.add(like);
        likeslist2.add(like2);
        likeslist2.add(like3);

        ArrayList<Like> likeslist3 = new ArrayList<>();
        likeslist3.add(like);
        likeslist3.add(like2);
        likeslist3.add(like3);

        ArrayList<Like> likeslist4 = new ArrayList<>();
        likeslist4.add(like);
        likeslist4.add(like2);
        likeslist4.add(like3);

        postItemList = new ArrayList<EventItem>();
        EventItem post1 = new EventItem("2-05-2016", "Viaje a New York", "viajes", "voy a viajar a New York, ¿Que me recomendais?", likeslist1, commentList);
        EventItem post2 = new EventItem("31-05-2017", "Como el conocimiento de variso idiomas te hace cambir la manera en la que funciona tu cerebro", "idiomas", "a partir del 2 idioma aprendidio tu celebro cambia como procesa la inforamcion", likeslist2, commentList);
        EventItem post3 = new EventItem("2-05-2016", "Me voy a casar", "eventos", "¿Que iglesia me recomendais?", likeslist3, commentList);
        EventItem post4 = new EventItem("2-05-2016", "Aun no se con quien me voy a casar", "eventos", "Vladimir Kokorev, presunto testaferro de Teodoro Obiang, presidente de Guinea " +
                "Ecuatorial, encarcelado por supuesto delito fiscal y blanqueo de capitales, contrató por 140.000 euros a una empresa de comunicación para limpiar la imagen de su familia e investigar a un abogado y a dos periodistas, uno de ellos de EL PAÍS, según se desprende de la documentación intervenida por la Policía en el registro de su domicilio en la madrileña calle Ferraz. La investigación sobre la familia Kokorev se inició hace 12 años al detectarse que había recibido más de 30 millones procedentes de la Tesorería General de Guinea Ecuatorial", likeslist4, commentList);
        EventItem post5 = new EventItem("2-05-2016", "Viaje al trabajo", "transporte", "¿Me recomendais venir a trabajar en metro?", null, null);
        EventItem post6 = new EventItem("2-05-2016", "voy a empezar a programar en Android", "tecnologia", "¿Es buena idea?", null, null);
        postItemList.add(post1);
        postItemList.add(post2);
        postItemList.add(post3);
        postItemList.add(post4);
        postItemList.add(post5);
        postItemList.add(post6);
        postListRecyclerView.setVisibility(View.VISIBLE);
        loadingLayout.setVisibility(View.GONE);
        errorLayout.setVisibility(View.GONE);
        postListAdapter = new EventListAdapter(getContext(), postItemList, this);
        postListRecyclerView.setAdapter(postListAdapter);

        return view;
    }

    @Override
    public void onAddCommentEventClick(EventItem post) {
        postListener.onCommentPostSelected(post);
    }

    @Override
    public void onNumberCommentEventClick(EventItem post) {
        postListener.onNumberCommentPostSelected(post);
    }

    @Override
    public void onLikeEventClick(EventItem post, int position, boolean toLike) {
        String username = Utils.getUserFromPreference(getContext());

        if (toLike){
            User user = new User();
            user.setUsername(username);
            Like like = new Like(user);
            if (postItemList.get(position).getLikes() != null){
                postItemList.get(position).getLikes().add(like);
            }else{
                ArrayList<Like> likes = new ArrayList<>();
                likes.add(like);
                postItemList.get(position).setLikes(likes);
            }
        }else{
            for (int i = 0; i < postItemList.get(position).getLikes().size(); i++){
                if (postItemList.get(position).getLikes().get(i).getUser().getUsername().equals(username)){
                    postItemList.get(position).getLikes().remove(i);
                    break;
                }
            }
        }
        postListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onNumberLikeEventClick(EventItem post) {
        postListener.onNumberLikePostSelected(post);
    }


    @OnClick(R.id.floating_action_button)
    void addEvent(View view) {
        postListener.onAddEventSelected();
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

