package com.example.pavikhanna.databaseprac;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Expense> mExpenses;
    ExpenseAdapter mAdapter;
    ExpenseOpenHelper openHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.list_view);
        openHelper= new ExpenseOpenHelper(this);
        mExpenses= fetchDataFromDB();
        mAdapter = new ExpenseAdapter(this,mExpenses);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Expense expense = mExpenses.get(i);
                String title= expense.getTitle();
                String desc= expense.getDescription();
                int amount= expense.getAmount();

                Intent detailIntent = new Intent(MainActivity.this,DetailActivity.class);
                Bundle bundle= new Bundle();
                bundle.putInt(Constants.POSITION_KEY,i);
                bundle.putString(Constants.TITLE_KEY,title);
                bundle.putString(Constants.DESCRIPTION_KEY,desc);
                bundle.putInt(Constants.AMOUNT_KEY,amount);
                detailIntent.putExtras(bundle);
                startActivityForResult(detailIntent,Constants.DETAIL_ACTIVITY_REQUEST);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Delete Expense?");
                builder.setMessage("Are you sure you want to delete this expense? You can not undo it.");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mExpenses.remove(i);
                        mAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
                return true;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.add){
            Intent intent = new Intent(this,ExpenseFormActivity.class);
            startActivityForResult(intent,Constants.ADD_EXPENSE_REQUEST);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private ArrayList<Expense> fetchDataFromDB() {

        ArrayList<Expense> expenses= new ArrayList<>();

        SQLiteDatabase database= openHelper.getReadableDatabase();
        Cursor cursor = database.query(Contract.Expenses.TABLE_NAME,null,null,null,null,null,null);
        while (cursor.moveToNext())
        {
            String title = cursor.getString(cursor.getColumnIndex(Contract.Expenses.TITLE));
            String desc = cursor.getString(cursor.getColumnIndex(Contract.Expenses.DESCRIPTION));
            int amount = cursor.getInt(cursor.getColumnIndex(Contract.Expenses.AMOUNT));
            int id= cursor.getInt(cursor.getColumnIndex(Contract.Expenses.ID));

            Expense expense= new Expense(title,desc,amount,id);
            expenses.add(expense);

        }
        return expenses;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(data!=null)
        {
            Bundle bundle=data.getExtras();

            switch(requestCode)
            {
                case Constants.DETAIL_ACTIVITY_REQUEST:
                    if(resultCode==Constants.SAVE_SUCCESS_RESULT)
                    {
                       if(bundle!=null)
                       {
                           int position= bundle.getInt(Constants.POSITION_KEY,-1);
                           if(position>=0)
                           {
                               Expense expense = getExpenseFromBundle(bundle);
                               mExpenses.set(position,expense);
                               mAdapter.notifyDataSetChanged();
                           }
                       }
                    }
                    break;
                case Constants.ADD_EXPENSE_REQUEST:
                    if(resultCode==Constants.SAVE_SUCCESS_RESULT)
                    {
                        if(bundle!=null)
                        {
                            Expense expense = getExpenseFromBundle(bundle);

                                SQLiteDatabase database = openHelper.getWritableDatabase();

                                ContentValues contentValues=new ContentValues();
                                contentValues.put(Contract.Expenses.TITLE,expense.getTitle());
                                contentValues.put(Contract.Expenses.DESCRIPTION,expense.getDescription());
                                contentValues.put(Contract.Expenses.AMOUNT,expense.getAmount());

                                long id = database.insert(Contract.Expenses.TABLE_NAME,null,contentValues);
                                expense.setId((int) id);
                                mExpenses.add(expense);
                                mAdapter.notifyDataSetChanged();
                        }
                    }
                    break;
            }

        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    private Expense getExpenseFromBundle(Bundle bundle){
        if(bundle != null){
            String title = bundle.getString(Constants.TITLE_KEY,"");
            String desc = bundle.getString(Constants.DESCRIPTION_KEY,"");
            int amount = bundle.getInt(Constants.AMOUNT_KEY,0);
            return new Expense(title,desc,amount);
        }
        return null;
    }

}
