package com.unir.grupo2.myzeancoach.domain;

import android.content.Context;
import android.content.SharedPreferences;

import com.unir.grupo2.myzeancoach.R;

/**
 * Created by Cesar on 22/03/2017.
 */

public class Utils {

    public static String getUserFromPreference(Context context){

        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        return sharedPref.getString(context.getString(R.string.PREFERENCES_USER),null);

    }

}
