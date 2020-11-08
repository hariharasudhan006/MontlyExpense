package com.example.monthlyexpense;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SignUp extends AppCompatActivity {
    private EditText username,password,phno;
    private Utility utility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        utility = new Utility();
        username = findViewById(R.id.signup_username);
        password = findViewById(R.id.signup_password);
        phno = findViewById(R.id.signup_phoneNo);
        Button proceed = findViewById(R.id.signup_proceed_bt);
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(username.getText().toString().isEmpty() || password.getText().toString().isEmpty() || phno.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Kindly enter the details",Toast.LENGTH_SHORT).show();
                }
                else if(phno.getText().toString().length()!=10){
                    utility.makeToast("Invalid phone number");
                    phno.setText("");
                }
                else{
                    if(utility.existsInDataBase(username.getText().toString())){
                        utility.makeToast("user already exists choose another one or sign in");
                    }
                    else{
                        User user = new User();
                        user.name = username.getText().toString().trim();
                        user.phoneNo = phno.getText().toString().trim();
                        user.setPassword(password.getText().toString().trim());
                        utility.saveToDateBase(user);
                        utility.putCurrentUsername(username.getText().toString());
                        finish();
                    }

                }

            }
        });
    }


    public void moveToSignIn(View view) {
        Intent intent = new Intent(SignUp.this,SignIn.class);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        finish();
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}