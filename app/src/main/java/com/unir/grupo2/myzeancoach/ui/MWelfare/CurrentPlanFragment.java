package com.unir.grupo2.myzeancoach.ui.MWelfare;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.domain.model.ExerciseWelfare;
import com.unir.grupo2.myzeancoach.domain.model.PlanWelfare;
import com.unir.grupo2.myzeancoach.ui.MEssentialInfo.VideosFragment;
import com.unir.grupo2.myzeancoach.ui.MWelfare.planList.ExerciseListAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cesar on 19/03/2017.
 */

public class CurrentPlanFragment extends Fragment implements ExerciseListAdapter.OnItemExerciseClickListener{
    List<ExerciseWelfare> exerciseItemList;
    ExerciseListAdapter exerciseListAdapter;

    @BindView(R.id.exercises_recycler_view) RecyclerView exercisesRecyclerView;
    @BindView(R.id.loading_layout) LinearLayout loadingLayout;
    @BindView(R.id.error_layout) LinearLayout errorLayout;

    CurrentPlanFragment.OnItemExerciseSelectedListener onItemExerciseSelectedListener;

    public interface OnItemExerciseSelectedListener{
        void onItemExerciseSelected(ExerciseWelfare exercise);
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if (context instanceof VideosFragment.OnItemVideoSelectedListener){
            onItemExerciseSelectedListener = (CurrentPlanFragment.OnItemExerciseSelectedListener) context;
        }else{
            throw new ClassCastException(context.toString() + " must implements CurrentPlanFragment.OnItemExerciseSelectedListener");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.welfare_current_plan,null);
        ButterKnife.bind(this, view);

        PlanWelfare plan;
        Bundle bundle = getArguments();
        if (bundle != null) {
            plan = bundle.getParcelable("PLAN");
        }else{
            plan = null;
        }

        updateData(plan.getExercises());

        exercisesRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        exercisesRecyclerView.setLayoutManager(linearLayoutManager);

        showContent();

        return view;
    }

    private void updateData(List<ExerciseWelfare> exercises) {
        showLoading();
        exerciseListAdapter = new ExerciseListAdapter(getActivity(),exercises,this);
        exercisesRecyclerView.setAdapter(exerciseListAdapter);
    }

    /**
     * Method used to show error view
     */
    public void showError() {
        exercisesRecyclerView.setVisibility(View.GONE);
        loadingLayout.setVisibility(View.GONE);
        errorLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onItemExerciseClick(ExerciseWelfare exercise) {
        onItemExerciseSelectedListener.onItemExerciseSelected(exercise);
    }

    /*** Method used to show the loading view
     */
    public void showLoading() {
        loadingLayout.setVisibility(View.VISIBLE);
        exercisesRecyclerView.setVisibility(View.GONE);
        errorLayout.setVisibility(View.GONE);
    }

    /**
     * Method used to show the listView
     */
    public void showContent() {
        exercisesRecyclerView.setVisibility(View.VISIBLE);
        loadingLayout.setVisibility(View.GONE);
        errorLayout.setVisibility(View.GONE);
    }

}
