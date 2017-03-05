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
import android.widget.TextView;

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.domain.LoginAndUserData.LoginChecker;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import id.arieridwan.lib.PageLoader;


public class LoginFragment extends Fragment {
    @Nullable
    @BindView(R.id.UsuarioLogin)
    EditText UsuarioLogin;
    @Nullable
    @BindView(R.id.Password)
    EditText Password;
    @Nullable
    @BindView(R.id.LoginButton)
    Button LoginButton;
    @Nullable
    @BindView(R.id.CreateAccount)
    Button CreateAccount;
    @Nullable
    @BindView(R.id.ForgotPassword)
    TextView ForgotPassword;
    @Nullable
    @BindView(R.id.LoginFalse)
    TextView LoginFalse;
    @Nullable
    @BindView(R.id.pageloader)
    public
    PageLoader pageLoader;

    @Nullable
    @Optional
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
    @Nullable
    @Optional
    @OnClick(R.id.ForgotPassword)
    public void ForgotPassword() {
        // llamar al adaptador para cambiar la vista
    }
    @Nullable
    @Optional
    @OnClick(R.id.CreateAccount)public void AbrirFragmentNewUser(){
        FragmentTransaction xfragmentTransaction = this.getFragmentManager().beginTransaction();
        xfragmentTransaction.replace(R.id.container_view,new CreateUserFragment()).commit();

    }


    //deslogueo
    @Nullable
    @BindView(R.id.Usuario_actual)
    TextView UsuarioActual;
    @Nullable
    @BindView(R.id.Logout)
    Button Logout;
    @Nullable
    @BindView(R.id.Edit)
    Button Edit;
    @Nullable
    @Optional
    @OnClick(R.id.Logout)
    public void Logout(){
        //se limpian las preferencias
        Context context = getActivity();
        SharedPreferences sharedPref = context.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear().commit();
        //se muestra la pantalla de login
        FragmentTransaction xfragmentTransaction = getFragmentManager().beginTransaction();
        xfragmentTransaction.replace(R.id.container_view,new LoginFragment()).commit();
    }

    @Optional
    @Nullable
    @OnClick (R.id.Edit)
    public void EditarUsuario(){
        //ir a la pantalla de crear usuario, la cual se auto rellenará con los datos de este
        FragmentTransaction xfragmentTransaction = getFragmentManager().beginTransaction();
        xfragmentTransaction.replace(R.id.container_view,new CreateUserFragment()).commit();
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
        String nombreDeUsuario = sharedPref.getString(getString(R.string.PREFERENCES_USER),null);
        if(nombreDeUsuario==null){
            //mostrar pantalla de registro
            View view = inflater.inflate(R.layout.login,null);
            ButterKnife.bind(this, view);
            return view;
        }else{
            //mostrar pantalla de deslogueo
            Log.d("usuario checkeado", "esta logeado el usuario "+ nombreDeUsuario);
            View view = inflater.inflate(R.layout.user_logout_and_edit_button,null);
            ButterKnife.bind(this, view);
            UsuarioActual.setText(getString(R.string.LOGOUT_WELCOME)+" "+nombreDeUsuario+"!");
            return view;
        }

    }

    public void showIncorrectPassword(){
        LoginFalse.setText(getResources().getString(R.string.LOGIN_BAD_LOGIN));
        LoginFalse.setTextColor(getResources().getColor(R.color.redApp));
    }
    public void errorServer(){
        LoginFalse.setText(getResources().getString(R.string.LOGIN_ERROR_SERVER));
        LoginFalse.setTextColor(getResources().getColor(R.color.redApp));
    }

}
