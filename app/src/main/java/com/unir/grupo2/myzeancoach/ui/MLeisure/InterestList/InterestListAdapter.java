package com.unir.grupo2.myzeancoach.ui.MLeisure.InterestList;

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

public class InterestListAdapter extends RecyclerView.Adapter<InteresItemViewHolder>  {

    private List<InterestItem> interestItemList;
    private Context context;

   /* public interface OnItemInterestClickListener{
        public void onItemInterestClick(InterestItem interest);
    }

    InterestListAdapter.OnItemInterestClickListener listener;*/

    public InterestListAdapter(Context context, List<InterestItem> interestItemList) {
        this.context = context;
        this.interestItemList = interestItemList;
    }

    @Override
    public InteresItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.interest_item_layout, viewGroup, false);
        final InteresItemViewHolder interestItemViewHolder = new InteresItemViewHolder(view);

        return interestItemViewHolder;
    }

    @Override
    public void onBindViewHolder(InteresItemViewHolder viewHolder, int position) {
        InterestItem interestItem = interestItemList.get(position);
        viewHolder.bind(interestItemList.get(position));
    }

    @Override
    public int getItemCount() {
        return interestItemList.size();
    }
}

