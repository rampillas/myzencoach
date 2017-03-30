package com.unir.grupo2.myzeancoach.ui.MCooperativeSol.dilemmaPostList;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.domain.model.Dilemma;

import java.util.List;

/**
 * Created by Cesar on 11/03/2017.
 */

public class DilemmaPostListAdapter extends RecyclerView.Adapter<DilemmaPostItemViewHolder>  {

    private List<Dilemma> dilemmaPostItemList;
    private Context context;
    private boolean fromMyDilemma;

    public interface OnDilemmaPostClickListener{
        public void onItemDilemmaPostClick(Dilemma post,boolean fromMyDilemma);
    }

    DilemmaPostListAdapter.OnDilemmaPostClickListener listener;

    public DilemmaPostListAdapter(Context context, List<Dilemma> dilemmaPostItemList, DilemmaPostListAdapter.OnDilemmaPostClickListener listener, boolean fromMyDilemma) {
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(DilemmaPostItemViewHolder viewHolder, int position) {
        viewHolder.bind(dilemmaPostItemList.get(position),listener, fromMyDilemma, context);
    }

    @Override
    public int getItemCount() {
        return dilemmaPostItemList.size();
    }
}

