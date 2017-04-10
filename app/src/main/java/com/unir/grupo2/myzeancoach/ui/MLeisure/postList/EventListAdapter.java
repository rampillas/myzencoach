package com.unir.grupo2.myzeancoach.ui.MLeisure.postList;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.domain.model.Event;
import com.unir.grupo2.myzeancoach.domain.utils.FooterSpaceViewHolder;
import com.unir.grupo2.myzeancoach.domain.utils.Utils;

import java.util.List;

/**
 * Created by Cesar on 11/03/2017.
 */

public class EventListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Event> eventItemList;
    private Context context;

    public interface OnEventClickListener {
        public void onAddCommentEventClick(Event event);

        public void onLikeEventClick(Event event, int position, boolean isLike);
    }

    EventListAdapter.OnEventClickListener listener;

    private class VIEWS_TYPES {
        public static final int Normal = 0;
        public static final int Footer = 1;
    }

    public EventListAdapter(Context context, List<Event> eventItemList, EventListAdapter.OnEventClickListener listener) {
        this.context = context;
        this.eventItemList = eventItemList;
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        if (viewType == VIEWS_TYPES.Footer) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.footer_space_for_floating_button_layout, viewGroup, false);
            return new FooterSpaceViewHolder(view);
        }else if (viewType == VIEWS_TYPES.Normal){
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.public_homepage_item_layout, viewGroup, false);
            final EventItemViewHolder eventItemViewHolder = new EventItemViewHolder(view);
            return eventItemViewHolder;
        }

        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof EventItemViewHolder) {
            final Event eventItem = eventItemList.get(position);
            EventItemViewHolder itemHolder = (EventItemViewHolder) holder;

            itemHolder.dateTextView.setText(eventItem.getDate());
            itemHolder.nickTextView.setText(eventItem.getUser());
            itemHolder.titleTextView.setText(eventItem.getTitle());
            itemHolder.categoryTextView.setText(Utils.getCategoryEvent(context, eventItem.getCategory()));
            itemHolder.descriptionTextView.setText(eventItem.getDescription());

            //like number
            itemHolder.likeNumberTextView.setText(eventItem.getLikes().toString());

            //***Like button
            if (eventItem.getUserLike().getIsLiked()) {
                itemHolder.likeButton.setTextColor(context.getColor(R.color.colorAccent));
                itemHolder.likeButton.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_thumb_up_orange_24dp, 0, 0, 0);
            } else {
                itemHolder.likeButton.setTextColor(context.getColor(R.color.black));
                itemHolder.likeButton.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_thumb_up_black_24dp, 0, 0, 0);
            }

            itemHolder.likeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (eventItem.getUserLike().getIsLiked()) {
                        listener.onLikeEventClick(eventItem, position, false);
                    } else {
                        listener.onLikeEventClick(eventItem, position, true);
                    }
                }
            });

            //***comment number
            if (eventItem.getComments() != null && !eventItem.getComments().isEmpty()) {
                itemHolder.commentNumberTextView.setText(String.format(context.getString(R.string.number_comments)
                        , eventItem.getComments().size()));
            } else {
                itemHolder.commentNumberTextView.setText(String.format(context.getString(R.string.number_comments)
                        , 0));
            }

            itemHolder.commentNumberTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onAddCommentEventClick(eventItem);
                }
            });

            //***Comment button
            itemHolder.commentButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onAddCommentEventClick(eventItem);
                }
            });
        }else{

        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionFooter(position)) {
            return VIEWS_TYPES.Footer;
        }
        return VIEWS_TYPES.Normal;
    }

    private boolean isPositionFooter (int position) {
        if (position == eventItemList.size()){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return eventItemList.size() + 1;
    }

}

