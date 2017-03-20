package com.unir.grupo2.myzeancoach.ui.MWelfare;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.domain.MWelfare.AllPlansUseCase;
import com.unir.grupo2.myzeancoach.domain.model.PlanWelfare;
import com.unir.grupo2.myzeancoach.ui.MWelfare.allPlansList.AllPlanListAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;

/**
 * Created by Cesar on 19/03/2017.
 */

public class WelfareAllPlansFragment extends Fragment implements AllPlanListAdapter.OnItemPlanClickListener{

    List<PlanWelfare> planItemList;
    AllPlanListAdapter palnListAdapter;

    @BindView(R.id.plan_list_recycler_view) RecyclerView planListRecyclerView;
    @BindView(R.id.loading_layout) LinearLayout loadingLayout;
    @BindView(R.id.error_layout) LinearLayout errorLayout;

    WelfareAllPlansFragment.OnItemPlanSelectedListener onItemplanSelectedListener;

    public interface OnItemPlanSelectedListener{
        void onItemPlanSelected(PlanWelfare plan);
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if (context instanceof WelfareAllPlansFragment.OnItemPlanSelectedListener){
            onItemplanSelectedListener = (WelfareAllPlansFragment.OnItemPlanSelectedListener) context;
        }else{
            throw new ClassCastException(context.toString() + " must implements WelfareAllPlansFragment.OnItemPlanSelectedListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.welfare_plan_list_layout,null);
        ButterKnife.bind(this, view);

        planListRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        planListRecyclerView.setLayoutManager(linearLayoutManager);

        requestData();

        return view;
    }

    private void requestData(){
        showLoading();
        new AllPlansUseCase().execute(new AllPlansSubscriber());
    }

    private void updateList(List<PlanWelfare> planList){
        planItemList = planList;
        palnListAdapter = new AllPlanListAdapter(getActivity(),planItemList,this);
        planListRecyclerView.setAdapter(palnListAdapter);
    }

    @Override
    public void onItemPlanClick(PlanWelfare plan) {
        onItemplanSelectedListener.onItemPlanSelected(plan);
    }

    /**
     * Method used to show error view
     */
    public void showError() {
        planListRecyclerView.setVisibility(View.GONE);
        loadingLayout.setVisibility(View.GONE);
        errorLayout.setVisibility(View.VISIBLE);
    }

    /**
     * Method used to show the loading view
     */
    public void showLoading() {
        loadingLayout.setVisibility(View.VISIBLE);
        planListRecyclerView.setVisibility(View.GONE);
        errorLayout.setVisibility(View.GONE);
    }

    /**
     * Method used to show the listView
     */
    public void showContent() {
        planListRecyclerView.setVisibility(View.VISIBLE);
        loadingLayout.setVisibility(View.GONE);
        errorLayout.setVisibility(View.GONE);
    }


    private final class AllPlansSubscriber extends Subscriber<List<PlanWelfare>> {
        //3 callbacks

        //Show the listView
        @Override public void onCompleted() {
            showContent();
        }

        //Show the error
        @Override public void onError(Throwable e) {
            showError();
        }

        //Update listview datas
        @Override
        public void onNext(List<PlanWelfare> plansList) {
           updateList(plansList);
        }
    }

}
