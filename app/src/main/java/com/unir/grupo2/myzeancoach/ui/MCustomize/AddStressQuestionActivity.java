package com.unir.grupo2.myzeancoach.ui.MCustomize;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.domain.MCustomize.Remainders.Stress.SetPersonalQuestionUseCase;
import com.unir.grupo2.myzeancoach.ui.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscriber;

/**
 * Created by andres on 29/03/2017.
 */

public class AddStressQuestionActivity extends AppCompatActivity {
    @Nullable
    @BindView(R.id.stressQuestion)
    EditText stressTitle;
    @Nullable
    @BindView(R.id.option1)
    EditText option1;
    @Nullable
    @BindView(R.id.option2)
    EditText option2;
    @Nullable
    @BindView(R.id.option3)
    EditText option3;
    @Nullable
    @BindView(R.id.option4)
    EditText option4;
    @Nullable
    @BindView(R.id.okButton)
    Button save;
    @Nullable
    @BindView(R.id.content)
    LinearLayout content;
    @Nullable
    @BindView(R.id.loading_layout)
    LinearLayout loadingLayout;
    @Nullable
    @BindView(R.id.error_layout)
    LinearLayout errorLayout;

    @Optional
    @Nullable
    @OnClick(R.id.okButton)
    public void addReminder() {
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String token = sharedPref.getString(getString(R.string.PREFERENCES_TOKEN), null);
        String user = sharedPref.getString(getString(R.string.PREFERENCES_USER), null);
        //get the options and create the request body
        if (!stressTitle.getText().toString().isEmpty() && !option1.getText().toString().isEmpty()
                && !option2.getText().toString().isEmpty() && !option3.getText().toString().isEmpty()
                && !option4.getText().toString().isEmpty()) {
            String bodyString = "{\n" +
                    "\t\"description\": \"" + stressTitle.getText().toString() + "\",\n" +
                    "\t\"possible_amswers\": 4 ,\n" +
                    "\t\"is_personal_question\": true ,\n" +
                    "\t\"answers\":  [{ \n" +
                    "\t\"description\": \"" + option1.getText().toString() + "\",\n" +
                    "\t\"color\": \"azul\",\n" +
                    "\t\"popup_message\": \"\"},{\n" +
                    "\t\"description\": \"" + option2.getText().toString() + "\",\n" +
                    "\t\"color\": \"amarillo\",\n" +
                    "\t\"popup_message\": \"\"},{\n" +
                    "\t\"description\": \"" + option3.getText().toString() + "\",\n" +
                    "\t\"color\": \"rojo\",\n" +
                    "\t\"popup_message\": \"\"},{\n" +
                    "\t\"description\": \"" + option4.getText().toString() + "\",\n" +
                    "\t\"color\": \"naranja\",\n" +
                    "\t\"popup_message\": \"\"}]\n" +
                    "}";
            Log.d("BODY: ", bodyString);
            RequestBody rb = RequestBody.create(MediaType.parse("text/plain"), bodyString);
            showLoading();
            new SetPersonalQuestionUseCase("Bearer " + token, rb).execute(new AddStressQuestionActivity.AddRemainderSubscriber());
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.alert_fill_out_all_fields), Toast.LENGTH_LONG).show();
        }

    }

    private final class AddRemainderSubscriber extends Subscriber<Void> {
        //3 callbacks
        //Show the listView
        @Override
        public void onCompleted() {
            showContent();
            startMain();
        }

        //Show the error
        @Override
        public void onError(Throwable e) {
            Log.e("ERROR REMINDERS ADD ", e.toString());
            showError();
        }

        @Override
        public void onNext(Void aVoid) {
            //volvemos al fragment de los remainders

        }
    }

    public void showError() {
        content.setVisibility(View.GONE);
        loadingLayout.setVisibility(View.GONE);
        errorLayout.setVisibility(View.VISIBLE);
    }

    /**
     * Method used to show the loading view
     */
    public void showLoading() {
        loadingLayout.setVisibility(View.VISIBLE);
        content.setVisibility(View.GONE);
        errorLayout.setVisibility(View.GONE);
    }

    /**
     * Method used to show the listView
     */
    public void showContent() {
        content.setVisibility(View.VISIBLE);
        loadingLayout.setVisibility(View.GONE);
        errorLayout.setVisibility(View.GONE);
    }

    private void startMain() {
        Intent mainActivity = new Intent(this, MainActivity.class);
        startActivity(mainActivity);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stress_add_quesion);
        ButterKnife.bind(this);
        showContent();
    }
}
