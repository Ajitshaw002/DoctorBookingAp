package com.example.ajit.doctorbookingapp;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by Ajit on 19-Sep-17.
 */

public class InternetCheking {
    private Context context;
    InternetCheking(Context context)
    {
        this.context=context;
    }
    public  boolean isInternetOn() {
        ConnectivityManager comm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (comm.getActiveNetworkInfo() != null && comm.getActiveNetworkInfo().isConnected() != false && comm.getActiveNetworkInfo().isAvailable() != false) {
            return true;
        } else {
            return false;
        }

    }
}
