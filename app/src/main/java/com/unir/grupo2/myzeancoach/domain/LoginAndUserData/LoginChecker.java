package com.unir.grupo2.myzeancoach.domain.LoginAndUserData;

import com.unir.grupo2.myzeancoach.data.UserData.UserData;

/**
 * Created by andres on 28/02/2017.
 */

public class LoginChecker {
    public static UserData userData;
    public boolean UserAndPassWordFilled(String User, String Password){
        if (User.length()==0 || Password.length()==0){
            return false;
        }else {
            return true;
        }
    }
    public boolean Login(String User, String Password){
        //encriptar password
        //llamar a servidor

        return true;

    }
}
