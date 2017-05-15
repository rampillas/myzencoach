package com.unir.grupo2.myzeancoach.ui.MLeisure;

import android.content.Intent;
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
import android.widget.LinearLayout;

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.domain.MLeisure.AddLikeUseCase;
import com.unir.grupo2.myzeancoach.domain.MLeisure.GetEventsUseCase;
import com.unir.grupo2.myzeancoach.domain.MLeisure.RemoveLikeUseCase;
import com.unir.grupo2.myzeancoach.domain.model.Event;
import com.unir.grupo2.myzeancoach.domain.model.UserLike;
import com.unir.grupo2.myzeancoach.domain.utils.Utils;
import com.unir.grupo2.myzeancoach.ui.MLeisure.postList.EventListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscriber;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Cesar on 22/02/2017.
 */

public class PublicHomepageFragment extends Fragment implements EventListAdapter.OnEventClickListener {

    static final int ADD_EVENT_REQUEST = 5;
    static final int COMMENTS_REQUEST = 6;

    @BindView(R.id.post_recycler_view)
    RecyclerView postListRecyclerView;
    @BindView(R.id.loading_layout)
    LinearLayout loadingLayout;
    @BindView(R.id.error_layout)
    LinearLayout errorLayout;
    @BindView(R.id.no_event_layout)
    LinearLayout noEventLayout;
    @BindView(R.id.floating_action_button)
    FloatingActionButton floatingActionButton;

    List<Event> eventItemList;
    EventListAdapter postListAdapter;

    private int updatePosition;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.public_homepage_layout, null);
        ButterKnife.bind(this, view);

        getEventsData();

        postListRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        postListRecyclerView.setLayoutManager(linearLayoutManager);

        return view;
    }

    @Override
    public void onAddCommentEventClick(Event event) {
        Intent intent = new Intent(getActivity(), CommentActivity.class);
        intent.putExtra("EVENT", event);
        startActivityForResult(intent, COMMENTS_REQUEST);
    }

    @Override
    public void onLikeEventClick(Event event, int position, boolean isAddLike) {
        this.updatePosition = position;
        updateLikeData(isAddLike, event);
    }

    @OnClick(R.id.floating_action_button)
    void addEvent(View view) {
        Intent intent = new Intent(getActivity(), AddPostActivity.class);
        startActivityForResult(intent, ADD_EVENT_REQUEST);
    }

    private void getEventsData() {
        showLoading();

        String userName = Utils.getUserFromPreference(getActivity());
        String token = "Bearer " + Utils.getTokenFromPreference(getActivity());

        new GetEventsUseCase(userName, token).execute(new EventSubscriber());
    }

    private void updateLikeData(boolean isAddLike, Event event){

        showLoading();

        String userName = Utils.getUserFromPreference(getActivity());
        String token = "Bearer " + Utils.getTokenFromPreference(getActivity());

        String text = "{\n" +
                "\t\"user_owner\": \"" + event.getUser() + "\",\n" +
                "\t\"title\": \"" + event.getTitle().replaceAll("\"","\\\\\"") + "\"\n" +
                "}\n";

        RequestBody body;

        if (isAddLike){
            body = RequestBody.create(MediaType.parse("text/plain"), text);
            new AddLikeUseCase(userName, token, body).execute(new AddLikeSubscriber());
        }else{
            body = RequestBody.create(MediaType.parse("text/plain"), text);
            new RemoveLikeUseCase(userName, token, body).execute(new RemoveLikeSubscriber());
        }
    }

    private void loadList(ArrayList<Event> eventList) {
        this.eventItemList = eventList;
        if (eventItemList != null && !eventItemList.isEmpty()) {
            postListAdapter = new EventListAdapter(getContext(), eventItemList, this);
            postListRecyclerView.setAdapter(postListAdapter);
            showContent();
        } else {
            showNoEvent();
        }
    }

    private void uploadListLike(boolean isToAdd, Event event){
        UserLike userLike = new UserLike();
        userLike.setIsLiked(isToAdd);
        userLike.setUser(Utils.getUserFromPreference(getActivity()));
        userLike.setEvent(event.getTitle());
        event.setUserLike(userLike);

        eventItemList.set(updatePosition, event);
        postListAdapter.notifyDataSetChanged();
    }

    private Event addCommentsToEvent(Event event){
        for (int i = 0; i < eventItemList.size(); i++){
           if (event.getTitle().equals(eventItemList.get(i).getTitle())){
               event.setComments(eventItemList.get(i).getComments());
           }
        }
        return event;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_EVENT_REQUEST) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    Event event = data.getParcelableExtra("EVENT_NEW");
                    if (event != null) {
                        if(eventItemList == null || eventItemList.isEmpty()){
                            eventItemList = new ArrayList<>();
                            eventItemList.add(event);
                            postListAdapter = new EventListAdapter(getContext(), eventItemList, this);
                            postListRecyclerView.setAdapter(postListAdapter);
                            showContent();
                        }else{
                            eventItemList.add(event);
                            postListAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        }else if (requestCode == COMMENTS_REQUEST) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    Event event = data.getParcelableExtra("EVENT");
                    if (event != null) {
                        for (int i = 0; i < eventItemList.size(); i++){
                            if (eventItemList.get(i).getTitle().equals(event.getTitle())){
                                eventItemList.set(i, event);
                                postListAdapter.notifyDataSetChanged();
                                break;
                            }
                        }
                    }
                }
            }
        }
    }


    /**
     * Method used to show error view
     */
    public void showError() {
        errorLayout.setVisibility(View.VISIBLE);
        postListRecyclerView.setVisibility(View.GONE);
        loadingLayout.setVisibility(View.GONE);
        noEventLayout.setVisibility(View.GONE);
        floatingActionButton.setVisibility(View.GONE);
    }

    /**
     * Method used to show the loading view
     */
    public void showLoading() {
        loadingLayout.setVisibility(View.VISIBLE);
        postListRecyclerView.setVisibility(View.GONE);
        errorLayout.setVisibility(View.GONE);
        noEventLayout.setVisibility(View.GONE);
        floatingActionButton.setVisibility(View.GONE);
    }

    /**
     * Method used to show the listView
     */
    public void showContent() {
        postListRecyclerView.setVisibility(View.VISIBLE);
        floatingActionButton.setVisibility(View.VISIBLE);
        loadingLayout.setVisibility(View.GONE);
        errorLayout.setVisibility(View.GONE);
        noEventLayout.setVisibility(View.GONE);
    }

    public void showNoEvent() {
        noEventLayout.setVisibility(View.VISIBLE);
        floatingActionButton.setVisibility(View.VISIBLE);
        postListRecyclerView.setVisibility(View.GONE);
        loadingLayout.setVisibility(View.GONE);
        errorLayout.setVisibility(View.GONE);
    }

    private final class EventSubscriber extends Subscriber<ArrayList<Event>> {
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
        public void onNext(ArrayList<Event> eventList) {
            loadList(eventList);
        }
    }

    private final class AddLikeSubscriber extends Subscriber<Event> {
        //3 callbacks

        //Show the listView
        @Override
        public void onCompleted() {
            showContent();
        }

        //Show the error
        @Override
        public void onError(Throwable e) {
            showError();
        }

        //Update listview datas
        @Override
        public void onNext(Event event) {
            uploadListLike(true, addCommentsToEvent(event));
        }
    }

    private final class RemoveLikeSubscriber extends Subscriber<Event> {
        //3 callbacks

        //Show the listView
        @Override
        public void onCompleted() {
            showContent();
        }

        //Show the error
        @Override
        public void onError(Throwable e) {
            showError();
        }

        //Update listview datas
        @Override
        public void onNext(Event event) {
            uploadListLike(false, addCommentsToEvent(event));
        }
    }

}

