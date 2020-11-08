package com.example.monthlyexpense;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class SignIn extends AppCompatActivity {
    private EditText username,password;
    private Button forgotpassword;
    private File currentuser = new File(MainActivity.getAppContext().getFilesDir(),"currentuser.dat");
    private Utility utility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        utility = new Utility();
        if(currentuser.exists()){
            finish();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        username = findViewById(R.id.sign_in_user_name);
        password = findViewById(R.id.sign_in_password);
        Button proceed = findViewById(R.id.signin_bt);
        forgotpassword = findViewById(R.id.forgot_password_bt);
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                forgotpassword.setVisibility(View.GONE);
                if(username.getText().toString().isEmpty() || password.getText().toString().isEmpty()){
                    utility.makeToast("Kindly enter the details");
                }
                else{
                    if(!utility.existsInDataBase(username.getText().toString())){
                        utility.makeToast("user doesn't exists kindly create an account");
                    }
                    else{
                        utility.putCurrentUsername(username.getText().toString());
                        if(utility.checkPassword(password.getText().toString())){
                            utility.makeToast("Signed in as"+username.getText().toString());
                            finish();
                        }
                        else{
                            utility.makeToast("Kindly check your password");
                            utility.deleteCurrentUserFile();
                            password.setText("");
                        }
                    }
                }

            }
        });
        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignIn.this,ForgotPassword.class);
                startActivityForResult(intent,1);
            }
        });
    }

    public void moveToSignup(View view) {
        Intent intent = new Intent(SignIn.this,SignUp.class);
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