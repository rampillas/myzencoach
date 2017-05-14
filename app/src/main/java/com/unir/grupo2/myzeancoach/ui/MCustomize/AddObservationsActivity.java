package com.unir.grupo2.myzeancoach.ui.MCustomize;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.domain.MCustomize.Remainders.Remainders.NewObservationsUseCase;
import com.unir.grupo2.myzeancoach.domain.utils.Utils;
import com.unir.grupo2.myzeancoach.ui.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscriber;

import static com.unir.grupo2.myzeancoach.domain.utils.Utils.isNewConnection;

public class AddObservationsActivity extends AppCompatActivity {
    @Nullable
    @BindView(R.id.remainderTitle)
    EditText remainderObservations;
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
    DatePickerDialog elegirFecha = null;
    String remainderTitle;

    @Optional
    @Nullable
    @OnClick(R.id.okButton)
    public void addReminder() {
        String token = Utils.getTokenFromPreference(getApplicationContext());
        String user = Utils.getUserFromPreference(getApplicationContext());
        if (!remainderObservations.getText().toString().isEmpty()) {
            String bodyString = "{\n" +
                    "\t\"title\": \"" + remainderTitle + "\",\n" +
                    "\t\"observations\": \"" + remainderObservations.getText().toString().toString().replaceAll("\"","\\\\\"") + "\"\n" +
                    "}";
            RequestBody rb = RequestBody.create(MediaType.parse("text/plain"), bodyString);
            showLoading();
            new NewObservationsUseCase(user, "Bearer " + token, rb).execute(new AddObservationsSuscriber());
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.alert_fill_out_all_fields), Toast.LENGTH_LONG).show();
        }

    }

    private final class AddObservationsSuscriber extends Subscriber<Void> {
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

    private void startMain() {
        Intent mainActivity = new Intent(this, MainActivity.class);
        startActivity(mainActivity);
    }

    @Override
    public void onBackPressed() {
        finish();
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remainder_add_observations);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        remainderTitle = intent.getStringExtra("REMAINDERTITLE");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        ActionBar toolbar = getSupportActionBar();
        //setSupportActionBar(toolbar);
        toolbar.setDisplayHomeAsUpEnabled(true);
        showContent();

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (isNewConnection()) {
            Utils.launchConnectionUseCase(this);
        }
    }
}
