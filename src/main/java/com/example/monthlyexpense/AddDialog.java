package com.example.monthlyexpense;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.text.DateFormat;
import java.util.Calendar;

public class AddDialog extends AppCompatDialogFragment {
    private EditText expenseName, expensePrice;
    private String  expense, price, date;
    private Utility utility;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_dialog, null);
        utility = new Utility();
        expenseName = view.findViewById(R.id.expenseName);
        expensePrice = view.findViewById(R.id.expensePrice);
        builder.setView(view)
                .setTitle("Add Expense")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (expenseName.getText().toString().isEmpty() || expensePrice.getText().toString().isEmpty()) {
                            Toast.makeText(getActivity(), "No input given, So closing the dialog box", Toast.LENGTH_LONG).show();
                        } else {
                            expense = expenseName.getText().toString();
                            price = expensePrice.getText().toString();
                            date = DateFormat.getDateInstance(DateFormat.SHORT).format(Calendar.getInstance().getTime());
                            putInServer();
                        }
                    }
                });
        return builder.create();
    }
    public void putInServer(){
        User user;
        user = utility.getFromDataBase();
        user.expense = user.expense+ expense+"\n";
        user.price = user.price+ price+"\n";
        user.date = user.date+ date+"\n";
        user.TotalExpense = String.valueOf(Integer.parseInt(user.TotalExpense) + Integer.parseInt(expense));
        utility.saveToDateBase(user);
        utility.makeToast("Added");
    }
}
