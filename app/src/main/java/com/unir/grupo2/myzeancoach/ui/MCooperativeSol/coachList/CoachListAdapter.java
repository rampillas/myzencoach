package com.unir.grupo2.myzeancoach.ui.MCooperativeSol.coachList;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.domain.model.Dilemma;
import com.unir.grupo2.myzeancoach.domain.utils.FooterSpaceViewHolder;

import java.util.List;

/**
 * Created by Cesar on 11/03/2017.
 */

public class CoachListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private class VIEWS_TYPES {
        public static final int Normal = 0;
        public static final int Footer = 1;
    }

    private List<Dilemma> dilemmaPostItemList;
    private Context context;

    public interface OnDilemmaCoachClickListener {
        public void onItemCilemmaCoachClick(Dilemma dilemma, int position);
    }

    CoachListAdapter.OnDilemmaCoachClickListener listener;

    public CoachListAdapter(Context context, List<Dilemma> dilemmaPostItemList, CoachListAdapter.OnDilemmaCoachClickListener listener) {
        this.context = context;
        this.dilemmaPostItemList = dilemmaPostItemList;
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (viewType == VIEWS_TYPES.Footer) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.footer_space_for_floating_button_layout, viewGroup, false);
            return new FooterSpaceViewHolder(view);
        } else if (viewType == VIEWS_TYPES.Normal) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.coop_sol_coach_list_item, viewGroup, false);
            return new CoachViewHolder(view);
        }
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof CoachViewHolder) {
            CoachViewHolder normalHolder = (CoachViewHolder) holder;
            normalHolder.bind(dilemmaPostItemList.get(position), context, listener, position);

        } else if (holder instanceof FooterSpaceViewHolder) {

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
        if (position == dilemmaPostItemList.size()){
            return true;
        }else{
            return false;
        }
    }


    @Override
    public int getItemCount() {
        return dilemmaPostItemList.size() + 1;
    }
}

