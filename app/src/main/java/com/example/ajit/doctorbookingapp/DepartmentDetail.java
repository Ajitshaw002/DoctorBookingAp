package com.example.ajit.doctorbookingapp;

import android.app.Dialog;
import android.app.FragmentTransaction;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DepartmentDetail extends AppCompatActivity implements View.OnClickListener{
        Spinner spinner;
    EditText editText;
    String url="";
    Dialog dialog;
    ArrayList<Setter_getter> list=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_detail);
        spinner=(Spinner)findViewById(R.id.spinnerDepartment);
        spinner.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);


         editText =(EditText)findViewById(R.id.ed_selectdate);
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                {
                    DataDialog dataDialog=new DataDialog(v);
                    FragmentTransaction ft=getFragmentManager().beginTransaction();
                    dataDialog.show(ft,"DataPicker");
                }
            }
        });
        LoadDataFromUrl();

    }

    @Override
    public void onClick(View view) {
//        if(view == editText)
//        {
//
//
//
//            }
    }
    public class CustomAdapter extends BaseAdapter
    {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            LayoutInflater inf=getLayoutInflater();
            View v=inf.inflate(R.layout.spinner_row,viewGroup,false);
            TextView tv=(TextView)v.findViewById(R.id.txt_list);
            Setter_getter obj=new Setter_getter();
            obj=list.get(i);
            tv.setText(obj.getDepartmentname());
            //  tv.setText(departname[i]);

            return v;

        }
    }

    public void LoadDataFromUrl()
    {
         //url="http://220.225.80.177/drbookingapp/bookingapp.asmx/GetDepartment?";
        url=Appdata.url_host+"GetDepartment?";
       // dialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jobjj = new JSONObject(response);
                            Log.e("response", "@@@@@@" + jobjj);

                            JSONArray jar=jobjj.getJSONArray("DepartmentDetails");
                            list=new ArrayList<Setter_getter>();
                            for(int i=0;i<jar.length();i++)
                            {
                                JSONObject job=jar.getJSONObject(i);
                                String deptId=job.getString("departmentid");
                                String departName=job.getString("departmentname");
                                Setter_getter obj=new Setter_getter();
                                obj.setdepatmentname(departName);
                                list.add(obj);
                            }
                            spinner.setAdapter(new CustomAdapter());


//                            dialog.cancel();
//                            Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();




                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            //dialog.cancel();
                            Toast.makeText(getApplicationContext(), "Volley Response Error", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               // dialog.cancel();
                Toast.makeText(getApplicationContext(), "VolleyError" + error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();


                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }



}

