package com.example.monthlyexpense;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileWriter;
import java.util.Random;

public class ForgotPassword extends AppCompatActivity {
    private TextView prompt;
    private EditText name;
    private Button send,verify;
    private Utility utility;
    private User user;
    private int otp;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        utility = new Utility();
        requestPermissions(new String[] {Manifest.permission.SEND_SMS},PackageManager.PERMISSION_GRANTED);
        final SmsManager smsManager = SmsManager.getDefault();
        prompt = findViewById(R.id.forgot_password_prompttxt);
        send = findViewById(R.id.forgot_password_sent_bt);
        name = findViewById(R.id.forgot_password_name);
        verify = findViewById(R.id.forgot_password_verify_bt);
        send.setText("Send OTP");
        name.setHint("Enter your name");
        prompt.setText("Enter your name");
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( name.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Kindly enter the Name",Toast.LENGTH_SHORT).show();
                }
                else{

                    if (!utility.existsInDataBase(name.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "Entered name Doesn't exists", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        utility.putCurrentUsername(name.getText().toString());
                        user = utility.getFromDataBase();
                        Random random = new Random();
                        otp = random.nextInt(9999);
                        String OTPmessage = "Hello " + user.name + ",\nYour OTP is " + String.valueOf(otp) + "\nThis message is sent by an app built for college project nothing important\n";
                        smsManager.sendTextMessage(user.phoneNo, null, OTPmessage, null, null);
                        Toast.makeText(getApplicationContext(), "OTP sent to your number", Toast.LENGTH_SHORT).show();
                        verify.setVisibility(View.VISIBLE);
                        send.setVisibility(View.GONE);
                        verify.setText("Verify");
                        name.setText("");
                        name.setHint("Enter your otp");
                        prompt.setText("Enter your OTP");
                    }
                }
            }
        });
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(otp == Integer.parseInt(name.getText().toString())){
                    Toast.makeText(getApplicationContext(),"verified successfully",Toast.LENGTH_SHORT).show();
                    File currentuser = new File(MainActivity.getAppContext().getFilesDir(),"currentuser.dat");
                    FileWriter writer;
                    try{
                        currentuser.createNewFile();
                        writer = new FileWriter(currentuser);
                        writer.write(user.name);
                        writer.flush();
                        writer.close();
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                    smsManager.sendTextMessage(user.phoneNo,null,"your password is reset to default(1234) kindly change it\nThis message is sent by an app built for college project nothing important",null,null);
                    user.setPassword("1234");
                    utility.saveToDataBase("password","1234");
                    Intent intent = new Intent(ForgotPassword.this,SignIn.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Incorrect OTP.Try again later",Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0] != PackageManager.PERMISSION_GRANTED){
            Toast.makeText(getApplicationContext(),"Kindly grant the permission",Toast.LENGTH_SHORT).show();
            requestPermissions(new String[] {Manifest.permission.SEND_SMS},PackageManager.PERMISSION_GRANTED);
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}