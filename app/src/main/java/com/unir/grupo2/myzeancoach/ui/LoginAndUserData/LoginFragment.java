package com.unir.grupo2.myzeancoach.ui.LoginAndUserData;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.domain.LoginAndUserData.LoginChecker;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginFragment extends Fragment {

    @BindView(R.id.UsuarioLogin)
    EditText UsuarioLogin;
    @BindView(R.id.Password)
    EditText Password;
    @BindView(R.id.LoginButton)
    Button LoginButton;
    @BindView(R.id.CreateAccount)
    Button CreateAccount;
    @BindView(R.id.ForgotPassword)
    TextView ForgotPassword;
    @BindView(R.id.LoginFalse)
    TextView LoginFalse;

    @OnClick(R.id.LoginButton)
    public void Login() {

        //checkear campos rellenos llamando al controlador
        LoginChecker loginChecker = new LoginChecker();
        if (loginChecker.UserAndPassWordFilled(UsuarioLogin.getText().toString(), Password.getText().toString())) {
            loginChecker.Login(UsuarioLogin.getText().toString(), Password.getText().toString(),this);
        }else{
            LoginFalse.setText(getResources().getString(R.string.LOGIN_BAD_LOGIN));
            LoginFalse.setTextColor(getResources().getColor(R.color.redApp));
        }

    }

    @OnClick(R.id.ForgotPassword)
    public void ForgotPassword() {
        // llamar al adaptador para cambiar la vista
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login,null);
        ButterKnife.bind(this, view);
        return view;
    }

    public void showIncorrectPassword(){
        LoginFalse.setText(getResources().getString(R.string.LOGIN_BAD_LOGIN));
        LoginFalse.setTextColor(getResources().getColor(R.color.redApp));
    }
}
