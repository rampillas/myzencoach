package com.unir.grupo2.myzeancoach.ui.MCooperativeSol.dilemmaPostList;

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

public class DilemmaPostListAdapter extends RecyclerView.Adapter<DilemmaPostItemViewHolder>  {

    private List<DilemmaPost> postItemList;
    private Context context;

    public interface OnPostClickListener{
        public void onItemPostClick(DilemmaPost post);
        public void onAddCommentPostClick(DilemmaPost post);
        public void onNumberCommentPostClick(DilemmaPost post);
        public void onLikePostClick(DilemmaPost post);
        public void onNumberLikePostClick(DilemmaPost post);
    }

    DilemmaPostListAdapter.OnPostClickListener listener;

    public DilemmaPostListAdapter(Context context, List<DilemmaPost> postItemList, DilemmaPostListAdapter.OnPostClickListener listener) {
        this.context = context;
        this.postItemList = postItemList;
        this.listener = listener;
    }

    @Override
    public DilemmaPostItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.public_homepage_item_layout, viewGroup, false);
        final DilemmaPostItemViewHolder postItemViewHolder = new DilemmaPostItemViewHolder(view);

        return postItemViewHolder;
    }

    @Override
    public void onBindViewHolder(DilemmaPostItemViewHolder viewHolder, int position) {
        DilemmaPost postItem = postItemList.get(position);
        viewHolder.bind(postItemList.get(position),listener);
    }

    @Override
    public int getItemCount() {
        return postItemList.size();
    }
}

