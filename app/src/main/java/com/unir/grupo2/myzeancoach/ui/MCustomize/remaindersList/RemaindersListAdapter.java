package com.unir.grupo2.myzeancoach.ui.MCustomize.remaindersList;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.domain.utils.FooterSpaceViewHolder;

import java.util.List;

/**
 * Created by andres on 16/03/2017.
 */

public class RemaindersListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<RemainderItemObject> remaindersItemList;
    private RemaindersListAdapter thisAdapter = this;

    public interface OnItemClickListener {
        public void onItemClick(RemainderItemObject remaindersItem);

        public void onCompleteClick(RemainderItemObject remainderItem);

        public void onAddClick(RemainderItemObject remainderItem);

        public void onAddObservations(RemainderItemObject remainderItem);
    }

    private final OnItemClickListener listener;

    private class VIEWS_TYPES {
        public static final int Normal = 0;
        public static final int Footer = 1;
    }

    public RemaindersListAdapter(Context context, List<RemainderItemObject> remaindersItemList, RemaindersListAdapter.OnItemClickListener listener) {
        this.context = context;
        this.remaindersItemList = remaindersItemList;
        this.listener = listener;
    }

    //We create the viewHolder
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == VIEWS_TYPES.Footer) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_space_for_floating_button_layout, parent, false);
            return new FooterSpaceViewHolder(view);
        }else{
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.remainders_layout_item, parent, false);
            final RemaindersItemViewHolder remaindersItemViewHolder = new RemaindersItemViewHolder(view);
            return remaindersItemViewHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof RemaindersItemViewHolder){
            final RemainderItemObject remainderItemObject = remaindersItemList.get(position);
            ((RemaindersItemViewHolder) holder).bind(remaindersItemList.get(position), listener);
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
        if (position == remaindersItemList.size()){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return remaindersItemList.size() + 1;
    }
}
