package com.example.monthlyexpense.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.monthlyexpense.HomeActivity;
import com.example.monthlyexpense.MainActivity;
import com.example.monthlyexpense.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.util.Scanner;

public class HomeFragment extends Fragment  {
    public View root;
    private TextView totalExpense;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
         root = inflater.inflate(R.layout.fragment_home, container, false);
         totalExpense = root.findViewById(R.id.totalexpense);
        HomeActivity.signout.show();
        HomeActivity.fab.show();
        return root;
    }

}
