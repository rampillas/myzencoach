package com.unir.grupo2.myzeancoach.ui.MCustomizeFragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import rx.Subscriber;

public class AddRemainderFragment extends Fragment {
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
        elegirFecha = new DatePickerDialog(getActivity(), datePickerDialog, 2017, 1, 1);
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
        elegirHora = new TimePickerDialog(getActivity(), hourPickerDialog, 12, 0, true);
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
        SharedPreferences sharedPref = getContext().getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String token = sharedPref.getString(getString(R.string.PREFERENCES_TOKEN), null);
        String user = sharedPref.getString(getString(R.string.PREFERENCES_USER), null);
        if (!remainderTitle.getText().toString().isEmpty() && !description.getText().toString().isEmpty()
                && !datePickerText.getText().toString().isEmpty() && !datePickerTextHour.getText().toString().isEmpty()) {
            String bodyString = "{\n" +
                    "\t\"type\": \"\" \n" +
                    "\t\"title\": " + remainderTitle.getText().toString() + "\n" +
                    "\t\"subtitle\":  \"\" \n" +
                    "\t\"description\": " + description.getText().toString() + "\n" +
                    "\t\"is_personal\": true\n" +
                    "\t\"date\": " + datePickerText.getText().toString() + "\n" +
                    "\t\"time\": " + datePickerTextHour.getText().toString() + "\n" +
                    "\t\"is_observations_enabled\": false\n" +
                    "\t\"frequency\": everyday\n" +
                    "}";
            RemainderItem body = new RemainderItem();
            body.setType("\"\"");
            body.setTitle(remainderTitle.getText().toString() );
            body.setDescription(description.getText().toString());
            body.setIsPersonal(true);
            body.setDate(datePickerText.getText().toString());
            body.setTime(datePickerTextHour.getText().toString());
            body.setFrequency("everyday");
            showLoading();
            new NewRemainderUserCase(user, "Bearer " + token, body).execute(new AddRemainderFragment.AddRemainderSubscriber());
        } else {
            Toast.makeText(getContext(), getString(R.string.REMAINDERS_EMPTY_ADD), Toast.LENGTH_LONG).show();
        }

    }

    private final class AddRemainderSubscriber extends Subscriber<Void> {
        //3 callbacks
        //Show the listView
        @Override
        public void onCompleted() {
            showContent();
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.remainders_add_layout, null);
        ButterKnife.bind(this, view);
        return view;
    }

}
