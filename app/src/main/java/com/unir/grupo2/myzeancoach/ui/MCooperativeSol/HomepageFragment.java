package com.unir.grupo2.myzeancoach.ui.MCooperativeSol;

import android.support.v4.app.Fragment;

/**
 * Created by Cesar on 22/02/2017.
 */

public class HomepageFragment extends Fragment{

  /*  List<PostItem> postItemList;
    PostListAdapter postListAdapter;
    private UseCase useCase;

    @BindView(R.id.sol_coop_post_recycler_view) RecyclerView dilemmaPostListRecyclerView;
    @BindView(R.id.loading_layout) LinearLayout loadingLayout;
    @BindView(R.id.error_layout) LinearLayout errorLayout;

    PublicHomepageFragment.OnPostListener postListener;

    public interface OnPostListener{
        void onItemPostSelected(PostItem post);
        void onAddPostSelected();
        void onLikePostSelected(PostItem post);
        void onNumberLikePostSelected(PostItem post);
        void onCommentPostSelected(PostItem post);
        void onNumberCommentPostSelected(PostItem post);
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if (context instanceof PublicHomepageFragment.OnPostListener){
            postListener = (PublicHomepageFragment.OnPostListener) context;
        }else{
            throw new ClassCastException(context.toString() + " must implements PublicHomepageFragment.OnPostListener");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.coop_sol_homepage_layout,null);
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

        postItemList = new ArrayList<PostItem>();
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
        postListener.onItemPostSelected(post);
    }

    @Override
    public void onAddCommentPostClick(PostItem post) {
        postListener.onCommentPostSelected(post);
    }

    @Override
    public void onNumberCommentPostClick(PostItem post) {
        postListener.onNumberCommentPostSelected(post);
    }

    @Override
    public void onLikePostClick(PostItem post) {
        postListener.onLikePostSelected(post);
    }

    @Override
    public void onNumberLikePostClick(PostItem post) {
        postListener.onNumberLikePostSelected(post);
    }


    @OnClick(R.id.floating_action_button)
    void addPost(View view) {
        postListener.onAddPostSelected();
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

