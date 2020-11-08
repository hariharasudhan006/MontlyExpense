package com.example.monthlyexpense;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.WriteAbortedException;

public class Utility {
    private File currenUserFile;
    private DatabaseReference reference;
    private User user;
    private boolean it;
    public Utility(){
        currenUserFile = new File(MainActivity.getAppContext().getFilesDir(),"currentuser.dat");
        it =true;
        reference = FirebaseDatabase.getInstance().getReference("Users");
        user = new User();
    }
    public String getCurrentUserName(){
        String returnUsername = " ";
        BufferedReader reader;
        try{
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(currenUserFile)));
            returnUsername = reader.readLine();
            reader.close();
        }
        catch (Exception e){
            returnUsername = "No UserName";
        }
        return returnUsername;
    }
    public void putCurrentUsername(String userName){
        FileWriter writer;
        try{
            if(!currenUserFile.exists()){
                currenUserFile.createNewFile();
            }
            writer = new FileWriter(currenUserFile);
            writer.write(userName+"\n");
            writer.flush();
            writer.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void deleteCurrentUserFile(){
        currenUserFile.delete();
    }
    public boolean saveToDateBase(User user){
        reference.child(getCurrentUserName()).setValue(user);
        return true;
    }
    public boolean saveToDataBase(String name,String data){
        reference.child(getCurrentUserName()).child(name).setValue(data);
        return true;
    }
    public boolean existsInDataBase(String child){
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                it = snapshot.child(getCurrentUserName()).exists();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return it;
    }
    public User getFromDataBase(){
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.child(getCurrentUserName()).getValue(User.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return user;
    }
    public String getFromDataBase(final String nameOfData){
        final String[] retData = {" "};
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                retData[0] = snapshot.child(getCurrentUserName()).child(nameOfData).getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return retData[0];
    }
    public void makeToast(String message){
        Toast.makeText(MainActivity.getAppContext(),message,Toast.LENGTH_SHORT).show();
    }
    public boolean checkPassword(String givenPassword){
        String password = getFromDataBase("password");
        return password.equals(givenPassword);
    }
}
