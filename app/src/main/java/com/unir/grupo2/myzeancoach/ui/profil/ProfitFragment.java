package com.unir.grupo2.myzeancoach.ui.profil;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.unir.grupo2.myzeancoach.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cesar on 18/03/2017.
 */

public class ProfitFragment extends Fragment implements EmoticonsListAdapter.OnItemEmoticonClickListener{

    @BindView(R.id.emoticon_imageView) ImageView emoticonImageView;

    private List<String> emoticonItemList;
    private EmoticonsListAdapter emoticonListAdapter;

    @BindView(R.id.emoticons_recycler_view) RecyclerView emoticonsListRecyclerView;
    @BindView(R.id.loading_layout) LinearLayout loadingLayout;
    @BindView(R.id.error_layout) LinearLayout errorLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profil_layout,null);
        ButterKnife.bind(this, view);

        emoticonsListRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

        emoticonsListRecyclerView.setLayoutManager(layoutManager);

        emoticonItemList = new ArrayList<String>(){{
            add(getString(R.string.emoticon_very_happy));
            add(getString(R.string.emoticon_happy));
            add(getString(R.string.emoticon_in_love));
            add(getString(R.string.emoticon_laughing));
            add(getString(R.string.emoticon_sat));
            add(getString(R.string.emoticon_disappointed));
            add(getString(R.string.emoticon_crying));
            add(getString(R.string.emoticon_angry));
        }};

        showContent();

        emoticonListAdapter = new EmoticonsListAdapter(getContext(),emoticonItemList, this);
        emoticonsListRecyclerView.setAdapter(emoticonListAdapter);

        return view;
    }

    /**
     * Method used to show error view
     */
    public void showError() {
        emoticonsListRecyclerView.setVisibility(View.GONE);
        loadingLayout.setVisibility(View.GONE);
        errorLayout.setVisibility(View.VISIBLE);
    }

    /**
     * Method used to show the loading view
     */
    public void showLoading() {
        loadingLayout.setVisibility(View.VISIBLE);
        emoticonsListRecyclerView.setVisibility(View.GONE);
        errorLayout.setVisibility(View.GONE);
    }

    /**
     * Method used to show the listView
     */
    public void showContent() {
        emoticonsListRecyclerView.setVisibility(View.VISIBLE);
        loadingLayout.setVisibility(View.GONE);
        errorLayout.setVisibility(View.GONE);
    }


    @Override
    public void onItemEmoticonClick(int position) {
        switch (position) {
            case 0:
                emoticonImageView.setImageResource(R.mipmap.ic_very_happy);
                break;
            case 1:
                emoticonImageView.setImageResource(R.mipmap.ic_happy);
                break;
            case 2:
                emoticonImageView.setImageResource(R.mipmap.ic_in_love);
                break;
            case 3:
                emoticonImageView.setImageResource(R.mipmap.ic_laughing);
                break;
            case 4:
                emoticonImageView.setImageResource(R.mipmap.ic_sat);
                break;
            case 5:
                emoticonImageView.setImageResource(R.mipmap.ic_disappointed);
                break;
            case 6:
                emoticonImageView.setImageResource(R.mipmap.ic_crying);
                break;
            case 7:
                emoticonImageView.setImageResource(R.mipmap.ic_angry);
                break;
            default:
                emoticonImageView.setImageResource(R.mipmap.ic_happy);
                break;
        }
    }
}
