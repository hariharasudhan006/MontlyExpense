package com.example.monthlyexpense;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Account extends AppCompatActivity {
    private User user;
    private EditText editTextPassword,editTextnewPassword;
    private Utility utility;
    private Button change;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        utility = new Utility();
        TextView name = findViewById(R.id.name);
        TextView phoneno = findViewById(R.id.phoneNo);
        editTextPassword = findViewById(R.id.editTextPassword);
        change = findViewById(R.id.change_bt);
        editTextnewPassword = findViewById(R.id.editTextnewPassword);
        user = utility.getFromDataBase();
        name.setText(user.name);
        phoneno.setText(user.phoneNo);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editTextPassword.getText().toString().isEmpty() || editTextnewPassword.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Kindly enter the Password",Toast.LENGTH_SHORT).show();
                }
                else{
                    if(editTextPassword.getText().toString().equals(user.getPassword())){
                        utility.saveToDataBase("password",editTextnewPassword.getText().toString());
                        Toast.makeText(getApplicationContext(),"Password Changed Successfully",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Kindly check the Password",Toast.LENGTH_SHORT).show();
                        editTextPassword.setText("");
                        editTextPassword.setHint("Enter your current password");
                        editTextnewPassword.setText("");
                        editTextnewPassword.setHint("Enter your new password");
                    }
                }
            }
        });
    }

    public void changepassword(View view) {
        editTextPassword.setVisibility(View.VISIBLE);
        editTextnewPassword.setVisibility(View.VISIBLE);
        editTextPassword.setHint("Enter your current password");
        editTextnewPassword.setHint("Enter your new password");
        change.setVisibility(View.VISIBLE);
    }
}