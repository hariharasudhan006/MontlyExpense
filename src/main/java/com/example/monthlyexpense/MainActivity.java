package com.example.monthlyexpense;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    public static Context appContext;
    public static User currentuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        currentuser = new User();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                appContext = getApplicationContext();

                    Intent intent = new Intent(MainActivity.this,HomeActivity.class);
                    startActivity(intent);
            }
        },2000);
    }
    public static Context getAppContext(){
        return appContext;
    }
    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

}