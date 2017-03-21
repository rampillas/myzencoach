package com.unir.grupo2.myzeancoach.ui.LoginAndUserData;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.domain.LoginAndUserData.LoginChecker;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import okhttp3.MediaType;
import okhttp3.RequestBody;


public class LoginFragment extends Fragment {
    @Nullable
    @BindView(R.id.UsuarioLogin)
    EditText usuarioLogin;
    @Nullable
    @BindView(R.id.Password)
    EditText password;
    @Nullable
    @BindView(R.id.LoginButton)
    Button loginButton;
    @Nullable
    @BindView(R.id.CreateAccount)
    Button createAccount;
    @Nullable
    @BindView(R.id.ForgotPassword)
    TextView forgotPassword;
    @Nullable
    @BindView(R.id.LoginFalse)
    TextView loginFalse;
    @Nullable
    @BindView(R.id.loginLayout)
    public
    ScrollView loginLayout;
    @Nullable
    @BindView(R.id.loading_layout)
    LinearLayout loadingLayout;
    @Nullable
    @BindView(R.id.error_layout)
    LinearLayout errorLayout;

    @Nullable
    @Optional
    @OnClick(R.id.LoginButton)

    public void login() {

        //checkear campos rellenos llamando al controlador
        LoginChecker loginChecker = new LoginChecker();
        if (loginChecker.UserAndPassWordFilled(usuarioLogin.getText().toString(), password.getText().toString())) {

            String bodyString = "{\n" +
                    "\t\"client_id\": \"clientweb2231\",\n" +
                    "\t\"client_secret\": secretweb2231\n" +
                    "\t\"username\": " + usuarioLogin.getText().toString() + "\n" +
                    "\t\"password\": " + password.getText().toString() + "\n" +
                    "\t\"grant_type\": password\n" +
                    "\t\"scope\": read+write\n" +
                    "}";
            RequestBody body =
                    RequestBody.create(MediaType.parse("text/plain"), bodyString);

            loginChecker.Login("application/json", usuarioLogin.getText().toString(), password.getText().toString(), this);
        } else {
            loginFalse.setText(getResources().getString(R.string.LOGIN_BAD_LOGIN));
            loginFalse.setTextColor(getResources().getColor(R.color.redApp));
        }

    }

    @Nullable
    @Optional
    @OnClick(R.id.ForgotPassword)
    public void forgotPassword() {
        FragmentTransaction xfragmentTransaction = this.getFragmentManager().beginTransaction();
        xfragmentTransaction.replace(R.id.container_view, new RecoveryPasswordFragment()).commit();
    }

    @Nullable
    @Optional
    @OnClick(R.id.CreateAccount)
    public void openNewUserFragment() {
        FragmentTransaction xfragmentTransaction = this.getFragmentManager().beginTransaction();
        xfragmentTransaction.replace(R.id.container_view, new CreateUserFragment()).commit();

    }


    //deslogueo
    @Nullable
    @BindView(R.id.Usuario_actual)
    TextView usuarioActual;

    @Nullable
    @BindView(R.id.Logout)
    Button logout;
    @Nullable
    @BindView(R.id.Edit)
    Button edit;

    @Nullable
    @Optional
    @OnClick(R.id.Logout)
    public void logout() {
        //se limpian las preferencias
        Context context = getActivity();
        SharedPreferences sharedPref = context.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear().commit();
        //se muestra la pantalla de login
        FragmentTransaction xfragmentTransaction = getFragmentManager().beginTransaction();
        xfragmentTransaction.replace(R.id.container_view, new LoginFragment()).commit();
    }

    @Optional
    @Nullable
    @OnClick(R.id.Edit)
    public void editUser() {
        //ir a la pantalla de crear usuario, la cual se auto rellenará con los datos de este
        FragmentTransaction xfragmentTransaction = getFragmentManager().beginTransaction();
        xfragmentTransaction.replace(R.id.container_view, new CreateUserFragment()).commit();
    }


    //métodos del fragment
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //hay que comprobar en las user preferences si el usuario esta logueado o no
        // si lo esta hay que deslogueear o editar datos
        // sino mostrar la pantalla de login

        Context context = getActivity();
        SharedPreferences sharedPref = context.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String token = sharedPref.getString(getString(R.string.PREFERENCES_TOKEN), null);
        String user = sharedPref.getString(getString(R.string.PREFERENCES_USER), null);

        if (token == null) {
            //mostrar pantalla de registro
            View view = inflater.inflate(R.layout.login, null);
            ButterKnife.bind(this, view);
            return view;
        } else {
            //mostrar pantalla de deslogueo
            Log.d("usuario checkeado", "esta logeado el usuario ");
            View view = inflater.inflate(R.layout.remainders_layout, null);
            ButterKnife.bind(this, view);
            return view;
        }

    }

    public void showIncorrectPassword() {
        loginFalse.setText(getResources().getString(R.string.LOGIN_BAD_LOGIN));
        loginFalse.setTextColor(getResources().getColor(R.color.redApp));
    }

    public void errorServer() {
        loginFalse.setText(getResources().getString(R.string.LOGIN_ERROR_SERVER));
        loginFalse.setTextColor(getResources().getColor(R.color.redApp));
    }

    public void passEmailSend() {
        Toast.makeText(getContext(), getResources().getString(R.string.LOGIN_EMAIL_SEND), Toast.LENGTH_LONG).show();
    }

    /**
     * Method used to show error view
     */
    public void showError() {
        loginLayout.setVisibility(View.GONE);
        loadingLayout.setVisibility(View.GONE);
        errorLayout.setVisibility(View.VISIBLE);
    }

    /**
     * Method used to show the loading view
     */
    public void showLoading() {
        loadingLayout.setVisibility(View.VISIBLE);
        loginLayout.setVisibility(View.GONE);
        errorLayout.setVisibility(View.GONE);
    }

    /**
     * Method used to show the listView
     */
    public void showContent() {
        loginLayout.setVisibility(View.VISIBLE);
        loadingLayout.setVisibility(View.GONE);
        errorLayout.setVisibility(View.GONE);
    }
}
