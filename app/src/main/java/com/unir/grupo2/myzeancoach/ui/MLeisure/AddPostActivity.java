package com.unir.grupo2.myzeancoach.ui.MLeisure;

/**
 * Created by Cesar on 11/03/2017.
 */

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.domain.MLeisure.CreateEventUseCase;
import com.unir.grupo2.myzeancoach.domain.model.Event;
import com.unir.grupo2.myzeancoach.domain.model.Interest;
import com.unir.grupo2.myzeancoach.domain.utils.Utils;

import java.lang.reflect.Type;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscriber;

import static com.unir.grupo2.myzeancoach.domain.utils.Utils.isNewConnection;


public class AddPostActivity extends AppCompatActivity {

    // ui
    @BindView(R.id.content_scrollView)
    ScrollView contentScrollView;
    @BindView(R.id.loading_layout)
    LinearLayout loadingLayout;
    @BindView(R.id.error_layout)
    LinearLayout errorLayout;

    @BindView(R.id.title_editText)
    EditText titleEditText;
    @BindView(R.id.description_editText)
    EditText descriptionEditText;
    @BindView(R.id.add_event_button)
    Button addEventButton;
    @BindView(R.id.category_radioGroup)
    RadioGroup categoryRadioGroup;

    private String[] categories = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_add_layout);
        ButterKnife.bind(this);

        showContent();

        categories = getResources().getStringArray(R.array.array_event_categories);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        final RadioButton[] rb = new RadioButton[8];
        for (int i = 0; i < categories.length; i++) {
            rb[i] = new RadioButton(this);
            rb[i].setText(Utils.getCategoryEvent(this, categories[i]));
            rb[i].setId(i);
            categoryRadioGroup.addView(rb[i]);
        }
    }

    @Override
    protected void onStart(){
        super.onStart();
        if (isNewConnection()){
            Utils.launchConnectionUseCase(this);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Utils.closeSoftKeyboard(AddPostActivity.this);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.add_event_button)
    public void addEvent(View view) {

        int radioButtonID = categoryRadioGroup.getCheckedRadioButtonId();

        if (titleEditText.getText().toString().trim().length() == 0 ||
                descriptionEditText.getText().toString().trim().length() == 0 ||
                radioButtonID == -1){

            showDialogFillOutFields();
        }else{

            showLoading();

            String userName = Utils.getUserFromPreference(this);
            String token = "Bearer " + Utils.getTokenFromPreference(this);
            String eventDate = Utils.dateNowForBackend();

            String eventText = "{\n" +
                    "    \"date\": \"" + eventDate + "\",\n" +
                    "    \"title\": \"" + titleEditText.getText().toString().trim() + "\",\n" +
                    "    \"description\": \"" + descriptionEditText.getText().toString().trim() + "\",\n" +
                    "    \"category\": \"" + categories[radioButtonID] + "\"\n" +
                    "}\n";

            RequestBody eventbody = RequestBody.create(MediaType.parse("text/plain"), eventText);

            new CreateEventUseCase(userName,token,eventbody).execute(new AddEventSubscriber());
        }
    }

    private void showDialogFillOutFields(){
        new AlertDialog.Builder(this)
                .setMessage(getString(R.string.alert_fill_out_fields_event))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void setData(Event event){
        Intent resultData = new Intent();
        resultData.putExtra("EVENT_NEW",event);
        setResult(Activity.RESULT_OK, resultData);
    }

    private boolean isCategoryInUserInterests(String eventCategory){
        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String savedList = sharedPref.getString(getString(R.string.PREFERENCES_INTERESTS_LEISURE), "default");

        Type type = new TypeToken<List<Interest>>() {}.getType();
        Gson gson = new Gson();
        List<Interest> interestList = gson.fromJson(savedList, type);

        boolean isIn = false;
        for (int i = 0; i < interestList.size(); i++){
            if (eventCategory.equals(interestList.get(i).getName())){
                isIn = true;
                break;
            }
        }
        return isIn;
    }

    /**
     * Method used to show error view
     */
    public void showError() {
        contentScrollView.setVisibility(View.GONE);
        loadingLayout.setVisibility(View.GONE);
        errorLayout.setVisibility(View.VISIBLE);
    }

    /**
     * Method used to show the loading view
     */
    public void showLoading() {
        loadingLayout.setVisibility(View.VISIBLE);
        contentScrollView.setVisibility(View.GONE);
        errorLayout.setVisibility(View.GONE);
    }

    /**
     * Method used to show the listView
     */
    public void showContent() {
        contentScrollView.setVisibility(View.VISIBLE);
        loadingLayout.setVisibility(View.GONE);
        errorLayout.setVisibility(View.GONE);
    }

    private final class AddEventSubscriber extends Subscriber<Event> {
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
            if (isCategoryInUserInterests(event.getCategory())){
                setData(event);
            }
            finish();
        }
    }
}