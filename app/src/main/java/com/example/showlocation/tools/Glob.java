package com.example.showlocation.tools;


import android.content.res.Resources;

import android.support.v7.app.AppCompatActivity;




public class Glob {



    public static int convertDpToPx(Resources res, int id) {
        return (int) res.getDimension(id);
    }

    public static int convertDpToPx(AppCompatActivity activity, int id) {
        return (int) activity.getResources().getDimension(id);
    }



}
