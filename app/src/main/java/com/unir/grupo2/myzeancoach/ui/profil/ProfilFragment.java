package com.unir.grupo2.myzeancoach.ui.profil;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.domain.Profil.EmoticonUseCase;
import com.unir.grupo2.myzeancoach.domain.Profil.UpdateEmoticonUseCase;
import com.unir.grupo2.myzeancoach.domain.model.Emoticon;
import com.unir.grupo2.myzeancoach.domain.utils.Constants;
import com.unir.grupo2.myzeancoach.domain.utils.Utils;
import com.unir.grupo2.myzeancoach.ui.MainActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscriber;

import static com.unir.grupo2.myzeancoach.domain.utils.Constants.ANGRY;
import static com.unir.grupo2.myzeancoach.domain.utils.Constants.CRYING;
import static com.unir.grupo2.myzeancoach.domain.utils.Constants.DISAPPOINTED;
import static com.unir.grupo2.myzeancoach.domain.utils.Constants.ENGLISH;
import static com.unir.grupo2.myzeancoach.domain.utils.Constants.HAPPY;
import static com.unir.grupo2.myzeancoach.domain.utils.Constants.IN_LOVE;
import static com.unir.grupo2.myzeancoach.domain.utils.Constants.ITALIAN;
import static com.unir.grupo2.myzeancoach.domain.utils.Constants.LAUGHING;
import static com.unir.grupo2.myzeancoach.domain.utils.Constants.SAT;
import static com.unir.grupo2.myzeancoach.domain.utils.Constants.SPANISH;
import static com.unir.grupo2.myzeancoach.domain.utils.Constants.VERY_HAPPY;

/**
 * Created by Cesar on 18/03/2017.
 */

public class ProfilFragment extends Fragment implements EmoticonsListAdapter.OnItemEmoticonClickListener {

    @BindView(R.id.emoticons_recycler_view)
    RecyclerView emoticonsListRecyclerView;
    @BindView(R.id.loading_layout) LinearLayout loadingLayout;
    @BindView(R.id.emoticon_layout) LinearLayout emoticonLayout;
    @BindView(R.id.emoticon_imageView) ImageView emoticonImageView;
    @BindView(R.id.language_radioGroup) RadioGroup languageRadioGroup;
    @BindView(R.id.save_button) Button saveButton;
    @BindView(R.id.content_layout) RelativeLayout contentLayout;
    @BindView(R.id.spanish_radio) RadioButton spanishRadioButton;
    @BindView(R.id.english_radio) RadioButton englishRadioButton;
    @BindView(R.id.italian_radio) RadioButton italianRadioButton;

    private List<String> emoticonItemList;
    private EmoticonsListAdapter emoticonListAdapter;

    private String languagePreference;
    private String languageSelected = "";
    private String emoticonSelected;
    private Emoticon emoticon;
    private String emoticonSharedPre;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profil_layout, null);
        ButterKnife.bind(this, view);

        getEmoticon();

        languagePreference = Utils.getLanguageFromPreference(getActivity());

        emoticonsListRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

        emoticonsListRecyclerView.setLayoutManager(layoutManager);

        emoticonItemList = new ArrayList<String>() {{
            add(Constants.VERY_HAPPY);
            add(Constants.HAPPY);
            add(Constants.IN_LOVE);
            add(Constants.LAUGHING);
            add(Constants.SAT);
            add(Constants.DISAPPOINTED);
            add(Constants.ANGRY);
            add(Constants.CRYING);
        }};

        emoticonListAdapter = new EmoticonsListAdapter(getContext(), emoticonItemList, this);
        emoticonsListRecyclerView.setAdapter(emoticonListAdapter);

        if (languagePreference != null && !languagePreference.equals("")){
            switch (languagePreference){
                case Constants.ENGLISH:
                    languageSelected = Constants.ENGLISH;
                    englishRadioButton.setChecked(true);
                    break;
                case Constants.SPANISH:
                    languageSelected = Constants.SPANISH;
                    spanishRadioButton.setChecked(true);
                    break;
                case Constants.ITALIAN:
                    languageSelected = Constants.ITALIAN;
                    italianRadioButton.setChecked(true);
                    break;
            }
        }

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
            if (!emoticonSelected.equals(emoticonSharedPre)){
                if (emoticon != null){
                    if (!emoticon.getName().equals(emoticonSelected)){
                        isUpdateData = true;
                    }
                }else{
                    isUpdateData = true;
                }
            }
        }
        if (isUpdateData){
            saveEmoticon();
        }else{
            if (languageSelected != null && !languageSelected.equals(languagePreference)){
                Utils.saveLanguagePreference(languageSelected, getActivity());
                changeLanguage(languageSelected);
                Toast.makeText(getActivity(), getString(R.string.data_saved), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void getEmoticon() {

        //check on SharedPreference
        SharedPreferences sharedPref = getContext().getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String emoticonShared = sharedPref.getString(getString(R.string.PREFERENCES_EMOTICON), null);

        if (emoticonShared == null){
            showLoading();
            String token = Constants.PRE_TOKEN + Utils.getTokenFromPreference(getActivity());
            new EmoticonUseCase(token).execute(new EmoticonSubscriber());
        }else{
            showLoading();
            emoticonSharedPre = emoticonShared;
            setEmoticonImage(emoticonShared);
            showContent();
        }
    }

    private void saveEmoticon(){
        showLoading();
        boolean isPositive = false;

            switch (emoticonSelected) {
                case Constants.VERY_HAPPY:
                case Constants.HAPPY:
                case Constants.IN_LOVE:
                case Constants.LAUGHING:
                    isPositive= true;
                    break;
                case Constants.DISAPPOINTED:
                case Constants.SAT:
                case Constants.CRYING:
                case Constants.ANGRY:
                    isPositive= false;
                    break;
                default:
                    isPositive= true;
                    break;
            }

        Date nowDate = new Date();

        String text = "{\n" +
                "  \"name\": \""+ emoticonSelected +"\",\n" +
                "  \"is_positive\": " + isPositive +",\n" +
                "  \"date\": \"" + nowDate.toString() +"\"\n" +
                "}";

        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), text);
        String token = Constants.PRE_TOKEN + Utils.getTokenFromPreference(getActivity());

        new UpdateEmoticonUseCase(token, body).execute(new UpdateEmoticonSubscriber());
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

    private void changeLanguage(String language){
        Locale locale;
        Configuration config = new Configuration();

        switch(language){
            case Constants.ENGLISH:
                locale = new Locale(Constants.ENGLISH);
                config.locale =locale;
                break;
            case Constants.SPANISH:
                locale = new Locale(Constants.SPANISH);
                config.locale =locale;
                break;
            case Constants.ITALIAN:
                locale = new Locale(Constants.ITALIAN);
                config.locale =locale;
                break;
        }
        getResources().updateConfiguration(config, null);
        Intent refresh = new Intent(getActivity(), MainActivity.class);
        refresh.putExtra("GO_TO_PROFILE", true);
        startActivity(refresh);
        getActivity().finish();
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
            case VERY_HAPPY:
                emoticonImageView.setImageResource(R.mipmap.ic_very_happy);
                break;
            case HAPPY:
                emoticonImageView.setImageResource(R.mipmap.ic_happy);
                break;
            case IN_LOVE:
                emoticonImageView.setImageResource(R.mipmap.ic_in_love);
                break;
            case LAUGHING:
                emoticonImageView.setImageResource(R.mipmap.ic_laughing);
                break;
            case SAT:
                emoticonImageView.setImageResource(R.mipmap.ic_sat);
                break;
            case DISAPPOINTED:
                emoticonImageView.setImageResource(R.mipmap.ic_disappointed);
                break;
            case CRYING:
                emoticonImageView.setImageResource(R.mipmap.ic_crying);
                break;
            case ANGRY:
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
    public void showContentNoEmoticon() {
        contentLayout.setVisibility(View.VISIBLE);
        loadingLayout.setVisibility(View.GONE);
        emoticonLayout.setVisibility(View.GONE);
    }

    /**
     * Method used to show the loading view
     */
    public void showLoading() {
        loadingLayout.setVisibility(View.VISIBLE);
        contentLayout.setVisibility(View.GONE);
        emoticonLayout.setVisibility(View.GONE);
    }

    /**
     * Method used to show the listView
     */
    public void showContent() {
        contentLayout.setVisibility(View.VISIBLE);
        emoticonLayout.setVisibility(View.VISIBLE);
        loadingLayout.setVisibility(View.GONE);
    }

    public void saveEmoticonSharedPreference(String emoticonName){
        SharedPreferences sharedPref = getContext().getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.PREFERENCES_EMOTICON), emoticonName);
        editor.commit();

        emoticonSharedPre = emoticonName;
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
            showContentNoEmoticon();
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
            Toast.makeText(getContext(), getActivity().getString(R.string.error_message),Toast.LENGTH_LONG).show();
        }

        //Update listview datas
        @Override
        public void onNext(Emoticon emoticon) {
            Toast.makeText(getActivity(), getString(R.string.data_saved), Toast.LENGTH_LONG).show();
            saveEmoticonSharedPreference(emoticon.getName());
            if (languageSelected != null && !languageSelected.equals(languagePreference)){
                Utils.saveLanguagePreference(languageSelected, getContext());
                changeLanguage(languageSelected);
            }
        }
    }
}
