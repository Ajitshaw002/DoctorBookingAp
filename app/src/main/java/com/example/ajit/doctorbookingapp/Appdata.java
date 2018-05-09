package com.example.ajit.doctorbookingapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by Ajit on 16-Sep-17.
 */

public class Appdata {
    public static String url_host = "http://220.225.80.177/drbookingapp/bookingapp.asmx/";

    public static void alertDialogShow(Context context, String message)
    {
        final Login obj=new Login();
        AlertDialog.Builder build = new AlertDialog.Builder(context);
        build.setMessage(message);
        build.setCancelable(false);
        build.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


            }
        });
        build.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alrt = build.create();
        alrt.setTitle("Connectivity Problem");
        alrt.show();
    }
    }

