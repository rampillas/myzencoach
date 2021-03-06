package com.unir.grupo2.myzeancoach.ui.MCustomize;

import android.content.Context;
import android.content.Intent;
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
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.domain.MCustomize.Remainders.Remainders.GetRewardsUseCase;
import com.unir.grupo2.myzeancoach.domain.MCustomize.Remainders.Remainders.SetRemainderFinishedUseCase;
import com.unir.grupo2.myzeancoach.domain.MCustomize.Remainders.Remainders.SetRewardsUseCase;
import com.unir.grupo2.myzeancoach.domain.MCustomize.Remainders.Remainders.UpdateListUseCase;
import com.unir.grupo2.myzeancoach.domain.model.RemainderItem;
import com.unir.grupo2.myzeancoach.domain.model.RemaindersListPojo;
import com.unir.grupo2.myzeancoach.domain.model.RewardsItem;
import com.unir.grupo2.myzeancoach.domain.utils.Utils;
import com.unir.grupo2.myzeancoach.ui.MCustomize.remaindersList.RemainderItemObject;
import com.unir.grupo2.myzeancoach.ui.MCustomize.remaindersList.RemaindersListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscriber;

import static com.unir.grupo2.myzeancoach.domain.utils.Constants.BASE_URL_CUSTOMIZE_REMAINDERS;
import static com.unir.grupo2.myzeancoach.domain.utils.Constants.URL_SERVER;

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
    ProgressBar progressBar;
    @Nullable
    @BindView(R.id.remainTaskNextLevel)
    TextView remainTaskNextLevel;
    @BindView(R.id.loading_layout)
    LinearLayout loadingLayout;
    @BindView(R.id.error_layout)
    LinearLayout errorLayout;
    @BindView(R.id.noplan)
    LinearLayout noPlan;

    // Variables for scroll listener
    private boolean userScrolled = true;
    private int pastVisiblesItems, visibleItemCount, totalItemCount;
    private String nextData = null;
    RemaindersListAdapter remaindersListAdapter;
    static String tokenActivo = "";
    static RemainderItemObject remainderItemInUse = null;
    //elements from database
    RemaindersListPojo remainderItemList = null;
    //local elements for view holder
    private List<RemainderItemObject> remainders;
    List<RemainderItem> remainderItemsList = null;
    LinearLayoutManager layoutManager;
    RemaindersFragment.OnPostListener postListener;
    Boolean isContentMoreThanOneItem = false;

    @OnClick(R.id.floatingActionButton)
    public void openNewRemainder() {
        Intent intent = new Intent(getActivity(), AddRemainderActivity.class);
        startActivity(intent);
        //postListener.onAddRemainderSelected();
    }

    public interface OnPostListener {
        void onItemRemainderSelected(RemainderItemObject remainderItem);

        void onAddRemainderSelected();

        void onCompletedRemainderSelected(RemainderItemObject remainderItem);

        void onAddObservations(RemainderItemObject remainderItem);

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
        updatedata(URL_SERVER + BASE_URL_CUSTOMIZE_REMAINDERS, true);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        return view;
    }

    private void setrewards() {
        showLoading();
        String token = Utils.getTokenFromPreference(getContext());
        new GetRewardsUseCase("Bearer " + token).execute(new RemaindersFragment.AwardsSuscriber());
    }

    private void updatedata(String url, boolean isFirstTime) {
        showLoading();
        String token = Utils.getTokenFromPreference(getContext());
        new UpdateListUseCase(url, "Bearer " + token).execute(new RemaindersFragment.RemaindersSubscriber());

    }

    private final class RemaindersSubscriber extends Subscriber<RemaindersListPojo> {
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
        public void onNext(RemaindersListPojo remainderItems) {
            updateList(remainderItems);
        }
    }

    private final class AwardsSuscriber extends Subscriber<List<RewardsItem>> {
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
        public void onNext(List<RewardsItem> rewardsItems) {
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

    private void updateList(RemaindersListPojo remainderItemList) {
        Log.d("updateList", "updateList1");
        this.remainderItemList = remainderItemList;


        // use a linear layout manager

        if (remainderItemList == null || remainderItemList.getCount() <= 0) {

            showNoPlan();

        } else {
            isContentMoreThanOneItem = true;
            nextData = remainderItemList.getNext();

            List<RemainderItem> remainderItems = remainderItemList.getRemainderItems();
            Log.d("NUMBER REMAINDERS: ", String.valueOf(remainderItems.size()));
            //Check if there some plan has already been finished
            List<RemainderItem> listNoFinished = new ArrayList<>();
            for (int i = 0; i < remainderItems.size(); i++) {
                //se añaden todos
                if (true) {
                    listNoFinished.add(remainderItems.get(i));
                }
            }
            //First time data are requested
            if (remainderItemsList == null) {

                remainderItemsList = listNoFinished;

                if (!remainderItemsList.isEmpty()) {
                    remainders = new ArrayList<RemainderItemObject>();
                    for (RemainderItem item : remainderItemsList) {
                        RemainderItemObject rio = new RemainderItemObject(item.getTitle(), item.getDescription(), item.isFinished(), Utils.getUserFromPreference(getContext()), item.isObservationsEnabled());
                        remainders.add(rio);
                    }
                    remaindersListAdapter = new RemaindersListAdapter(getContext(), remainders, this);
                    recyclerView.setAdapter(remaindersListAdapter);
                    implementScrollListener();
                    showContent();
                    isContentMoreThanOneItem = true;
                } else {
                    isContentMoreThanOneItem = false;
                    showNoPlan();
                }
                //No first time
            } else {
                for (RemainderItem item : listNoFinished) {
                    remainderItemsList.add(item);
                    RemainderItemObject rio = new RemainderItemObject(item.getTitle(), item.getDescription(), item.isFinished(), Utils.getUserFromPreference(getContext()), item.isObservationsEnabled());
                    remainders.add(rio);
                }
                remaindersListAdapter.notifyDataSetChanged();
            }
        }
    }

    private void implementScrollListener() {
        recyclerView
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
                                updatedata(nextData, true);
                            }
                        }
                    }

                });
    }


    private void setrewardspoints(List<RewardsItem> rewardsItemList) {
        int totalPoints = 0;
        for (int i = 0; i < rewardsItemList.size(); i++) {
            totalPoints += Integer.valueOf(rewardsItemList.get(i).getPoints());
        }
        int level = (totalPoints / 10) + 1;
        int progressPoints = totalPoints % 10;
        progressBar.setProgress(progressPoints * 10);
        String texto = remainTaskNextLevel.getText().toString();
        remainTaskNextLevel.setText(getResources().getString(R.string.REMAINDERS_LEVEL) + " " + String.valueOf(level + "\n " + (10 - progressPoints)) + " " + texto);
        if (isContentMoreThanOneItem) {
            showContent();
        } else {
            showNoPlan();
        }

    }

    private static void setUserPoints() {
        String bodyString = "{\n" +
                "\t\"title\": \"" + remainderItemInUse.getTitle().replaceAll("\"","\\\\\"") + "\",\n" +
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
        noPlan.setVisibility(View.GONE);
    }

    /**
     * Method used to show the loading view
     */
    public void showLoading() {
        loadingLayout.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        errorLayout.setVisibility(View.GONE);
        noPlan.setVisibility(View.GONE);
    }

    /**
     * Method used to show the listView
     */
    public void showContent() {
        recyclerView.setVisibility(View.VISIBLE);
        loadingLayout.setVisibility(View.GONE);
        errorLayout.setVisibility(View.GONE);
        noPlan.setVisibility(View.GONE);
    }

    /**
     * Method used to show error view
     */
    public void showNoPlan() {
        noPlan.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        errorLayout.setVisibility(View.GONE);
        loadingLayout.setVisibility(View.GONE);
    }

    @Override
    public void onItemClick(RemainderItemObject remaindersItem) {
        postListener.onItemRemainderSelected(remaindersItem);
    }

    @Override
    public void onCompleteClick(RemainderItemObject remainderItem) {
        showLoading();
        String bodyString = "{\n" +
                "\t\"title\": \"" + remainderItem.getTitle().replaceAll("\"","\\\\\"") + "\",\n" +
                "\t\"is_finished\": true \n" +
                "}";
        RequestBody rb = RequestBody.create(MediaType.parse("text/plain"), bodyString);
        String token = Utils.getTokenFromPreference(getContext());
        String user = Utils.getUserFromPreference(getContext());
        tokenActivo = token;
        remainderItemInUse = remainderItem;
        new SetRemainderFinishedUseCase(user, "Bearer " + token, rb).execute(new RemaindersFragment.FinishedSuscriber());
        postListener.onCompletedRemainderSelected(remainderItem);
    }

    @Override
    public void onAddClick(RemainderItemObject remainderItem) {
        postListener.onAddRemainderSelected();
    }

    @Override
    public void onAddObservations(RemainderItemObject remainderItem) {
        postListener.onAddObservations(remainderItem);
    }
}
