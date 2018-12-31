package com.example.hp.featuredsongs.activities;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.hp.featuredsongs.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Signup extends AppCompatActivity {
    EditText txtname;
    EditText txtuname;
    EditText txtmail;
    EditText txtpass;
    EditText txtcpass;
    Button btnRegister;

    AlertDialog.Builder builder;

    public void loginBtn(View biew){
        Intent intent = new Intent(this,Login.class);
        ActivityOptions options = ActivityOptions.makeCustomAnimation(this, R.anim.slideright,R.anim.slideleft);
        startActivity(intent, options.toBundle());
    }
    public  void learnMore(View view) {

        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(Signup.this);
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
        setContentView(R.layout.activity_signup);
        txtname = (EditText)findViewById(R.id.txtname);
        txtuname = (EditText)findViewById(R.id.txtuname);
        txtmail = (EditText)findViewById(R.id.txtmail);
        txtpass = (EditText)findViewById(R.id.txtpass);
        txtcpass = (EditText)findViewById(R.id.txtcpass);
        btnRegister=(Button) findViewById(R.id.btnSignUp);


        // Volley SignUP

        final RequestQueue queue = Volley.newRequestQueue(this);

        final String req_url = "http://172.16.7.96:3000";

        queue.start();

        builder = new AlertDialog.Builder(Signup.this);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> params = new HashMap<String,String>();

                params.put("name", String.valueOf(txtname.getText()));
                params.put("username", String.valueOf(txtuname.getText()));
                params.put("email", String.valueOf(txtmail.getText()));
                params.put("password", String.valueOf(txtpass.getText()));
                params.put("cpassword", String.valueOf(txtcpass.getText()));
                JsonObjectRequest jsObjRequest = new
                        JsonObjectRequest(Request.Method.POST,
                        req_url,
                        new JSONObject(params),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    builder.setTitle("Register User");
                                    builder.setMessage(response.getString("message"));
                                    AlertDialog alertDialog = builder.create();
                                    alertDialog.show();
                                    if(response.getString("code").equals("req_success")) {
                                        Intent i = new Intent (getApplicationContext(), Login.class);
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

                        builder.setTitle("Register User");
                        builder.setMessage("Error Registering User!");
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                });
                queue.add(jsObjRequest);
            }
        });


        // /Volley SignUP

    }
}
