package com.example.showlocation.tools;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckedTextView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arox on 2.11.2016.
 */

public class Glob {




    public static void setSpinGlob(List<?> list, Spinner spinner, int layout, int dropDownResourceLayout) {

        ArrayAdapter<?> spinnerArrayAdapter = new ArrayAdapter(spinner.getContext(), layout, new ArrayList<>(list)) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                AppCompatCheckedTextView tv = (AppCompatCheckedTextView) view;
                if (position == 0) {
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        spinnerArrayAdapter.setDropDownViewResource(dropDownResourceLayout);
        spinner.setAdapter(spinnerArrayAdapter);
    }

    public static void setSpinGlob(List<?> list, Spinner spinner, int layout) {

        ArrayAdapter<?> spinnerArrayAdapter = new ArrayAdapter(spinner.getContext(), layout, new ArrayList<>(list)) {
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    return false;
                } else {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                AppCompatCheckedTextView tv = (AppCompatCheckedTextView) view;
                if (position == 0) {
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);
    }

    public static int convertDpToPx(Resources res, int id) {
        return (int) res.getDimension(id);
    }

    public static int convertDpToPx(AppCompatActivity activity, int id) {
        return (int) activity.getResources().getDimension(id);
    }

    /**
     * Klavyeyi kapatan rutindir.
     *
     * @param editText Klavye Acik olan edittext
     */
    public static void keyboardClose(Activity activity, EditText editText) {
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)‌​;
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    public static boolean isInternetConnected(Context c) {
        ConnectivityManager cm = (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;

    }

    public static String fixedNumberForCall(String number) {
        if (number != null && !number.isEmpty()) {
            if (number.startsWith("444"))
                return number;
            else if (number.startsWith("5")) {
                return "0" + number;
            }
            return number;
        }
        return null;
    }

}
