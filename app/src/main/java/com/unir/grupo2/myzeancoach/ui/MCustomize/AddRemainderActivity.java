package com.unir.grupo2.myzeancoach.ui.MCustomize;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.domain.MCustomize.Remainders.Remainders.NewRemainderUserCase;
import com.unir.grupo2.myzeancoach.domain.model.RemainderItem;
import com.unir.grupo2.myzeancoach.ui.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Subscriber;

public class AddRemainderActivity extends AppCompatActivity  {
    @Nullable
    @BindView(R.id.remainderTitle)
    EditText remainderTitle;
    @Nullable
    @BindView(R.id.description)
    EditText description;
    @Nullable
    @BindView(R.id.datePickerText)
    TextView datePickerText;
    @Nullable
    @BindView(R.id.datePickerTextHour)
    TextView datePickerTextHour;
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
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    Intent intent;

    DatePickerDialog elegirFecha = null;
    @Optional
    @Nullable
    @OnClick(R.id.datePickerText)
    public void clickDate() {
        elegirFecha = new DatePickerDialog(this, datePickerDialog, 2017, 1, 1);
        elegirFecha.show();
    }

    DatePickerDialog.OnDateSetListener datePickerDialog = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int i, int i1, int i2) {
            datePickerText.setText(i + "-" + i1 + "-" + i2);
            elegirFecha.dismiss();
        }
    };

    TimePickerDialog elegirHora = null;
    @Optional
    @Nullable
    @OnClick(R.id.datePickerTextHour)
    public void clickHour() {
        elegirHora = new TimePickerDialog(this, hourPickerDialog, 12, 0, true);
        elegirHora.show();
    }

    TimePickerDialog.OnTimeSetListener hourPickerDialog = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int i, int i1) {
            datePickerTextHour.setText(i + ":" + i1);
            elegirHora.dismiss();
        }
    };
    @Optional
    @Nullable
    @OnClick(R.id.okButton)
    public void addReminder() {
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String token = sharedPref.getString(getString(R.string.PREFERENCES_TOKEN), null);
        String user = sharedPref.getString(getString(R.string.PREFERENCES_USER), null);
        if (!remainderTitle.getText().toString().isEmpty() && !description.getText().toString().isEmpty()
                && !datePickerText.getText().toString().isEmpty() && !datePickerTextHour.getText().toString().isEmpty()) {
            String bodyString = "{\n" +
                    "\t\"type\": \"individual\", \n" +
                    "\t\"user\": \"" + user + "\",\n" +
                    "\t\"title\": \"" + remainderTitle.getText().toString() + "\",\n" +
                    "\t\"subtitle\":  \"\", \n" +
                    "\t\"description\": \"" + description.getText().toString() + "\",\n" +
                    "\t\"is_personal\": true,\n" +
                    "\t\"date\": \"" + datePickerText.getText().toString() + "\",\n" +
                    "\t\"time\": \"" + datePickerTextHour.getText().toString() + "\",\n" +
                    "\t\"is_observations_enabled\": false,\n" +
                    "\t\"frequency\": \"everyday\"\n" +
                    "}";
            RequestBody rb = RequestBody.create(MediaType.parse("text/plain"),bodyString);
            RemainderItem body = new RemainderItem();
            body.setType("\"\"");
            body.setTitle(remainderTitle.getText().toString() );
            body.setDescription(description.getText().toString());
            body.setIsPersonal(true);
            body.setDate(datePickerText.getText().toString());
            body.setTime(datePickerTextHour.getText().toString());
            body.setFrequency("everyday");
            showLoading();
            new NewRemainderUserCase(user, "Bearer " + token, rb).execute(new AddRemainderActivity.AddRemainderSubscriber());
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

    private void startMain() {
        Intent mainActivity= new Intent(this, MainActivity.class);
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
        setContentView(R.layout.remainders_add_layout);
        ButterKnife.bind(this);
        showContent();

    }

}
