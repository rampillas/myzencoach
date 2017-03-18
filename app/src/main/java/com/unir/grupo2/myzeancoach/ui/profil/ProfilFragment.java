package com.unir.grupo2.myzeancoach.ui.profil;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.domain.Profil.EmoticonUseCase;
import com.unir.grupo2.myzeancoach.domain.Profil.UpdateEmoticonUseCase;
import com.unir.grupo2.myzeancoach.domain.model.Emoticon;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

/**
 * Created by Cesar on 18/03/2017.
 */

public class ProfilFragment extends Fragment implements EmoticonsListAdapter.OnItemEmoticonClickListener {

    @BindView(R.id.emoticons_recycler_view)
    RecyclerView emoticonsListRecyclerView;
    @BindView(R.id.loading_layout)
    LinearLayout loadingLayout;
    @BindView(R.id.error_layout)
    LinearLayout errorLayout;
    @BindView(R.id.emoticon_imageView)
    ImageView emoticonImageView;
    @BindView(R.id.language_radioGroup)
    RadioGroup languageRadioGroup;
    @BindView(R.id.save_button)
    Button saveButton;
    @BindView(R.id.content_layout)
    RelativeLayout contentLayout;

    private List<String> emoticonItemList;
    private EmoticonsListAdapter emoticonListAdapter;

    private String languageSelected;
    private String emoticonSelected;
    private Emoticon emoticon;

    final static String VERY_HAPPY = "very_happy";
    final static String HAPPY = "happy";
    final static String IN_LOVE = "in_love";
    final static String LAUGHING = "laughing";
    final static String SAT = "sat";
    final static String DISAPPOINTED = "disappointed";
    final static String ANGRY = "angry";
    final static String CRYING = "crying";

    private final static String SPANISH = "spanish";
    private final static String ENGLISH = "english";
    private final static String ITALIAN = "italian";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profil_layout, null);
        ButterKnife.bind(this, view);

        getEmoticon();

        emoticonsListRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

        emoticonsListRecyclerView.setLayoutManager(layoutManager);

        emoticonItemList = new ArrayList<String>() {{
            add(VERY_HAPPY);
            add(HAPPY);
            add(IN_LOVE);
            add(LAUGHING);
            add(SAT);
            add(DISAPPOINTED);
            add(ANGRY);
            add(CRYING);
        }};

        emoticonListAdapter = new EmoticonsListAdapter(getContext(), emoticonItemList, this);
        emoticonsListRecyclerView.setAdapter(emoticonListAdapter);

        languageRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if (checkedId == R.id.spanish_radio) {
                    languageSelected = SPANISH;
                } else if (checkedId == R.id.english_radio) {
                    languageSelected = ENGLISH;
                } else if (checkedId == R.id.italian_radio) {
                    languageSelected = ITALIAN;
                }
            }
        });

        return view;
    }

    @OnClick(R.id.save_button)
    public void submit(View view) {
        boolean isUpdateData = false;
        if (emoticonSelected != null){
            if (emoticon != null){
                if (!emoticon.getName().equals(emoticonSelected)){
                    isUpdateData = true;
                }
            }else{
                isUpdateData = true;
            }
        }
        if (isUpdateData){
            saveEmoticon();
        }

        //********UDPDATE LANGUAGE ******
    }

    private void getEmoticon() {
        showLoading();
        new EmoticonUseCase().execute(new EmoticonSubscriber());
    }

    private void saveEmoticon(){
        showLoading();
        new UpdateEmoticonUseCase().execute(new UpdateEmoticonSubscriber());
    }

    private void showEmoticon(Emoticon emoticon) {
        this.emoticon = emoticon;
        if (emoticon == null) {
            emoticonImageView.setVisibility(View.GONE);
        } else {
            setEmoticonImage(emoticon.getName());
            emoticonSelected = emoticon.getName();
        }
    }

    @Override
    public void onItemEmoticonClick(String emoticonName) {
        emoticonSelected = emoticonName;
        emoticonImageView.setVisibility(View.VISIBLE);
        setEmoticonImage(emoticonName);
    }

    private void setEmoticonImage(String emoticonName){
        emoticonSelected = emoticonName;
        switch (emoticonName) {
            case "very_happy":
                emoticonImageView.setImageResource(R.mipmap.ic_very_happy);
                break;
            case "happy":
                emoticonImageView.setImageResource(R.mipmap.ic_happy);
                break;
            case "in_love":
                emoticonImageView.setImageResource(R.mipmap.ic_in_love);
                break;
            case "laughing":
                emoticonImageView.setImageResource(R.mipmap.ic_laughing);
                break;
            case "sat":
                emoticonImageView.setImageResource(R.mipmap.ic_sat);
                break;
            case "disappointed":
                emoticonImageView.setImageResource(R.mipmap.ic_disappointed);
                break;
            case "angry":
                emoticonImageView.setImageResource(R.mipmap.ic_crying);
                break;
            case "crying":
                emoticonImageView.setImageResource(R.mipmap.ic_angry);
                break;
            default:
                emoticonImageView.setImageResource(R.mipmap.ic_happy);
                break;
        }
    }

    /**
     * Method used to show error view
     */
    public void showError() {
        contentLayout.setVisibility(View.GONE);
        loadingLayout.setVisibility(View.GONE);
        errorLayout.setVisibility(View.VISIBLE);
    }

    /**
     * Method used to show the loading view
     */
    public void showLoading() {
        loadingLayout.setVisibility(View.VISIBLE);
        contentLayout.setVisibility(View.GONE);
        errorLayout.setVisibility(View.GONE);
    }

    /**
     * Method used to show the listView
     */
    public void showContent() {
        contentLayout.setVisibility(View.VISIBLE);
        loadingLayout.setVisibility(View.GONE);
        errorLayout.setVisibility(View.GONE);
    }

    private final class EmoticonSubscriber extends Subscriber<Emoticon> {
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
        public void onNext(Emoticon emoticon) {
            showEmoticon(emoticon);
        }
    }

    private final class UpdateEmoticonSubscriber extends Subscriber<Emoticon> {
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
        public void onNext(Emoticon emoticon) {
            Toast.makeText(getContext(), "data saved", Toast.LENGTH_LONG).show();
        }
    }
}
