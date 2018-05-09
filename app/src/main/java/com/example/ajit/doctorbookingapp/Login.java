package com.example.ajit.doctorbookingapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class Login extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener{
    TextView tv_register;
    Button btn_login;
    EditText ed_username, ed_pass;
    Dialog dialog;
    Toolbar toolbar;
    InternetCheking internetCheking;

    String url_login = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tv_register.setPaintFlags(tv_register.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tv_register.setOnClickListener(this);
        ed_username = (EditText) findViewById(R.id.ed_username);
        ed_pass = (EditText) findViewById(R.id.ed_password);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
        dialog = new Dialog(this);
        dialog.setTitle("Please Wait!!!");
        dialog.setCancelable(false);

       toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer,toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {
        if (view == tv_register) {
            Intent i = new Intent(getApplicationContext(), Registration.class);
            startActivity(i);
        }
        if (view == btn_login) {
            if (ed_username.getText().toString().isEmpty() && ed_pass.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), " Please Enter Username & password ", Toast.LENGTH_SHORT).show();
            } else {
                LoadDataFromUrl();
            }

        }
    }

    public void LoadDataFromUrl() {
        //url_login ="http://220.225.80.177/drbookingapp/bookingapp.asmx/UserLogin?username=ed_username.getText().toString()&pwd=ed_pass.getText().toString()";
        //Log.i("value",url_login);
        url_login = Appdata.url_host + "UserLogin?";
        Log.i("value", url_login);
        dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_login,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jobjj = new JSONObject(response);
                            Log.e("response", "@@@@@@" + jobjj);
                            String message = jobjj.getString("Message");
                            String sucess = jobjj.getString("Sucess");
                            if (sucess.equals("1")) {
                                JSONObject jsonObject = jobjj.getJSONObject("UserDetails");
//                                String userid = jsonObject.getString("userid");
//                                String fname = jsonObject.getString("fname");
//                                String lname = jsonObject.getString("lname");
//                                String username = jsonObject.getString("username");
//
//                                String passwords = jsonObject.getString("pwd");
//                                String adress = jsonObject.getString("address");
//                                String phnnumb = jsonObject.getString("phoneno");
//                                String email = jsonObject.getString("email");
                                dialog.cancel();
                                Toast.makeText(getApplicationContext(), "login sucess!!", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getApplicationContext(), DepartmentDetail.class);
                                startActivity(i);
                            } else {
                                dialog.cancel();
                                Toast.makeText(getApplicationContext(), "Invalid username and password!!", Toast.LENGTH_SHORT).show();

                            }

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

                params.put("username", ed_username.getText().toString());
                params.put("Pwd", ed_pass.getText().toString());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    public void AlertDial(final String message) {
        AlertDialog.Builder build = new AlertDialog.Builder(Login.this);
        build.setMessage(message);
        build.setCancelable(false);
        build.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                internetCheking=new InternetCheking(Login.this);
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
