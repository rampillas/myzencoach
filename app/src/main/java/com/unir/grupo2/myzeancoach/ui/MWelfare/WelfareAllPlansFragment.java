package com.unir.grupo2.myzeancoach.ui.MWelfare;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.domain.MWelfare.AllPlansUseCase;
import com.unir.grupo2.myzeancoach.domain.model.PlanListWelfarePojo;
import com.unir.grupo2.myzeancoach.domain.model.PlanWelfare;
import com.unir.grupo2.myzeancoach.domain.utils.Constants;
import com.unir.grupo2.myzeancoach.domain.utils.Utils;
import com.unir.grupo2.myzeancoach.ui.MWelfare.allPlansList.AllPlanListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;

import static com.unir.grupo2.myzeancoach.domain.utils.Constants.BASE_URL_WELFARE_ALL_PLANS;

/**
 * Created by Cesar on 19/03/2017.
 */

public class WelfareAllPlansFragment extends Fragment implements AllPlanListAdapter.OnItemPlanClickListener {

    @BindView(R.id.plan_list_recycler_view)
    RecyclerView planListRecyclerView;
    @BindView(R.id.content_layout)
    RelativeLayout contentRelativeLayout;
    @BindView(R.id.loading_layout)
    LinearLayout loadingLayout;
    @BindView(R.id.no_plan_layout)
    LinearLayout noPlanLayout;
    @BindView(R.id.loadItemsLayout_recyclerView)
    RelativeLayout loadingDataRelativeLayout;

    List<PlanWelfare> planItemList;
    AllPlanListAdapter palnListAdapter;
    private LinearLayoutManager layoutManager;

    // Variables for scroll listener
    private boolean userScrolled = true;
    private int pastVisiblesItems, visibleItemCount, totalItemCount;
    private String nextData = null;

    WelfareAllPlansFragment.OnItemPlanSelectedListener onItemplanSelectedListener;

    public interface OnItemPlanSelectedListener {
        void onItemPlanSelected(PlanWelfare plan);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof WelfareAllPlansFragment.OnItemPlanSelectedListener) {
            onItemplanSelectedListener = (WelfareAllPlansFragment.OnItemPlanSelectedListener) context;
        } else {
            throw new ClassCastException(context.toString() + " must implements WelfareAllPlansFragment.OnItemPlanSelectedListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.welfare_plan_list_layout, null);
        ButterKnife.bind(this, view);

        planListRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        planListRecyclerView.setLayoutManager(layoutManager);

        requestData(BASE_URL_WELFARE_ALL_PLANS, true);

        return view;
    }

    private void requestData(String url, boolean isFirstTime) {
        if (isFirstTime) {
            showLoading();
        } else {
            loadingDataRelativeLayout.setVisibility(View.VISIBLE);
        }

        new AllPlansUseCase(url, Constants.PRE_TOKEN + Utils.getTokenFromPreference(getActivity())).execute(new AllPlansSubscriber());
    }

    private void updateList(PlanListWelfarePojo planListPojo) {

        if (planListPojo == null || planListPojo.getCount() <= 0) {
            showNoPlan();
        } else {

            nextData = planListPojo.getNext();

            List<PlanWelfare> planList = planListPojo.getPlans();

            //Check if there some plan has already been finished
            List<PlanWelfare> planListNoFinished = new ArrayList<>();
            for (int i = 0; i < planList.size(); i++) {
                if (!planList.get(i).getIsFinished()) {
                    planListNoFinished.add(planList.get(i));
                }
            }

            //First time data are requested
            if (planItemList == null) {

                planItemList = planListNoFinished;

                if (!planItemList.isEmpty()) {
                    palnListAdapter = new AllPlanListAdapter(getActivity(), planItemList, this);
                    planListRecyclerView.setAdapter(palnListAdapter);
                    implementScrollListener();
                    showContent();
                } else {
                    showNoPlan();
                }
                //No first time
            } else {
                for (PlanWelfare plan : planListNoFinished) {
                    planItemList.add(plan);
                }
                loadingDataRelativeLayout.setVisibility(View.GONE);
                palnListAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onItemPlanClick(PlanWelfare plan) {
        //Saving the plan user chose
        Gson gson = new Gson();
        String jsonPlan = gson.toJson(plan);

        SharedPreferences sharedPref = getActivity().getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.PREFERENCES_CURRENT_PLAN_WELFARE), jsonPlan);
        editor.commit();

        Toast.makeText(getContext(), getString(R.string.alert_plan_chosen), Toast.LENGTH_LONG).show();

        onItemplanSelectedListener.onItemPlanSelected(plan);
    }

    //Pagination
    // Implement scroll listener
    private void implementScrollListener() {
        planListRecyclerView
                .addOnScrollListener(new RecyclerView.OnScrollListener() {

                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView,
                                                     int newState) {

                        super.onScrollStateChanged(recyclerView, newState);

                        // If scroll state is touch scroll then set userScrolled
                        // true
                        if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                            userScrolled = true;

                        }

                    }

                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx,
                                           int dy) {

                        super.onScrolled(recyclerView, dx, dy);
                        // Here get the child count, item count and visibleitems
                        // from layout manager

                        visibleItemCount = layoutManager.getChildCount();
                        totalItemCount = layoutManager.getItemCount();
                        pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();

                        // Now check if userScrolled is true and also check if
                        // the item is end then update recycler view and set
                        // userScrolled to false
                        if (userScrolled
                                && (visibleItemCount + pastVisiblesItems) == totalItemCount) {
                            userScrolled = false;

                            if (nextData != null) {
                                requestData(nextData, false);
                            }
                        }
                    }
                });
    }

    /**
     * Method used to show error view
     */
    public void showNoPlan() {
        noPlanLayout.setVisibility(View.VISIBLE);
        contentRelativeLayout.setVisibility(View.GONE);
        loadingLayout.setVisibility(View.GONE);
    }

    /**
     * Method used to show the loading view
     */
    public void showLoading() {
        loadingLayout.setVisibility(View.VISIBLE);
        contentRelativeLayout.setVisibility(View.GONE);
        noPlanLayout.setVisibility(View.GONE);
    }

    /**
     * Method used to show the listView
     */
    public void showContent() {
        contentRelativeLayout.setVisibility(View.VISIBLE);
        loadingLayout.setVisibility(View.GONE);
        noPlanLayout.setVisibility(View.GONE);
    }


    private final class AllPlansSubscriber extends Subscriber<PlanListWelfarePojo> {
        //3 callbacks

        //Show the listView
        @Override
        public void onCompleted() {

        }

        //Show the error
        @Override
        public void onError(Throwable e) {
            showNoPlan();
        }

        //Update listview datas
        @Override
        public void onNext(PlanListWelfarePojo plansListPojo) {
            updateList(plansListPojo);
        }
    }

}
