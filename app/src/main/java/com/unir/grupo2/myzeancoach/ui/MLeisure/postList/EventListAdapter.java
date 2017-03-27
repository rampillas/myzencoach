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

public class EventListAdapter extends RecyclerView.Adapter<EventItemViewHolder>  {

    private List<EventItem> eventItemList;
    private Context context;

    public interface OnEventClickListener{
        public void onAddCommentEventClick(EventItem post);
        public void onNumberCommentEventClick(EventItem post);
        public void onLikeEventClick(EventItem post, int position, boolean isLike);
        public void onNumberLikeEventClick(EventItem post);
    }

    EventListAdapter.OnEventClickListener listener;

    public EventListAdapter(Context context, List<EventItem> eventItemList, EventListAdapter.OnEventClickListener listener) {
        this.context = context;
        this.eventItemList = eventItemList;
        this.listener = listener;
    }

    @Override
    public EventItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.public_homepage_item_layout, viewGroup, false);
        final EventItemViewHolder eventItemViewHolder = new EventItemViewHolder(view);

        return eventItemViewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(EventItemViewHolder viewHolder, final int position) {
        final EventItem eventItem = eventItemList.get(position);
        EventItemViewHolder itemHolder = (EventItemViewHolder) viewHolder;

        itemHolder.dateTextView.setText(eventItem.getDate());
        itemHolder.titleTextView.setText(eventItem.getTitle());
        itemHolder.categoryTextView.setText(eventItem.getCategory());
        itemHolder.descriptionTextView.setText(eventItem.getDescription());

        //like number
        if (eventItem.getLikes() != null && !eventItem.getLikes().isEmpty()){
            itemHolder.likeNumberTextView.setText(Integer.toString(eventItem.getLikes().size()));
        }else{
            itemHolder.likeNumberTextView.setText("0");
        }

        //***Like button
        if (eventItem.getLikes() != null && !eventItem.getLikes().isEmpty()){

            if (isLike(eventItem)){
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
                if (isLike(eventItem)){
                    listener.onLikeEventClick(eventItem, position, false);
                }else{
                    listener.onLikeEventClick(eventItem, position, true);
                }

            }
        });

        //***comment number
        if (eventItem.getComments() != null && !eventItem.getComments().isEmpty()){
            itemHolder.commentNumberTextView.setText(String.format(context.getString(R.string.number_comments)
                    , eventItem.getComments().size()));
        }else{
            itemHolder.commentNumberTextView.setText(String.format(context.getString(R.string.number_comments)
                    , 0));
        }

        itemHolder.commentNumberTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onNumberCommentEventClick(eventItem);
            }
        });

        //***Comment button
        itemHolder.commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onAddCommentEventClick(eventItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return eventItemList.size();
    }

    private boolean isLike(EventItem postItem){
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
