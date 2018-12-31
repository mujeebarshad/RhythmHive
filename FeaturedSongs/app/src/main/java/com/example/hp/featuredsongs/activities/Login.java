package com.example.hp.featuredsongs.activities;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.hp.featuredsongs.R;
import com.example.hp.featuredsongs.databinding.NavHeaderBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Login extends AppCompatActivity {
    EditText txtname;
    EditText txtpassword;
    Button btnLogin;
    private Session session; //Session variable


    AlertDialog.Builder builder;
    public void registerBtn(View view){
        Intent intent = new Intent(this,Signup.class);
        ActivityOptions options = ActivityOptions.makeCustomAnimation(this, R.anim.slideright,R.anim.slideleft);
        startActivity(intent, options.toBundle());
    }
    public  void learnMore(View view) {

        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(Login.this);
        View mView = getLayoutInflater().inflate(R.layout.learn_more_dialog, null);
        Button close = (Button) mView.findViewById(R.id.btnClose);
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();
        close.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtname = (EditText)findViewById(R.id.txtname);
        txtpassword = (EditText)findViewById(R.id.txtpassword);
        btnLogin=(Button) findViewById(R.id.btnlogin);


        // Volley Login

        final RequestQueue queue = Volley.newRequestQueue(this);

        final String req_url = "http://192.168.100.8:3000/login";

        queue.start();

        builder = new AlertDialog.Builder(Login.this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> params = new HashMap<String,String>();

                params.put("username", String.valueOf(txtname.getText()));
                params.put("password", String.valueOf(txtpassword.getText()));
                JsonObjectRequest jsObjRequest = new
                        JsonObjectRequest(Request.Method.POST,
                        req_url,
                        new JSONObject(params),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    builder.setTitle("Login");
                                    builder.setMessage(response.getString("message"));
                                    AlertDialog alertDialog = builder.create();
                                    alertDialog.show();
                                    if(response.getString("code").equals("true")) {
                                        // Sessions
                                        session = new Session(getApplicationContext());
                                        session.setusename(txtname.getText().toString());
                                        //Global = txtname.getText().toString();
                                        // /Sessions
                                        Intent i = new Intent (getApplicationContext(),MainActivity.class);
                                        startActivity(i);
                                        finish();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        builder.setTitle("Login");
                        builder.setMessage("Error in Login!");
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                });
                queue.add(jsObjRequest);
            }
        });


        // /Volley Login
    }
}
