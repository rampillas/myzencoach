package com.unir.grupo2.myzeancoach.ui.LoginAndUserData;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.UserValidator;
import com.example.exceptions.email.InvalidEmailFormatException;
import com.example.exceptions.email.NullEmailException;
import com.example.exceptions.password.InvalidPasswordException;
import com.example.exceptions.password.InvalidPasswordFormatException;
import com.example.exceptions.password.InvalidPasswordLengthException;
import com.example.exceptions.password.NullPasswordException;
import com.example.exceptions.username.InvalidUsernameFormatException;
import com.example.exceptions.username.InvalidUsernameLengthException;
import com.example.exceptions.username.UsernameIsNullException;
import com.mukesh.countrypicker.fragments.CountryPicker;
import com.mukesh.countrypicker.interfaces.CountryPickerListener;
import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.domain.LoginAndUserData.CreateUserServer;
import com.unir.grupo2.myzeancoach.domain.LoginAndUserData.LoginChecker;
import com.unir.grupo2.myzeancoach.domain.model.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;


public class CreateUserFragment extends Fragment {
    String pais = "";
    int estudiosPersona = 0;
    @BindView(R.id.UsuarioSingUp)
    EditText usuario;
    String usuarioValor;
    @BindView(R.id.Password)
    EditText password;
    String passwordValor;
    @BindView(R.id.email)
    EditText email;
    String emailValor;
    @BindView(R.id.UNombreSingUp)
    EditText apellido;
    String apellidoValor;
    @BindView(R.id.ApellidoSingUp)
    EditText nombre;
    String nombreValor;
    @BindView(R.id.datePickerText)
    TextView nacimiento;
    String nacimientoValor;
    @BindView(R.id.radio_man)
    RadioButton hombre;
    @BindView(R.id.radio_woman)
    RadioButton mujer;
    String sexoValor;
    @BindView(R.id.PaisList)
    Button listaPaises;
    String paisValor;
    @BindView(R.id.ciudad)
    EditText ciudad;
    String ciudadValor;
    @BindView(R.id.radio_rural)
    RadioButton rural;
    @BindView(R.id.radio_urbana)
    RadioButton urbana;
    String zonaValor;
    @BindView(R.id.radio_si)
    RadioButton si;
    @BindView(R.id.radio_no)
    RadioButton no;
    String siNoValor;
    @BindView(R.id.StudyList)
    Spinner estudios;
    String estudiosValor;
    @BindView(R.id.okButton)
    Button okButton;
    //validacion del formulario
    @BindView(R.id.loading_layout)
    LinearLayout loadingLayout;
    @BindView(R.id.error_layout)
    LinearLayout errorLayout;
    @BindView(R.id.createUserLayout)
    LinearLayout createUserLayout;

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

    //Editar para cuando se viene de la pantalla anterior
    String editar = "No";
    //datetimePicker
    DatePickerDialog elegirFecha = null;

    @OnClick(R.id.datePickerText)
    public void elegirFechaNacimiento() {
        elegirFecha = new DatePickerDialog(getContext(), datePickerDialog, 2017, 1, 1);
        elegirFecha.show();
    }


    DatePickerDialog.OnDateSetListener datePickerDialog = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int i, int i1, int i2) {
            nacimiento.setText(i + "-" + i1 + "-" + i2);
            elegirFecha.dismiss();
        }
    };


    @OnClick(R.id.okButton)
    public void addAcount() {
        if (usuario.getText().length() > 0 && password.getText().length() > 0 && email.getText().length() > 0 && nombre.getText().length() > 0 && nacimiento.getText().length() > 0
                && pais.length() > 0 && ciudad.getText().length() > 0 && estudiosPersona > 0) {
            UserValidator validator = UserValidator.builder().build();
            usuarioValor = usuario.getText().toString();
            //encriptar la clave
            passwordValor = password.getText().toString();
            emailValor = email.getText().toString();
            nombreValor = nombre.getText().toString();
            apellidoValor = apellido.getText().toString();
            nacimientoValor = nacimiento.getText().toString();
            if (hombre.isChecked()) sexoValor = "H";
            else sexoValor = "M";
            paisValor = pais;
            ciudadValor = ciudad.getText().toString();
            if (rural.isChecked()) zonaValor = "Rural";
            else zonaValor = "Urbana";
            if (si.isChecked()) siNoValor = "SI";
            else siNoValor = "NO";
            switch (estudiosPersona) {
                case 1:
                    estudiosValor = "Primaria";
                    break;
                case 2:
                    estudiosValor = "Secundaria";
                    break;
                case 3:
                    estudiosValor = "Bachiller";
                    break;
                case 4:
                    estudiosValor = "Ciclo Formativo";
                    break;
                case 5:
                    estudiosValor = "Carrera Universitaria";
                    break;
                case 6:
                    estudiosValor = "Doctorado";
                    break;
            }
            //validacion de campos
            UserValidator.builder().usernamePattern("").build();
            try {
                if (validator.validateUsername(usuarioValor) &&
                        validator.validateEmail(emailValor) &&
                        validator.validatePassword(usuarioValor, passwordValor)) {
                    CreateUserServer registerUser = new CreateUserServer();

                    registerUser.newUser(usuarioValor, emailValor, nombreValor, apellidoValor, passwordValor,
                            nacimientoValor, sexoValor, paisValor, ciudadValor, "empleado", zonaValor, siNoValor, estudiosValor, this);

                }
            } catch (InvalidEmailFormatException e) { // Catch all exceptions you're interested to handle
                Toast.makeText(getContext(), getResources().getString(R.string.SIGNUP_ERROR_EMAIL), Toast.LENGTH_LONG).show();
            } catch (NullEmailException e) { // Catch all exceptions you're interested to handle
                Toast.makeText(getContext(), getResources().getString(R.string.SIGNUP_ERROR_EMAIL_NULL), Toast.LENGTH_LONG).show();
            } catch (InvalidPasswordException e) { // Catch all exceptions you're interested to handle
                Toast.makeText(getContext(), getResources().getString(R.string.SIGNUP_ERROR_PASSWORD), Toast.LENGTH_LONG).show();
            } catch (InvalidPasswordFormatException e) { // Catch all exceptions you're interested to handle
                CreateUserServer registerUser = new CreateUserServer();
                registerUser.newUser(usuarioValor, emailValor, nombreValor, apellidoValor, passwordValor,
                        nacimientoValor, sexoValor, paisValor, ciudadValor, "empleado", zonaValor, siNoValor, estudiosValor, this);
            } catch (InvalidPasswordLengthException e) { // Catch all exceptions you're interested to handle
                Toast.makeText(getContext(), getResources().getString(R.string.SIGNUP_ERROR_PASSWORD_LEN), Toast.LENGTH_LONG).show();
            } catch (NullPasswordException e) { // Catch all exceptions you're interested to handle
                Toast.makeText(getContext(), getResources().getString(R.string.SIGNUP_ERROR_PASSWORD_NULL), Toast.LENGTH_LONG).show();
            } catch (UsernameIsNullException e) { // Catch all exceptions you're interested to handle
                Toast.makeText(getContext(), getResources().getString(R.string.SIGNUP_ERROR_USERNAME_NULL), Toast.LENGTH_LONG).show();
            } catch (InvalidUsernameFormatException e) { // Catch all exceptions you're interested to handle
                Toast.makeText(getContext(), getResources().getString(R.string.SIGNUP_ERROR_USERNAME), Toast.LENGTH_LONG).show();
            } catch (InvalidUsernameLengthException e) { // Catch all exceptions you're interested to handle
                Toast.makeText(getContext(), getResources().getString(R.string.SIGNUP_ERROR_USERNAME_LEN), Toast.LENGTH_LONG).show();
            } catch (Exception e) { // Catch all exceptions you're interested to handle
                Toast.makeText(getContext(), getResources().getString(R.string.SIGNUP_ERROR_FIELDS), Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getContext(), getResources().getString(R.string.SIGNUP_INCORRECT_DATA), Toast.LENGTH_LONG).show();

        }


    }

    @OnClick(R.id.PaisList)
    public void showCountryList() {
        final CountryPicker picker = CountryPicker.newInstance("Select Country");
        picker.show(getFragmentManager(), "COUNTRY_PICKER");
        picker.setListener(new CountryPickerListener() {
            @Override
            public void onSelectCountry(String name, String code, String dialCode, int flagDrawableResID) {
                pais = name;
                listaPaises.setText(name);
                listaPaises.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                listaPaises.setTextColor(getResources().getColor(R.color.white));
                picker.dismiss();
            }
        });
    }

    @OnItemSelected(R.id.StudyList)
    public void spinnerItemSelected(Spinner spinner, int position) {
        estudiosPersona = spinner.getSelectedItemPosition();
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

        //en funcion de si el usuario esta logueado o no se muestran sus datosen pantalla o los campos vacios
        Context context = getActivity();
        SharedPreferences sharedPref = context.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String nombreDeUsuario = sharedPref.getString(getString(R.string.PREFERENCES_USER), null);
        String claveDeUsuario = sharedPref.getString(getString(R.string.PREFERENCES_PASSWORD), null);

        //iniciadores de vistas y spinner
        View view = inflater.inflate(R.layout.create_user, null);
        ButterKnife.bind(this, view);
        // Inflate the layout for this fragment

// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.SIGNUP_STUDY_LEVELS, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        estudios.setAdapter(adapter);

        //comprobamos logueo
        if (nombreDeUsuario == null) {
            //mostrar pantalla de creacion


            return view;
        } else {
            //mostrar pantalla rellena de datos qque hay que obtener de la db
            Log.d("usuario checkeado", "esta logeado el usuario " + nombreDeUsuario);
            LoginChecker loginChecker = new LoginChecker();
            //loginChecker.Login("application/json","clientweb2231", "secretweb2231", nombreDeUsuario, claveDeUsuario, "password", "read+write", this);
            return view;
        }

    }

    public void showFieldsIntoCases(User userObject) {
        nombre.setText(userObject.getFirstName());
        apellido.setText(userObject.getLastName());
        usuario.setText(userObject.getUsername());
        usuario.setEnabled(false);
        //se muestra la clave
        password.setText(userObject.getPassword());
        email.setText(userObject.getEmail());
        nacimiento.setText(userObject.getProfile().getBirthday());
        listaPaises.setText(userObject.getProfile().getCountry());
        pais = userObject.getProfile().getCountry();
        ciudad.setText(userObject.getProfile().getCity());
        if (userObject.getProfile().getGender().equalsIgnoreCase("H")) hombre.setChecked(true);
        else mujer.setChecked(true);
        if (userObject.getProfile().getChangeCountry().equalsIgnoreCase("SI")) si.setChecked(true);
        else no.setChecked(true);
        if (userObject.getProfile().getRuralZone().equalsIgnoreCase("Rural"))
            rural.setChecked(true);
        else urbana.setChecked(true);

        switch (userObject.getProfile().getLevelStudies()) {
            case "Primaria":
                estudios.setSelection(1);
                break;
            case "Secundaria":
                estudios.setSelection(2);
                break;
            case "Bachiller":
                estudios.setSelection(3);
                break;
            case "Ciclo Formativo":
                estudios.setSelection(4);
                break;
            case "Carrera Universitaria":
                estudios.setSelection(5);
                break;
            case "Doctorado":
                estudios.setSelection(6);
                break;
        }
        editar = "Si";

    }

    /**
     * Method used to show error view
     */
    public void showError() {
        createUserLayout.setVisibility(View.GONE);
        loadingLayout.setVisibility(View.GONE);
        errorLayout.setVisibility(View.VISIBLE);
    }

    /**
     * Method used to show the loading view
     */
    public void showLoading() {
        loadingLayout.setVisibility(View.VISIBLE);
        createUserLayout.setVisibility(View.GONE);
        errorLayout.setVisibility(View.GONE);
    }

    /**
     * Method used to show the listView
     */
    public void showContent() {
        createUserLayout.setVisibility(View.VISIBLE);
        loadingLayout.setVisibility(View.GONE);
        errorLayout.setVisibility(View.GONE);
    }

}
