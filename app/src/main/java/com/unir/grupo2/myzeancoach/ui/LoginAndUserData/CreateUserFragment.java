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
import com.unir.grupo2.myzeancoach.Encryption.Security;
import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.data.UserData.UserObject;
import com.unir.grupo2.myzeancoach.domain.LoginAndUserData.CreateUserServer;
import com.unir.grupo2.myzeancoach.domain.LoginAndUserData.LoginChecker;

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

    //Editar para cuando se viene de la pantalla anterior
    String Editar="No";
    //datetimePicker
    DatePickerDialog elegirFecha = null;

    @OnClick(R.id.datePickerText)
    public void ElegirFechaNacimiento() {
        elegirFecha = new DatePickerDialog(getContext(), datePickerDialog, 2017, 1, 1);
        elegirFecha.show();
    }


    DatePickerDialog.OnDateSetListener datePickerDialog = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int i, int i1, int i2) {
            Nacimiento.setText(i + ":" + i1 + ":" + i2);
            elegirFecha.dismiss();
        }
    };


    @OnClick(R.id.okButton)
    public void crearCuenta() {
        // TODO comprobar que loscampos estan rellenos
        if (Usuario.getText().length() > 0 && Password.getText().length() > 0 && Email.getText().length() > 0 && Nombre.getText().length() > 0 && Nacimiento.getText().length() > 0
                && Pais.length() > 0 && Ciudad.getText().length() > 0 && EstudiosPersona > 0) {
            UserValidator validator = UserValidator.builder().build();
            UsuarioValor = Usuario.getText().toString();
            //encriptar la clave
            Security encriptador=new Security();
            String claveEncriptada=encriptador.encrypt(Password.getText().toString());
            PasswordValor = claveEncriptada;
            EmailValor = Email.getText().toString();
            NombreValor = Nombre.getText().toString();
            NacimientoValor = Nacimiento.getText().toString();
            if (Hombre.isChecked()) SexoValor = "Hombre";
            else SexoValor = "Mujer";
            PaisValor = Pais;
            CiudadValor = Ciudad.getText().toString();
            if (Rural.isChecked()) ZonaValor = "Rural";
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
            //validacion de campos
            UserValidator.builder().usernamePattern("").build();
            try {
                if (validator.validateUsername(UsuarioValor) &&
                        validator.validateEmail(EmailValor) &&
                        validator.validatePassword(UsuarioValor, PasswordValor)) {
                    CreateUserServer RegisterUser = new CreateUserServer();
                    RegisterUser.NewUser(UsuarioValor, PasswordValor, EmailValor, NombreValor, NacimientoValor, SexoValor, PaisValor, CiudadValor, ZonaValor, SiNoValor, EstudiosValor,Editar, this);

                }
            } catch (InvalidEmailFormatException e) { // Catch all exceptions you're interested to handle
                Toast.makeText(getContext(),getResources().getString(R.string.SIGNUP_ERROR_EMAIL),Toast.LENGTH_LONG).show();
            }catch (NullEmailException e) { // Catch all exceptions you're interested to handle
                Toast.makeText(getContext(),getResources().getString(R.string.SIGNUP_ERROR_EMAIL_NULL),Toast.LENGTH_LONG).show();
            }catch (InvalidPasswordException e) { // Catch all exceptions you're interested to handle
                Toast.makeText(getContext(),getResources().getString(R.string.SIGNUP_ERROR_PASSWORD),Toast.LENGTH_LONG).show();
            }catch (InvalidPasswordFormatException e) { // Catch all exceptions you're interested to handle
                CreateUserServer RegisterUser = new CreateUserServer();
                RegisterUser.NewUser(UsuarioValor, PasswordValor, EmailValor, NombreValor, NacimientoValor, SexoValor, PaisValor, CiudadValor, ZonaValor, SiNoValor, EstudiosValor,Editar, this);

            }catch (InvalidPasswordLengthException e) { // Catch all exceptions you're interested to handle
                Toast.makeText(getContext(),getResources().getString(R.string.SIGNUP_ERROR_PASSWORD_LEN),Toast.LENGTH_LONG).show();
            }catch (NullPasswordException e) { // Catch all exceptions you're interested to handle
                Toast.makeText(getContext(),getResources().getString(R.string.SIGNUP_ERROR_PASSWORD_NULL),Toast.LENGTH_LONG).show();
            }catch (UsernameIsNullException e) { // Catch all exceptions you're interested to handle
                Toast.makeText(getContext(),getResources().getString(R.string.SIGNUP_ERROR_USERNAME_NULL),Toast.LENGTH_LONG).show();
            }catch (InvalidUsernameFormatException e) { // Catch all exceptions you're interested to handle
                Toast.makeText(getContext(),getResources().getString(R.string.SIGNUP_ERROR_USERNAME),Toast.LENGTH_LONG).show();
            }catch (InvalidUsernameLengthException e) { // Catch all exceptions you're interested to handle
                Toast.makeText(getContext(),getResources().getString(R.string.SIGNUP_ERROR_USERNAME_LEN),Toast.LENGTH_LONG).show();
            }catch (Exception e) { // Catch all exceptions you're interested to handle
                Toast.makeText(getContext(),getResources().getString(R.string.SIGNUP_ERROR_FIELDS),Toast.LENGTH_LONG).show();
            }
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
        Estudios.setAdapter(adapter);

        //comprobamos logueo
        if (nombreDeUsuario == null) {
            //mostrar pantalla de creacion


            return view;
        } else {
            //mostrar pantalla rellena de datos qque hay que obtener de la db
            Log.d("usuario checkeado", "esta logeado el usuario " + nombreDeUsuario);
            LoginChecker loginChecker = new LoginChecker();
            loginChecker.Login(nombreDeUsuario, claveDeUsuario, this);
            return view;
        }

    }

    public void ponerNombresEnCasilleros(UserObject userObject) {
        Nombre.setText(userObject.getNombre());
        Usuario.setText(userObject.getUsuario());
        Usuario.setEnabled(false);
        //se muestra la clave
        Security encriptador=new Security();
        String claveDesencriptada=encriptador.decrypt(userObject.getContrasena());
        Password.setText(claveDesencriptada);
        Email.setText(userObject.getEmail());
        Nacimiento.setText(userObject.getFechaNacimiento());
        ListaPaises.setText(userObject.getPaisNacimiento());
        Pais = userObject.getPaisNacimiento();
        Ciudad.setText(userObject.getCiudadNacimiento());
        if (userObject.getSexo() == "Hombre") Hombre.setChecked(true);
        else Mujer.setChecked(true);
        if (userObject.getCambioTrabajo() == "Si") Si.setChecked(true);
        else No.setChecked(true);
        if (userObject.getZonaResidencia() == "Rural") Rural.setChecked(true);
        else Urbana.setChecked(true);

        switch (userObject.getNivelDeEstudios()) {
            case "Primaria":
                Estudios.setSelection(1);
                break;
            case "Secundaria":
                Estudios.setSelection(2);
                break;
            case "Bachiller":
                Estudios.setSelection(3);
                break;
            case "Ciclo Formativo":
                Estudios.setSelection(4);
                break;
            case "Carrera Universitaria":
                Estudios.setSelection(5);
                break;
            case "Doctorado":
                Estudios.setSelection(6);
                break;
        }
        Editar="Si";

    }

}
