package com.unir.grupo2.myzeancoach.ui.LoginAndUserData;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.mukesh.countrypicker.fragments.CountryPicker;
import com.mukesh.countrypicker.interfaces.CountryPickerListener;
import com.unir.grupo2.myzeancoach.R;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnItemSelected;


public class CreateUserFragment extends Fragment {
    String Pais = "";
    int EstudiosPersona = 0;
    @BindView(R.id.UsuarioSingUp)
    EditText Usuario;
    @BindView(R.id.Password)
    EditText Password;
    @BindView(R.id.email)
    EditText Email;
    @BindView(R.id.UNombreSingUp)
    EditText Nombre;
    @BindView(R.id.datePickerText)
    EditText Nacimiento;
    @BindView(R.id.radio_man)
    RadioButton Hombre;
    @BindView(R.id.radio_woman)
    RadioButton Mujer;
    @BindView(R.id.PaisList)
    Button ListaPaises;
    @BindView(R.id.ciudad)
    EditText Ciudad;
    @BindView(R.id.radio_rural)
    RadioButton Rural;
    @BindView(R.id.radio_urbana)
    RadioButton Urbana;
    @BindView(R.id.radio_si)
    RadioButton Si;
    @BindView(R.id.radio_no)
    RadioButton No;
    @BindView(R.id.StudyList)
    Spinner Estudios;
    @BindView(R.id.okButton)
    Button okButton;

    @OnClick(R.id.okButton)
    public void crearCuenta() {
        // TODO comprobar que loscampos estan rellenos
        // TODO hacer la creacion de usuario
        // TODO volver al login Fragment

    }

    @OnClick(R.id.PaisList)
    public void ListaPaises() {
        CountryPicker picker = CountryPicker.newInstance("Select Country");
        picker.show(getFragmentManager(), "COUNTRY_PICKER");
        picker.setListener(new CountryPickerListener() {
            @Override
            public void onSelectCountry(String name, String code, String dialCode, int flagDrawableResID) {
                Pais = name;
            }
        });
    }

    @OnItemSelected(R.id.StudyList)
    public void spinnerItemSelected(Spinner spinner, int position) {
        EstudiosPersona = spinner.getSelectedItemPosition();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.SIGNUP_STUDY_LEVELS, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        Estudios.setAdapter(adapter);
        return inflater.inflate(R.layout.create_user, container, false);
    }

}
