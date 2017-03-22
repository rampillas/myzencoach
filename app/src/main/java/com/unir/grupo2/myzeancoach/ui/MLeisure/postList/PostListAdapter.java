package com.unir.grupo2.myzeancoach.ui.MLeisure.postList;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unir.grupo2.myzeancoach.R;

import java.util.List;

/**
 * Created by Cesar on 11/03/2017.
 */

public class PostListAdapter  extends RecyclerView.Adapter<PostItemViewHolder>  {

    private List<PostItem> postItemList;
    private Context context;

    public interface OnPostClickListener{
        public void onItemPostClick(PostItem post);
        public void onAddCommentPostClick(PostItem post);
        public void onNumberCommentPostClick(PostItem post);
        public void onLikePostClick(PostItem post);
        public void onNumberLikePostClick(PostItem post);
    }

    PostListAdapter.OnPostClickListener listener;

    public PostListAdapter(Context context, List<PostItem> postItemList, PostListAdapter.OnPostClickListener listener) {
        this.context = context;
        this.postItemList = postItemList;
        this.listener = listener;
    }

    @Override
    public PostItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.public_homepage_item_layout, viewGroup, false);
        final PostItemViewHolder postItemViewHolder = new PostItemViewHolder(view);

        return postItemViewHolder;
    }

    @Override
    public void onBindViewHolder(PostItemViewHolder viewHolder, int position) {
        viewHolder.bind(postItemList.get(position),listener);
    }

    @Override
    public int getItemCount() {
        return postItemList.size();
    }
}

