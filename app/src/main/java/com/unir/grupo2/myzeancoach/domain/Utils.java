package com.unir.grupo2.myzeancoach.domain;

import android.content.Context;
import android.content.SharedPreferences;

import com.unir.grupo2.myzeancoach.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Cesar on 22/03/2017.
 */

public class Utils {

    public static void saveUserInPreference(Context context, String token, String username){
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(context.getString(R.string.PREFERENCES_TOKEN), token);
        editor.putString(context.getString(R.string.PREFERENCES_USER), username);
        editor.commit();
    }

    public static String getUserFromPreference(Context context){

        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        return sharedPref.getString(context.getString(R.string.PREFERENCES_USER),null);
    }

    public static String getTokenFromPreference(Context context){

        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        return sharedPref.getString(context.getString(R.string.PREFERENCES_TOKEN),null);
    }

    public static String dateFormat(String dateString){
        SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat output = new SimpleDateFormat("dd-MM-yyyy HH:mm");

        try {

            Date date = sdf .parse(dateString);

            dateString = output.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dateString;
    }

}
