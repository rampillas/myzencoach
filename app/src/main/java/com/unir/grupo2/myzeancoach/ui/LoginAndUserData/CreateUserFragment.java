package com.unir.grupo2.myzeancoach.ui.LoginAndUserData;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mukesh.countrypicker.fragments.CountryPicker;
import com.mukesh.countrypicker.interfaces.CountryPickerListener;
import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.domain.LoginAndUserData.CreateUserServer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import id.arieridwan.lib.PageLoader;


public class CreateUserFragment extends Fragment {
    String Pais = "";
    int EstudiosPersona = 0;
    @BindView(R.id.UsuarioSingUp)
    EditText Usuario;
    String UsuarioValor;
    @BindView(R.id.Password)
    EditText Password;
    String PasswordValor;
    @BindView(R.id.email)
    EditText Email;
    String EmailValor;
    @BindView(R.id.UNombreSingUp)
    EditText Nombre;
    String NombreValor;
    @BindView(R.id.datePickerText)
    TextView Nacimiento;
    String NacimientoValor;
    @BindView(R.id.radio_man)
    RadioButton Hombre;
    @BindView(R.id.radio_woman)
    RadioButton Mujer;
    String SexoValor;
    @BindView(R.id.PaisList)
    Button ListaPaises;
    String PaisValor;
    @BindView(R.id.ciudad)
    EditText Ciudad;
    String CiudadValor;
    @BindView(R.id.radio_rural)
    RadioButton Rural;
    @BindView(R.id.radio_urbana)
    RadioButton Urbana;
    String ZonaValor;
    @BindView(R.id.radio_si)
    RadioButton Si;
    @BindView(R.id.radio_no)
    RadioButton No;
    String SiNoValor;
    @BindView(R.id.StudyList)
    Spinner Estudios;
    String EstudiosValor;
    @BindView(R.id.okButton)
    Button okButton;
    //validacion del formulario
    @BindView(R.id.pageloader)
    public
    PageLoader pageLoader;

    //iniciamos los radio buttons
    @OnClick(R.id.radio_si)
    public void nada() {
    }

    @OnClick(R.id.radio_no)
    public void nada2() {
    }

    @OnClick(R.id.radio_man)
    public void nada3() {
    }

    @OnClick(R.id.radio_woman)
    public void nada4() {
    }

    @OnClick(R.id.radio_rural)
    public void nada5() {
    }

    @OnClick(R.id.radio_urbana)
    public void nada6() {
    }



    //datetimePicker
    DatePickerDialog elegirFecha=null;
    @OnClick(R.id.datePickerText)
    public void ElegirFechaNacimiento() {
        elegirFecha=new DatePickerDialog(getContext(), datePickerDialog, 2017,1,1);
        elegirFecha.show();
    }


    DatePickerDialog.OnDateSetListener datePickerDialog = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int i, int i1, int i2) {
            Nacimiento.setText(i+":"+i1+":"+i2);
            elegirFecha.dismiss();
        }
    };





    @OnClick(R.id.okButton)
    public void crearCuenta() {
        // TODO comprobar que loscampos estan rellenos
        if (Usuario.getText().length() > 0 && Password.getText().length() > 0 && Email.getText().length() > 0 && Nombre.getText().length() > 0 && Nacimiento.getText().length() > 0
                && Pais.length() > 0 && Ciudad.getText().length() > 0 && EstudiosPersona > 0) {
            UsuarioValor = Usuario.getText().toString();
            PasswordValor = Password.getText().toString();
            EmailValor = Email.getText().toString();
            NombreValor = Nombre.getText().toString();
            NacimientoValor = Nacimiento.getText().toString();
            if (Hombre.isChecked()) SexoValor = "Hombre";
            else SexoValor = "Mujer";
            PaisValor = Pais;
            CiudadValor = Ciudad.getText().toString();
            if (Rural.isChecked()) ZonaValor = "Rulal";
            else ZonaValor = "Urbana";
            if (Si.isChecked()) SiNoValor = "Si";
            else SiNoValor = "No";
            switch (EstudiosPersona) {
                case 1:
                    EstudiosValor = "Primaria";
                    break;
                case 2:
                    EstudiosValor = "Secundaria";
                    break;
                case 3:
                    EstudiosValor = "Bachiller";
                    break;
                case 4:
                    EstudiosValor = "Ciclo Formativo";
                    break;
                case 5:
                    EstudiosValor = "Carrera Universitaria";
                    break;
                case 6:
                    EstudiosValor = "Doctorado";
                    break;
            }
            CreateUserServer RegisterUser = new CreateUserServer();
            RegisterUser.NewUser(UsuarioValor, PasswordValor, EmailValor, NombreValor, NacimientoValor, SexoValor, PaisValor, CiudadValor, ZonaValor, SiNoValor, EstudiosValor, this);
        } else {
            Toast.makeText(getContext(), getResources().getString(R.string.SIGNUP_INCORRECT_DATA), Toast.LENGTH_LONG).show();

        }
        // TODO hacer la creacion de usuario
        // TODO volver al login Fragment

    }

    @OnClick(R.id.PaisList)
    public void ListaPaises() {
        final CountryPicker picker = CountryPicker.newInstance("Select Country");
        picker.show(getFragmentManager(), "COUNTRY_PICKER");
        picker.setListener(new CountryPickerListener() {
            @Override
            public void onSelectCountry(String name, String code, String dialCode, int flagDrawableResID) {
                Pais = name;
                ListaPaises.setText(name);
                ListaPaises.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                ListaPaises.setTextColor(getResources().getColor(R.color.white));
                picker.dismiss();
            }
        });
    }

    @OnItemSelected(R.id.StudyList)
    public void spinnerItemSelected(Spinner spinner, int position) {
        EstudiosPersona = spinner.getSelectedItemPosition();
    }

    public void errorServer() {
        Toast.makeText(getContext(), getResources().getString(R.string.LOGIN_ERROR_SERVER), Toast.LENGTH_LONG).show();
    }

    public void userExits() {
        Toast.makeText(getContext(), getResources().getString(R.string.SIGNUP_USER_EXITS), Toast.LENGTH_LONG).show();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_user, null);
        ButterKnife.bind(this, view);
        // Inflate the layout for this fragment

// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.SIGNUP_STUDY_LEVELS, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        Estudios.setAdapter(adapter);

        return view;
    }

}
