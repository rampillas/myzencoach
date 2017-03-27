package com.unir.grupo2.myzeancoach.ui.MCustomizeFragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.domain.MCustomize.Remainders.Remainders.GetRewardsUseCase;
import com.unir.grupo2.myzeancoach.domain.MCustomize.Remainders.Remainders.SetRemainderFinishedUseCase;
import com.unir.grupo2.myzeancoach.domain.MCustomize.Remainders.Remainders.SetRewardsUseCase;
import com.unir.grupo2.myzeancoach.domain.MCustomize.Remainders.Remainders.UpdateListUseCase;
import com.unir.grupo2.myzeancoach.domain.model.RemainderItem;
import com.unir.grupo2.myzeancoach.domain.model.ResultsItem;
import com.unir.grupo2.myzeancoach.ui.MCustomizeFragment.remaindersList.RemainderItemObject;
import com.unir.grupo2.myzeancoach.ui.MCustomizeFragment.remaindersList.RemaindersListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscriber;

/**
 * Created by Cesar on 22/02/2017.
 */

public class RemaindersFragment extends Fragment implements RemaindersListAdapter.OnItemClickListener {
    @BindView(R.id.taskList)
    RecyclerView recyclerView;
    @Nullable
    @BindView(R.id.floating_action_button)
    FloatingActionButton floatingActionButton;
    @Nullable
    @BindView(R.id.ratingBar)
    RatingBar ratingBar;
    @Nullable
    @BindView(R.id.loading_layout)
    LinearLayout loadingLayout;
    @Nullable
    @BindView(R.id.error_layout)
    LinearLayout errorLayout;

    @OnClick(R.id.floatingActionButton)
    public void openNewRemainder() {
        postListener.onAddRemainderSelected();
    }

    RemaindersListAdapter remaindersListAdapter;
    static String tokenActivo = "";
    static RemainderItemObject remainderItemInUse = null;
    //elements from database
    List<RemainderItem> remainderItemList;
    //local elements for view holder
    private List<RemainderItemObject> remainders;

    RemaindersFragment.OnPostListener postListener;

    public interface OnPostListener {
        void onItemRemainderSelected(RemainderItemObject remainderItem);

        void onAddRemainderSelected();

        void onCompletedRemainderSelected(RemainderItemObject remainderItem);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof RemaindersFragment.OnPostListener) {
            postListener = (RemaindersFragment.OnPostListener) context;
        } else {
            throw new ClassCastException(context.toString() + " must implements RemainderFragment.OnPostListener");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.remainders_layout, null);
        ButterKnife.bind(this, view);
        updatedata();
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        return view;
    }

    private void setrewards() {
        showLoading();
        Context context = getActivity();
        SharedPreferences sharedPref = context.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String token = sharedPref.getString(getString(R.string.PREFERENCES_TOKEN), null);
        Log.d("Access TOKEN: ", token);
        new GetRewardsUseCase("Bearer " + token).execute(new RemaindersFragment.AwardsSuscriber());
    }

    private void updatedata() {
        showLoading();
        Context context = getActivity();
        SharedPreferences sharedPref = context.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String token = sharedPref.getString(getString(R.string.PREFERENCES_TOKEN), null);
        Log.d("Access TOKEN: ", token);
        new UpdateListUseCase("Bearer " + token).execute(new RemaindersFragment.RemaindersSubscriber());

    }

    private final class RemaindersSubscriber extends Subscriber<List<RemainderItem>> {
        //3 callbacks
        //Show the listView
        @Override
        public void onCompleted() {
            setrewards();
        }

        //Show the error
        @Override
        public void onError(Throwable e) {
            Log.e("ERROR REMINDERS ", e.toString());
            showError();
        }

        //Update listview datas
        @Override
        public void onNext(List<RemainderItem> remainderItems) {
            updateList(remainderItems);
        }
    }

    private final class AwardsSuscriber extends Subscriber<List<ResultsItem>> {
        //3 callbacks

        //Show the listView
        @Override
        public void onCompleted() {
        }

        //Show the error
        @Override
        public void onError(Throwable e) {
            Log.e("ERROR Awards ", e.toString());

            showError();
        }

        //Update listview datas
        @Override
        public void onNext(List<ResultsItem> rewardsItems) {
            setrewardspoints(rewardsItems);
        }
    }

    public static final class FinishedSuscriber extends Subscriber<Void> {
        //3 callbacks

        @Override
        public void onCompleted() {
            setUserPoints();
        }

        //Show the error
        @Override
        public void onError(Throwable e) {
            Log.e("ERROR mark as complete ", e.toString());
        }

        @Override
        public void onNext(Void e) {
        }
    }

    public static final class SetPointsSuscriber extends Subscriber<Void> {
        //3 callbacks

        @Override
        public void onCompleted() {
        }

        //Show the error
        @Override
        public void onError(Throwable e) {
            Log.e("ERROR mark as complete ", e.toString());
        }

        @Override
        public void onNext(Void e) {
        }
    }

    private void updateList(List<RemainderItem> remainderItemList) {
        Log.d("updateList", "updateList1");
        this.remainderItemList = remainderItemList;

        remainders = new ArrayList<RemainderItemObject>();
        if (remainderItemList != null && remainderItemList.size() > 0) {
            for (int i = 0; i < this.remainderItemList.size(); i++) {
                RemainderItem remainderItem = remainderItemList.get(i);
                Log.w("IS_Finished: ", String.valueOf(remainderItem.isFinished()));
                RemainderItemObject remainderItemObject = new RemainderItemObject(remainderItem.getTitle(), remainderItem.getDescription(), remainderItem.isFinished(), remainderItem.getUser());
                remainders.add(remainderItemObject);
            }
        }
        // use a linear layout manager
        remaindersListAdapter = new RemaindersListAdapter(getContext(), remainders, this);
        recyclerView.setAdapter(remaindersListAdapter);
    }

    private void setrewardspoints(List<ResultsItem> rewardsItemList) {
        int totalPoints = 0;
        for (int i = 0; i < rewardsItemList.size(); i++) {
            totalPoints += Integer.valueOf(rewardsItemList.get(i).getPoints());
        }
        Log.d("PUNTOS", String.valueOf(totalPoints));
        ratingBar.setRating(totalPoints);
        showContent();
    }

    private static void setUserPoints() {
        String bodyString = "{\n" +
                "\t\"title\": \"" + remainderItemInUse.getTitle() + "\",\n" +
                "\t\"points\": 1 \n" +
                "}";
        RequestBody rb = RequestBody.create(MediaType.parse("text/plain"), bodyString);
        new SetRewardsUseCase("Bearer " + tokenActivo, rb).execute(new RemaindersFragment.SetPointsSuscriber());
    }

    /**
     * Method used to show error view
     */
    public void showError() {
        recyclerView.setVisibility(View.GONE);
        loadingLayout.setVisibility(View.GONE);
        errorLayout.setVisibility(View.VISIBLE);
    }

    /**
     * Method used to show the loading view
     */
    public void showLoading() {
        loadingLayout.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        errorLayout.setVisibility(View.GONE);
    }

    /**
     * Method used to show the listView
     */
    public void showContent() {
        recyclerView.setVisibility(View.VISIBLE);
        loadingLayout.setVisibility(View.GONE);
        errorLayout.setVisibility(View.GONE);
    }

    @Override
    public void onItemClick(RemainderItemObject remaindersItem) {
        postListener.onItemRemainderSelected(remaindersItem);
    }

    @Override
    public void onCompleteClick(RemainderItemObject remainderItem) {
        showLoading();
        String bodyString = "{\n" +
                "\t\"title\": \"" + remainderItem.getTitle() + "\",\n" +
                "\t\"is_finished\": true \n" +
                "}";
        RequestBody rb = RequestBody.create(MediaType.parse("text/plain"), bodyString);
        SharedPreferences sharedPref = getContext().getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String token = sharedPref.getString(getString(R.string.PREFERENCES_TOKEN), null);
        String user = sharedPref.getString(getString(R.string.PREFERENCES_USER), null);
        tokenActivo = token;
        remainderItemInUse = remainderItem;
        new SetRemainderFinishedUseCase(user, "Bearer " + token, rb).execute(new RemaindersFragment.FinishedSuscriber());
        postListener.onCompletedRemainderSelected(remainderItem);
    }

    @Override
    public void onAddClick(RemainderItemObject remainderItem) {
        postListener.onAddRemainderSelected();
    }
}
