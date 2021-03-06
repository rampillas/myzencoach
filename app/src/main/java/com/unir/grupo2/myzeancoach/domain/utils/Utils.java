package com.unir.grupo2.myzeancoach.domain.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.unir.grupo2.myzeancoach.Foreground;
import com.unir.grupo2.myzeancoach.R;
import com.unir.grupo2.myzeancoach.domain.Tracking.ConnectionUseCase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.RequestBody;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Created by Cesar on 22/03/2017.
 */

public class Utils {

    public static void saveUserInPreference(Context context, String token, String username) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(context.getString(R.string.PREFERENCES_TOKEN), encrypt(token));
        editor.putString(context.getString(R.string.PREFERENCES_USER), encrypt(username));
        long time = System.currentTimeMillis();
        long expiration_time = time + 604800000;
        editor.putString(context.getString(R.string.PREFERENCES_EXPIRATION), encrypt(String.valueOf(expiration_time)));
        editor.commit();
    }

    public static String getUserFromPreference(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        String user = sharedPref.getString(context.getString(R.string.PREFERENCES_USER), null);

        if (user != null) {
            return decrypt(user);
        } else {
            return null;
        }
    }

    public static String getExpirationTime(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        return decrypt(sharedPref.getString(context.getString(R.string.PREFERENCES_EXPIRATION), null));
    }

    public static String getTokenFromPreference(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        String token = sharedPref.getString(context.getString(R.string.PREFERENCES_TOKEN), null);

        if (token != null) {
            return decrypt(token);
        } else {
            return null;
        }
    }

    public static void saveLanguagePreference(String language, Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(context.getString(R.string.PREFERENCES_LANGUAGE), language);
        editor.commit();
    }

    public static String getLanguageFromPreference(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        return sharedPref.getString(context.getString(R.string.PREFERENCES_LANGUAGE), null);
    }

    public static String dateFormat(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat output = new SimpleDateFormat("dd-MM-yyyy HH:mm");

        try {
            Date date = sdf.parse(dateString);
            dateString = output.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateString;
    }

    public static String dateNowForBackend() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dateNow = new Date();
        return sdf.format(dateNow);
    }

    public static void closeSoftKeyboard(@NonNull Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static String covertUserNameBackend(String userName) {
        String[] parts = userName.trim().split("users/");
        return parts[1].substring(0, parts[1].length() - 1);
    }

    public static String getCategoryEvent(Context context, String category) {
        switch (category) {
            case "viajes":
                return context.getString(R.string.array_category_trip);
            case "tecnologia":
                return context.getString(R.string.array_category_technology);
            case "naturaleza":
                return context.getString(R.string.array_category_nature);
            case "deportes":
                return context.getString(R.string.array_category_sport);
            case "salud":
                return context.getString(R.string.array_category_health);
            case "naval":
                return context.getString(R.string.array_category_naval);
            case "trabajo":
                return context.getString(R.string.array_category_work);
            default:
                throw new IllegalArgumentException("Invalid category");
        }

    }

    public static void launchConnectionUseCase(Context context) {

        String userName = Utils.getUserFromPreference(context);
        String token = Constants.PRE_TOKEN + Utils.getTokenFromPreference(context);

        String text = "{\n" +
                "\"user\": \"" + userName + "\"\n" +
                "}";

        RequestBody body =
                RequestBody.create(MediaType.parse("text/plain"), text);

        new ConnectionUseCase(token, body).execute(new ConnectSubscriber());
    }

    public static boolean isNewConnection() {
        if (Foreground.get().isForeground()) {
            return false;
        } else {
            return true;
        }
    }

    public static String encrypt(String input) {
        // Simple encryption, not very strong!
        return Base64.encodeToString(input.getBytes(), Base64.DEFAULT);
    }

    public static String decrypt(String input) {
        return new String(Base64.decode(input, Base64.DEFAULT));
    }

}
