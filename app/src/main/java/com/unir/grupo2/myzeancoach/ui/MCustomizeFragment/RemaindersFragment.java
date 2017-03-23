package com.unir.grupo2.myzeancoach.ui.MCustomizeFragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.domain.MCustomize.Remainders.Remainders.UpdateListUseCase;
import com.unir.grupo2.myzeancoach.domain.model.RemainderItem;
import com.unir.grupo2.myzeancoach.ui.MCustomizeFragment.remaindersList.RemainderItemObject;
import com.unir.grupo2.myzeancoach.ui.MCustomizeFragment.remaindersList.RemaindersListAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

/**
 * Created by Cesar on 22/02/2017.
 */

public class RemaindersFragment extends Fragment implements RemaindersListAdapter.OnItemClickListener {
    @BindView(R.id.taskList)
    RecyclerView taskList;
    @Nullable
    @BindView(R.id.floating_action_button)
    FloatingActionButton floatingActionButton;

    @OnClick(R.id.floatingActionButton)
    public void openNewRemainder() {
        postListener.onAddRemainderSelected();
    }

    RemaindersListAdapter remaindersListAdapter;
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
        taskList.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        taskList.setLayoutManager(mLayoutManager);

        /*RemainderItemObject p1 = new RemainderItemObject("titulo", "obs", true);
        RemainderItemObject p2 = new RemainderItemObject("titulo", "obs", true);
        RemainderItemObject p3 = new RemainderItemObject("titulo", "obs", true);
        RemainderItemObject p4 = new RemainderItemObject("titulo", "obs", true);
        RemainderItemObject p5 = new RemainderItemObject("titulo", "obs", true);
        remainders = new ArrayList<RemainderItemObject>();
        remainders.add(p1);
        remainders.add(p2);
        remainders.add(p3);
        remainders.add(p4);
        remainders.add(p5);


        // specify an adapter (see also next example)
        remaindersListAdapter = new RemaindersListAdapter(getContext(), remainders, this);
        taskList.setAdapter(remaindersListAdapter);*/
        updatedata();
        return view;
    }

    private void updatedata() {
        showLoading();
        //todo pasarel token sacado de las shared preferences
        new UpdateListUseCase("TOKEN HERE").execute(new RemaindersFragment.RemaindersSubscriber());

    }

    private final class RemaindersSubscriber extends Subscriber<List<RemainderItem>> {
        //3 callbacks

        //Show the listView
        @Override
        public void onCompleted() {

        }

        //Show the error
        @Override
        public void onError(Throwable e) {
            showError();
        }

        //Update listview datas
        @Override
        public void onNext(List<RemainderItem> remainderItems) {
            updateList(remainderItems);
        }
    }

    private void updateList(List<RemainderItem> remainderItemList) {
        remainders.clear();
        this.remainderItemList = remainderItemList;
        if(remainderItemList!=null&& remainderItemList.size()>0){
            for (int i = 0; i< this.remainderItemList.size(); i++){
                RemainderItem remainderItem = remainderItemList.get(i);
                RemainderItemObject remainderItemObject = new RemainderItemObject(remainderItem.getTitle(),remainderItem.getDescription(),true);
                        //todo implementar el recuperar si ese item esta completo o no;
                remainders.add(remainderItemObject);
            }
        }
        showContent();
        remaindersListAdapter = new RemaindersListAdapter(getContext(), this.remainders, this);
        taskList.setAdapter(remaindersListAdapter);
    }

    /**
     * Method used to show error view
     */
    public void showError() {
        /*recyclerView.setVisibility(View.GONE);
        loadingLayout.setVisibility(View.GONE);
        noTestLayout.setVisibility(View.GONE);
        errorLayout.setVisibility(View.VISIBLE);*/
    }

    /**
     * Method used to show the loading view
     */
    public void showLoading() {
        /*loadingLayout.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        errorLayout.setVisibility(View.GONE);
        noTestLayout.setVisibility(View.GONE);*/
    }

    /**
     * Method used to show the listView
     */
    public void showContent() {
        /*recyclerView.setVisibility(View.VISIBLE);
        loadingLayout.setVisibility(View.GONE);
        errorLayout.setVisibility(View.GONE);
        noTestLayout.setVisibility(View.GONE);*/
    }

    @Override
    public void onItemClick(RemainderItemObject remaindersItem) {
        postListener.onItemRemainderSelected(remaindersItem);
    }

    @Override
    public void onCompleteClick(RemainderItemObject remainderItem) {
        postListener.onCompletedRemainderSelected(remainderItem);
    }

    @Override
    public void onAddClick(RemainderItemObject remainderItem) {
        postListener.onAddRemainderSelected();
    }
}
