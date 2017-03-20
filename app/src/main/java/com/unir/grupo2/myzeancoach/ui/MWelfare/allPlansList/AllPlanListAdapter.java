package com.unir.grupo2.myzeancoach.ui.MWelfare.allPlansList;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.domain.model.PlanWelfare;

import java.util.List;

/**
 * Created by vaiojr on 15/03/2017.
 */

public class AllPlanListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<PlanWelfare> planList;
    AllPlanListAdapter thisAdapter = this;
    int ongoingPlanPosition = -1;

    private class VIEWS_TYPES{
        public static final int Header = 1;
        public static final int Normal = 2;
    }

    boolean isHeader = false;

    public interface OnItemPlanClickListener{
        public void onItemPlanClick(PlanWelfare plan);
    }

    AllPlanListAdapter.OnItemPlanClickListener listener;


    public AllPlanListAdapter(Context context, List<PlanWelfare> planList, AllPlanListAdapter.OnItemPlanClickListener listener) {
        this.context = context;
        this.planList = planList;
        this.listener = listener;

        //cheking if there is an ongloing plan
        for (int i = 0; i < planList.size(); i++){
            if (planList.get(i).getExercises().size() > 0){
                for (int j = 0; j < planList.get(i).getExercises().size(); j++){
                    if (planList.get(i).getExercises().get(j).getQuestionExercises().size() > 0){
                        for (int h = 0; h < planList.get(i).getExercises().get(j).getQuestionExercises().size(); h++){
                            if (planList.get(i).getExercises().get(j).getQuestionExercises().get(h).getIsAnswered()){
                                ongoingPlanPosition = i;
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEWS_TYPES.Normal){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.welfare_plan_list_item_layout, parent, false);
            return new AllPlanItemViewHolder(view);
        }else if (viewType == VIEWS_TYPES.Header) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.welfare_plan_list_header_layout, parent, false);
            return new AllPlanHeaderViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        if(viewHolder instanceof AllPlanHeaderViewHolder) {
            /**Nothing**/

        } else if(viewHolder instanceof AllPlanItemViewHolder) {
            final PlanWelfare plan = planList.get(position - 1);
            AllPlanItemViewHolder normalHolder = (AllPlanItemViewHolder) viewHolder;

            boolean isThisPlanOngling = false;
            boolean isThereAnOngoing = false;
            if ((position - 1) == ongoingPlanPosition){
                isThisPlanOngling = true;
            }
            if (ongoingPlanPosition != -1){
                isThereAnOngoing = true;
            }

            normalHolder.bind(plan, isThisPlanOngling, isThereAnOngoing, listener, context);
        }
    }

    @Override
    public int getItemViewType(int position){
        if(isPositionHeader (position)) {
            return VIEWS_TYPES.Header;
        }
        return VIEWS_TYPES.Normal;

    }

    @Override
    public int getItemCount() {
        return planList.size() + 1;
    }


    private boolean isPositionHeader (int position) {
        boolean isHeader = false;
        if (position == 0){
            isHeader = true;
        }
        return isHeader;
    }
}
