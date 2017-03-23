package com.unir.grupo2.myzeancoach.ui.MLeisure.postList;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.domain.Utils;

import java.util.List;

/**
 * Created by Cesar on 11/03/2017.
 */

public class PostListAdapter  extends RecyclerView.Adapter<PostItemViewHolder>  {

    private List<PostItem> postItemList;
    private Context context;

    public interface OnPostClickListener{
        public void onAddCommentPostClick(PostItem post);
        public void onNumberCommentPostClick(PostItem post);
        public void onLikePostClick(PostItem post, int position, boolean isLike);
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(PostItemViewHolder viewHolder, final int position) {
        final PostItem postItem = postItemList.get(position);
        PostItemViewHolder itemHolder = (PostItemViewHolder) viewHolder;

        itemHolder.dateTextView.setText(postItem.getDate());
        itemHolder.titleTextView.setText(postItem.getTitle());
        itemHolder.categoryTextView.setText(postItem.getCategory());
        itemHolder.descriptionTextView.setText(postItem.getDescription());

        //like number
        if (postItem.getLikes() != null && !postItem.getLikes().isEmpty()){
            itemHolder.likeNumberTextView.setText(Integer.toString(postItem.getLikes().size()));
        }else{
            itemHolder.likeNumberTextView.setText("0");
        }

        //***Like button
        if (postItem.getLikes() != null && !postItem.getLikes().isEmpty()){

            if (isLike(postItem)){
                itemHolder.likeButton.setTextColor(context.getColor(R.color.colorAccent));
                itemHolder.likeButton.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_thumb_up_orange_24dp, 0, 0, 0);
            }else{
                itemHolder.likeButton.setTextColor(context.getColor(R.color.black));
                itemHolder.likeButton.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_thumb_up_black_24dp, 0, 0, 0);
            }
        }else{
            itemHolder.likeButton.setTextColor(context.getColor(R.color.black));
            itemHolder.likeButton.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_thumb_up_black_24dp, 0, 0, 0);
        }
        itemHolder.likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLike(postItem)){
                    listener.onLikePostClick(postItem, position, false);
                }else{
                    listener.onLikePostClick(postItem, position, true);
                }

            }
        });

        //***comment number
        if (postItem.getComments() != null && !postItem.getComments().isEmpty()){
            itemHolder.commentNumberTextView.setText(String.format(context.getString(R.string.number_comments)
                    , postItem.getComments().size()));
        }else{
            itemHolder.commentNumberTextView.setText(String.format(context.getString(R.string.number_comments)
                    , 0));
        }

        itemHolder.commentNumberTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onNumberCommentPostClick(postItem);
            }
        });

        //***Comment button
        itemHolder.commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onAddCommentPostClick(postItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return postItemList.size();
    }

    private boolean isLike(PostItem postItem){
        boolean isLike = false;
        if (postItem.getLikes() != null && !postItem.getLikes().isEmpty()) {
            for (Like like : postItem.getLikes()) {
                if (like.getUser().getUsername().equals(Utils.getUserFromPreference(context))) {
                    isLike = true;
                    break;
                }
            }
        }
        return isLike;
    }
}

