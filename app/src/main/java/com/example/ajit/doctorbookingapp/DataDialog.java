package com.example.ajit.doctorbookingapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

/**
 * Created by Android-user on 06-Apr-17.
 */

public class DataDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    EditText txtData;
    public DataDialog(View view)
    {
        txtData=(EditText)view;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year=c.get(Calendar.YEAR);
        int month=c.get(Calendar.MONTH);
        int day =c.get(Calendar.DAY_OF_MONTH);
        return  new DatePickerDialog(getActivity(),this,year,month,day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
String date=dayOfMonth+"-"+(month+1)+"-"+year;
        txtData.setText(date);
    }
}
