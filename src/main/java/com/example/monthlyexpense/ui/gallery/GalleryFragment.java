package com.example.monthlyexpense.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.monthlyexpense.HomeActivity;
import com.example.monthlyexpense.R;
import com.example.monthlyexpense.User;
import com.example.monthlyexpense.Utility;

public class GalleryFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        HomeActivity.fab.hide();
        HomeActivity.signout.hide();
        Utility utility;
        utility = new Utility();
        User user;
        TextView expense = root.findViewById(R.id.expense);
        TextView price = root.findViewById(R.id.price);
        TextView date = root.findViewById(R.id.date);
        user = utility.getFromDataBase();
        if(user == null || user.expense.isEmpty()){
            utility.makeToast("Enter the expense first");
        }
        else{
            expense.setText(user.expense);
            price.setText(user.price);
            date.setText(user.date);
        }
        return root;
    }
}