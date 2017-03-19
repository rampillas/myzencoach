package com.unir.grupo2.myzeancoach.ui.MCustomizeFragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.unir.grupo2.myzeancoach.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddRemainderActivity extends AppCompatActivity {
    @BindView(R.id.remainderTitle)
    EditText remainderTitle;
    @BindView(R.id.description)
    EditText description;
    @BindView(R.id.datePickerText)
    TextView datePickerText;
    @BindView(R.id.datePickerTextHour)
    TextView datePickerTextHour;
    @BindView(R.id.okButton)
    Button save;

    DatePickerDialog elegirFecha = null;

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

    @OnClick(R.id.datePickerTextHour)
    public void clickHour() {
        elegirHora = new TimePickerDialog(this, hourPickerDialog, 12, 0, true);
        elegirHora.show();
    }

    TimePickerDialog.OnTimeSetListener hourPickerDialog = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int i, int i1) {
            datePickerTextHour.setText(i + ":" + i1 );
            elegirHora.dismiss();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remainders_add_layout);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

}
