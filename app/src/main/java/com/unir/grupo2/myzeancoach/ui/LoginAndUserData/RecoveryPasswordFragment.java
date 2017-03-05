package com.unir.grupo2.myzeancoach.ui.LoginAndUserData;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.domain.LoginAndUserData.RecoveryPasswordServer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.arieridwan.lib.PageLoader;


public class RecoveryPasswordFragment extends Fragment {

    @BindView(R.id.Usuario)
    EditText Usuario;
    @BindView(R.id.okButton)
    Button okButton;
    @Nullable
    @BindView(R.id.pageloader)
    public PageLoader pageLoader;

    @OnClick(R.id.okButton)

    public void recoveryPass(){
        String User=Usuario.getText().toString();
        if (User.length()>0){
            RecoveryPasswordServer recoveryPasswordServer=new RecoveryPasswordServer();
            recoveryPasswordServer.RecoveryPass(User,this);
        }else Toast.makeText(getContext(),getResources().getString(R.string.SIGNUP_ERROR_USERNAME_LEN),Toast.LENGTH_LONG).show();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recovery_password,null);
        ButterKnife.bind(this, view);
        return view;
    }
    public void errorServer(){
        Toast.makeText(getContext(),getResources().getString(R.string.LOGIN_ERROR_SERVER),Toast.LENGTH_LONG).show();
    }
    public void userNotExits() {
        Toast.makeText(getContext(),getResources().getString(R.string.SIGNUP_USER_NOT_EXITS),Toast.LENGTH_LONG).show();
    }
    public void passEmailSend() {
        Toast.makeText(getContext(),getResources().getString(R.string.LOGIN_EMAIL_SEND),Toast.LENGTH_LONG).show();
    }

}
