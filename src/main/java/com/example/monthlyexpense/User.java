package com.example.monthlyexpense;

public class User {
    public String name = "",expense = "",price = "",date = "",TotalExpense = "0",phoneNo = "";
    private String password = "";
    public User(){
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String inPass){
        password = inPass;
    }
    public void assign(User other){
        this.name = other.name;
        this.password = other.password;
        this.expense = other.expense;
        this.price = other.price;
        this.date = other.date;
    }
}
