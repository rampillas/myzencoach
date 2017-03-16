package com.unir.grupo2.myzeancoach.ui.MCustomizeFragment.remaindersList;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unir.grupo2.myzeancoach.domain.model.Remainders;
import com.unir.grupo2.myzeancoach.domain.model.Test;
import com.unir.grupo2.myzeancoach.ui.MEssentialInfo.testList.TestItemViewHolder;

import java.util.List;

/**
 * Created by andres on 16/03/2017.
 */

public class RemaindersListAdapter extends RecyclerView.Adapter<RemaindersItemViewHolder> {
    private Context context;
    private List<Remainders> remaindersItemList;
    private RemaindersListAdapter thisAdapter = this;

    public interface OnItemClickListener{
        public void onItemClick(Remainders remaindersItem);
    }

    private final OnItemClickListener listener;

    public RemaindersListAdapter(Context context, List<Remainders> remaindersItemList, RemaindersListAdapter.OnItemClickListener listener){
        this.context = context;
        this.remaindersItemList = remaindersItemList;
        this.listener = listener;
    }

    //We create the viewHolder
    @Override
    public RemaindersItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.remainders_layout_item, parent, false);
        final RemaindersItemViewHolder remaindersItemViewHolder = new RemaindersItemViewHolder(view);

        return remaindersItemViewHolder;
    }


    @Override
    public void onBindViewHolder(RemaindersItemViewHolder remaindersItemViewHolder, int position) {
        final Remainders remainderItem = remaindersItemList.get(position);
        remaindersItemViewHolder.bind(remaindersItemList.get(position),listener);
    }

    @Override
    public int getItemCount() {
        return remaindersItemList.size();
    }
}
