package com.unir.grupo2.myzeancoach.ui.LoginAndUserData;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.domain.LoginAndUserData.RecoveryPasswordServer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class RecoveryPasswordFragment extends Fragment {

    @BindView(R.id.Usuario)
    EditText usuario;
    @BindView(R.id.okButton)
    Button okButton;
    @Nullable
    @BindView(R.id.recoveryLayout)
    public
    ScrollView recoveryLayout;
    @Nullable
    @BindView(R.id.loading_layout)
    LinearLayout loadingLayout;
    @Nullable
    @BindView(R.id.error_layout)
    LinearLayout errorLayout;

    @OnClick(R.id.okButton)

    public void recoveryPass(){
        String user=usuario.getText().toString();
        if (user.length()>0){
            RecoveryPasswordServer recoveryPasswordServer=new RecoveryPasswordServer();
            recoveryPasswordServer.recoveryPass(user, this);
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
    /**
     * Method used to show error view
     */
    public void showError() {
        recoveryLayout.setVisibility(View.GONE);
        loadingLayout.setVisibility(View.GONE);
        errorLayout.setVisibility(View.VISIBLE);
    }

    /**
     * Method used to show the loading view
     */
    public void showLoading() {
        loadingLayout.setVisibility(View.VISIBLE);
        recoveryLayout.setVisibility(View.GONE);
        errorLayout.setVisibility(View.GONE);
    }

    /**
     * Method used to show the listView
     */
    public void showContent() {
        recoveryLayout.setVisibility(View.VISIBLE);
        loadingLayout.setVisibility(View.GONE);
        errorLayout.setVisibility(View.GONE);
    }

}
