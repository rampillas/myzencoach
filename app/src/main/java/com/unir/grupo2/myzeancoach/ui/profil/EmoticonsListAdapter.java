package com.unir.grupo2.myzeancoach.ui.profil;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unir.grupo2.myzeancoach.R;

import java.util.List;

/**
 * Created by Cesar on 18/03/2017.
 */

public class EmoticonsListAdapter extends RecyclerView.Adapter<EmoticonsItemViewHolder>{
    private List<String> emoticonNameItemList;
    private Context context;

    public interface OnItemEmoticonClickListener{
        public void onItemEmoticonClick(String emoticonName);
    }

    EmoticonsListAdapter.OnItemEmoticonClickListener listener;

    public EmoticonsListAdapter(Context context, List<String> emoticonNameItemList, EmoticonsListAdapter.OnItemEmoticonClickListener listener) {
        this.context = context;
        this.emoticonNameItemList = emoticonNameItemList;
        this.listener = listener;
    }

    @Override
    public EmoticonsItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.profil_emoticon_list_item_layout, viewGroup, false);
        final EmoticonsItemViewHolder emticonsItemViewHolder = new EmoticonsItemViewHolder(view);

        return emticonsItemViewHolder;
    }

    @Override
    public void onBindViewHolder(EmoticonsItemViewHolder viewHolder, int position) {
        String emoticonName = emoticonNameItemList.get(position);

        viewHolder.bind(emoticonName,listener,context);

    }

    @Override
    public int getItemCount() {
        return emoticonNameItemList.size();
    }
}

