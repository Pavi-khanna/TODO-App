package com.example.pavikhanna.databaseprac;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Pavi Khanna on 2/17/2018.
 */

public class ExpenseAdapter extends BaseAdapter {

    Context context;
    ArrayList<Expense> mExpenses;

    public ExpenseAdapter(Context context, ArrayList<Expense> mExpenses) {
        this.context = context;
        this.mExpenses = mExpenses;
    }

    @Override
    public int getCount() {
        return mExpenses.size();
    }

    @Override
    public Expense getItem(int i) {
        return mExpenses.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View output= view;
        if(output==null)
        {
            output = inflater.inflate(R.layout.expense_row,viewGroup,false);
            ExpenseViewHolder holder = new ExpenseViewHolder(output);
            output.setTag(holder);
        }

        ExpenseViewHolder holder= (ExpenseViewHolder) output.getTag();
        Expense expense = getItem(i);

        holder.titleTextView.setText(expense.getTitle());
        holder.amountTextView.setText(expense.getAmountString());
        holder.descTextView.setText(expense.getDescription());
        return output;

    }


    class ExpenseViewHolder {

        TextView titleTextView;
        TextView descTextView;
        TextView amountTextView;

        ExpenseViewHolder(View rowLayout) {
            titleTextView = rowLayout.findViewById(R.id.title);
            descTextView = rowLayout.findViewById(R.id.description);
            amountTextView = rowLayout.findViewById(R.id.amount);
        }
    }

}
