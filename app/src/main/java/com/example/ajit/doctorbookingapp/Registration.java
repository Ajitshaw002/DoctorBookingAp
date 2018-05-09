package com.example.ajit.doctorbookingapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Registration extends AppCompatActivity implements View.OnClickListener {
EditText ed_fname,ed_lname,ed_username,ed_password,ed_phn,ed_email,ed_address;
    Button btn_register;
    String url="";
    Dialog dialog;
    InternetCheking internetCheking;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ed_fname=(EditText)findViewById(R.id.ed_fname);
        ed_lname=(EditText)findViewById(R.id.ed_lname);
        ed_username=(EditText)findViewById(R.id.ed_usrname);
        ed_password=(EditText)findViewById(R.id.ed_password);
        ed_phn=(EditText)findViewById(R.id.ed_phnumbr);
        ed_email=(EditText)findViewById(R.id.ed_email);
        btn_register=(Button)findViewById(R.id.btn_register);
        ed_address=(EditText)findViewById(R.id.ed_address);
        btn_register.setOnClickListener(this);
        dialog=new Dialog(this);
        dialog.setTitle("Please Wait!!!");
        dialog.setCancelable(false);

    }
    @Override
    public void onClick(View view) {
        if(view==btn_register)
        {
            if(ed_username.getText().toString().isEmpty() && ed_password.getText().toString().isEmpty())
            {
                Toast.makeText(getApplicationContext(), "Invalid username and password", Toast.LENGTH_SHORT).show();
            }
            else
            {
                LoadDataFromUrl();
            }
        }

    }
    public void LoadDataFromUrl()
    {
       // url="http://220.225.80.177/drbookingapp/bookingapp.asmx/UserRegistration?";
        url=Appdata.url_host+"UserRegistration?";
         dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jobjj = new JSONObject(response);
                            Log.e("response", "@@@@@@" + jobjj);
                            String departname=jobjj.getString("DepartmentDetails");
                            String doctorsecudle=jobjj.getString("DoctorSchedule");
                            String userDetail=jobjj.getString("UserDetails");
                            String timeslotdetail=jobjj.getString("timeslotdetails");
                            String message=jobjj.getString("Message");
                            String d=jobjj.getString("d");
                            String sucess=jobjj.getString("Sucess");
                            String avilabesecedule=jobjj.getString("availableschedule");

                                dialog.cancel();
                                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                            Intent i= new Intent(getApplicationContext(),Login.class);
                            startActivity(i);




                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            dialog.cancel();
                            Toast.makeText(getApplicationContext(), "Volley Response Error", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.cancel();
                AlertDial(error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("fname", ed_fname.getText().toString());
                params.put("lname", ed_lname.getText().toString());
                params.put("username",ed_username.getText().toString());
                params.put("pwd",ed_password.getText().toString());
                params.put("address",ed_address.getText().toString());
                params.put("phoneno",ed_phn.getText().toString());
                params.put("email",ed_email.getText().toString());
                Log.i("value", String.valueOf(params));

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
    public void AlertDial(final String message) {
        AlertDialog.Builder build = new AlertDialog.Builder(Registration.this);
        build.setMessage(message);
        build.setCancelable(false);
        build.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                internetCheking=new InternetCheking(Registration.this);
                boolean value = internetCheking.isInternetOn();
                if (value == true) {
                    LoadDataFromUrl();
                }
                if (value == false) {
                    AlertDial(message);
                }
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
