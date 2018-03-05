package com.example.pavikhanna.databaseprac;

import java.util.ArrayList;

/**
 * Created by Pavi Khanna on 2/17/2018.
 */

public class Expense {

    private String title;
    private String description;
    private int amount;
    private int id;

    public Expense(String title, String description, int amount) {
        this.title = title;
        this.description = description;
        this.amount = amount;

        this.id = -1;
    }

    public Expense(String title, String description, int amount, int id) {
        this.title = title;
        this.description = description;
        this.amount = amount;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getAmountString(){
        return amount + " ₹" ;
    }

    static ArrayList<Expense> getDummyExpenses(int size){
        ArrayList<Expense> expenses = new ArrayList<>();
        for(int i = 1;i<=size;i++){
            Expense expense = new Expense("Title " + i,"Description "+i, i*100);
            expenses.add(expense);
        }
        return expenses;
    }

}
