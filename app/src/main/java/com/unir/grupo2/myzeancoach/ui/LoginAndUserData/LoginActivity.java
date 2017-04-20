package com.unir.grupo2.myzeancoach.ui.LoginAndUserData;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.ndk.CrashlyticsNdk;
import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.domain.LoginAndUserData.LoginChecker;
import com.unir.grupo2.myzeancoach.domain.utils.Utils;
import com.unir.grupo2.myzeancoach.ui.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import io.fabric.sdk.android.Fabric;

/**
 * Created by Cesar on 26/03/2017.
 */

public class LoginActivity extends AppCompatActivity {

    static final int CREATE_USER_REQUEST = 1;

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
    public ScrollView loginLayout;
    @Nullable
    @BindView(R.id.loading_layout)
    LinearLayout loadingLayout;
    @Nullable
    @BindView(R.id.error_layout)
    LinearLayout errorLayout;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics(), new CrashlyticsNdk());
        setContentView(R.layout.login);
        ButterKnife.bind(this);

        //hay que comprobar en las user preferences si el usuario esta logueado o no
        // si lo esta hay que deslogueear o editar datos
        // sino mostrar la pantalla de login
        String token = Utils.getTokenFromPreference(getApplicationContext());
        String user = Utils.getUserFromPreference(getApplicationContext());

        if (token != null) {
            //Show mainActiviy directly
            launchMainActivity();
        }
    }

    @Nullable
    @Optional
    @OnClick(R.id.LoginButton)
    public void login() {
        closeKeyboard();
        //checkear campos rellenos llamando al controlador
        LoginChecker loginChecker = new LoginChecker();
        if (loginChecker.UserAndPassWordFilled(usuarioLogin.getText().toString(), password.getText().toString())) {
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
        Intent intent = new Intent(this, RecoveryPasswordActivity.class);
        startActivity(intent);
    }

    @Nullable
    @Optional
    @OnClick(R.id.CreateAccount)
    public void openNewUserFragment() {
        Intent intent = new Intent(this, CreateUserActivity.class);
        startActivityForResult(intent, CREATE_USER_REQUEST);
    }

    //deslogueo
    @Nullable
    @BindView(R.id.Usuario_actual)
    TextView usuarioActual;

    @Nullable
    @BindView(R.id.Edit)
    Button edit;

    public void showIncorrectPassword() {
        loginFalse.setText(getResources().getString(R.string.LOGIN_BAD_LOGIN));
        loginFalse.setTextColor(getResources().getColor(R.color.redApp));
    }
//FINALLY NOT IMPLEMENTED //

   /* public void errorServer() {
        loginFalse.setText(getResources().getString(R.string.LOGIN_ERROR_SERVER));
        loginFalse.setTextColor(getResources().getColor(R.color.redApp));
    }

    public void passEmailSend() {
        Toast.makeText(this, getResources().getString(R.string.LOGIN_EMAIL_SEND), Toast.LENGTH_LONG).show();
    }*/

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

    public void closeKeyboard() {
        // Check if no view has focus:
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void launchMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("FROM_LOGIN", true);
        startActivity(intent);
        finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CREATE_USER_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    String userName = data.getStringExtra("USER_NAME");
                    usuarioLogin.setText(userName);
                }
            }
        }
    }

}

