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
        public void onItemEmoticonClick(int position);
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

        switch (position) {
            case 0:
                viewHolder.emoticonImageView.setImageResource(R.mipmap.ic_very_happy);
                break;
            case 1:
                viewHolder.emoticonImageView.setImageResource(R.mipmap.ic_happy);
                break;
            case 2:
                viewHolder.emoticonImageView.setImageResource(R.mipmap.ic_in_love);
                break;
            case 3:
                viewHolder.emoticonImageView.setImageResource(R.mipmap.ic_laughing);
                break;
            case 4:
                viewHolder.emoticonImageView.setImageResource(R.mipmap.ic_sat);
                break;
            case 5:
                viewHolder.emoticonImageView.setImageResource(R.mipmap.ic_disappointed);
                break;
            case 6:
                viewHolder.emoticonImageView.setImageResource(R.mipmap.ic_crying);
                break;
            case 7:
                viewHolder.emoticonImageView.setImageResource(R.mipmap.ic_angry);
                break;
            default:
                viewHolder.emoticonImageView.setImageResource(R.mipmap.ic_happy);
                break;
        }

        viewHolder.nameTextView.setText(emoticonName);

        listener.onItemEmoticonClick(position);
    }

    @Override
    public int getItemCount() {
        return emoticonNameItemList.size();
    }
}

