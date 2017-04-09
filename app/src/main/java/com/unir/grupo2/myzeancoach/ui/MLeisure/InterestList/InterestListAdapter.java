package com.unir.grupo2.myzeancoach.ui.MLeisure.InterestList;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.domain.model.Interest;

import java.util.List;

/**
 * Created by Cesar on 11/03/2017.
 */

public class InterestListAdapter extends RecyclerView.Adapter<InteresItemViewHolder>  {

    private List<Interest> interestItemList;
    private Context context;
    private String[] categories;

    public interface OnItemInterestClickListener{
        public void onItemInterestClick(int position, boolean isToAdd);
    }

    InterestListAdapter.OnItemInterestClickListener listener;

    public InterestListAdapter(Context context, List<Interest> interestItemList, String[] categories,
                               InterestListAdapter.OnItemInterestClickListener listener) {
        this.context = context;
        this.interestItemList = interestItemList;
        this.categories = categories;
        this.listener = listener;
    }

    @Override
    public InteresItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.interest_item_layout, viewGroup, false);
        final InteresItemViewHolder interestItemViewHolder = new InteresItemViewHolder(view);

        return interestItemViewHolder;
    }

    @Override
    public void onBindViewHolder(InteresItemViewHolder viewHolder, final int position) {

        String category;

        switch (categories[position]){
            case "viajes":
                category = context.getString(R.string.array_category_trip);
                break;
            case "tecnologia":
                category = context.getString(R.string.array_category_technology);
                break;
            case "naturaleza":
                category = context.getString(R.string.array_category_nature);
                break;
            case "deportes":
                category = context.getString(R.string.array_category_sport);
                break;
            case "salud":
                category = context.getString(R.string.array_category_health);
                break;
            case "naval":
                category = context.getString(R.string.array_category_naval);
                break;
            case "trabajo":
                category = context.getString(R.string.array_category_work);
                break;
            default:
                throw new IllegalArgumentException("Invalid category");
        }

        final InteresItemViewHolder itemHolder = (InteresItemViewHolder) viewHolder;

        itemHolder.checkedTextView.setText(category);

        boolean isChecked = false;
        for (int i = 0; i < interestItemList.size(); i++){
            if (interestItemList.get(i).getName().equals(category)){
                isChecked = true;
            }
        }
        itemHolder.checkedTextView.setChecked(isChecked);

        itemHolder.checkedTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemHolder.checkedTextView.isChecked()) {
                    itemHolder.checkedTextView.setChecked(false);
                    listener.onItemInterestClick(position, false);
                }else {
                    itemHolder.checkedTextView.setChecked(true);
                    listener.onItemInterestClick(position, true);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.length;
    }
}

