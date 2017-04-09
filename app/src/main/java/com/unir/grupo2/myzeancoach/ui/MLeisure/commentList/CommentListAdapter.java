package com.unir.grupo2.myzeancoach.ui.MLeisure.commentList;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.domain.model.CommentEvent;

import java.util.List;

/**
 * Created by Cesar on 12/03/2017.
 */

public class CommentListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<CommentEvent> commentItemList;

    public CommentListAdapter(Context context, List<CommentEvent> commentItemList) {
        this.context = context;
        this.commentItemList = commentItemList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_comment_list_item_layout, parent, false);
        return new CommentItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof CommentItemViewHolder) {

            final CommentEvent commentItem = commentItemList.get(position);
            CommentItemViewHolder itemHolder = (CommentItemViewHolder) holder;

            if (position == 0) {
                itemHolder.lineView.setVisibility(View.GONE);
            }
            itemHolder.descriptionTextView.setText(commentItem.getDescription());
            itemHolder.dateTextView.setText(commentItem.getDate());
        }
    }

    @Override
    public int getItemCount() {
        return commentItemList.size();
    }

}
