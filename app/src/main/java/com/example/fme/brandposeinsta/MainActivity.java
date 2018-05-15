package com.example.fme.brandposeinsta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fme.brandposeinsta.CustomViews.AuthenticationDialog;
import com.example.fme.brandposeinsta.Interfaces.AuthenticationListener;

public class MainActivity extends AppCompatActivity implements AuthenticationListener{
    DatabaseHelper db;
    EditText e1, e2, e3;
    Button b1, b2;
    private AuthenticationDialog auth_dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DatabaseHelper(this);
        e1 = (EditText) findViewById(R.id.email);
        e2 = (EditText) findViewById(R.id.pass);
        e3 = (EditText) findViewById(R.id.cpass);
        b1 = (Button) findViewById(R.id.register);
        b2 = (Button) findViewById(R.id.button3);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1 = e1.getText().toString();
                String s2 = e2.getText().toString();
                String s3 = e3.getText().toString();
                if (s1.equals("") || s2.equals("") || s3.equals("")){
                    Toast.makeText(getApplicationContext(),"Fields are empty", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (s2.equals(s3)){
                        Boolean chkemail = db.chkemail(s1);
                        if (chkemail == true){
                            Boolean insert = db.insert(s1,s2);
                            if (insert == true){
                                Toast.makeText(getApplicationContext(),"Registered Successfully", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Email already exists", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                    Toast.makeText(getApplicationContext(),"Password do not match", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });
    }

    @Override
    public void onCodeReceived(String auth_token) {
        if (auth_token == null)
            return;
        //use the token for further

    }

    public void after_click_login(View view) {
        auth_dialog = new AuthenticationDialog(this,this);
        auth_dialog.setCancelable(true);
        auth_dialog.show();
    }
}
