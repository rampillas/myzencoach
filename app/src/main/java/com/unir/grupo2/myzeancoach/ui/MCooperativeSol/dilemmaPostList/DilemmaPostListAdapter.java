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

    private List<DilemmaPost> dilemmaPostItemList;
    private Context context;
    private boolean fromMyDilemma;

    public interface OnDilemmaPostClickListener{
        public void onItemDilemmaPostClick(DilemmaPost post,boolean fromMyDilemma);
    }

    DilemmaPostListAdapter.OnDilemmaPostClickListener listener;

    public DilemmaPostListAdapter(Context context, List<DilemmaPost> dilemmaPostItemList, DilemmaPostListAdapter.OnDilemmaPostClickListener listener, boolean fromMyDilemma) {
        this.context = context;
        this.dilemmaPostItemList = dilemmaPostItemList;
        this.listener = listener;
        this.fromMyDilemma = fromMyDilemma;
    }

    @Override
    public DilemmaPostItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.coop_sol_homepage_item_layout, viewGroup, false);
        final DilemmaPostItemViewHolder dilemmaPostItemViewHolder = new DilemmaPostItemViewHolder(view);

        return dilemmaPostItemViewHolder;
    }

    @Override
    public void onBindViewHolder(DilemmaPostItemViewHolder viewHolder, int position) {
        DilemmaPost dilemmaPostItem = dilemmaPostItemList.get(position);
        viewHolder.bind(dilemmaPostItemList.get(position),listener, fromMyDilemma, context);
    }

    @Override
    public int getItemCount() {
        return dilemmaPostItemList.size();
    }
}

