package com.example.a40397559;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class editScreen extends AppCompatActivity {

    float x1, x2, y1, y2;

    public static final String EXTRA_NUMBER_EatOut = "com.example.a40397559.EXTRA_NUMBER_EatOut";
    public static final String EXTRA_NUMBER_Entertainment = "com.example.a40397559.EXTRA_NUMBER_Entertainment";
    public static final String EXTRA_NUMBER_Expenses = "com.example.a40397559.EXTRA_NUMBER_Expenses";
    public static final String EXTRA_NUMBER_Groceries = "com.example.a40397559.EXTRA_NUMBER_Groceries";
    public static final String EXTRA_NUMBER_Shopping = "com.example.a40397559.EXTRA_NUMBER_Shopping";
    public static final String SHARED_PREFS = "com.example.a40397559.SHAREDPREFS";
    int rEatOut;
    int rEntertainment;
    int rExpenses;
    int rGroceries;
    int rShopping;
    private int saveEatOutB;
    private int saveEntertainmentB;
    private int saveExpensesB;
    private int saveGroceriesB;
    private int saveShoppingB;
    private int saveEatOutS;
    private int saveEntertainmentS;
    private int saveExpensesS;
    private int saveGroceriesS;
    private int saveShoppingS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_screen);

        try {
            loadData();
            update();
        } catch (Exception e) {

        }

        Button Clear = findViewById(R.id.btn_reset);
        Button Update = findViewById(R.id.btn_update);
        EditText eatOutB = (EditText) findViewById(R.id.txt_budgetEatingOut);
        EditText entertainmentB = (EditText) findViewById(R.id.txt_budgetEntertainment);
        EditText expensesB = (EditText) findViewById(R.id.txt_budgetExpenses);
        EditText groceriesB = (EditText) findViewById(R.id.txt_budgetGroceries);
        EditText shoppingB = (EditText) findViewById(R.id.txt_budgetShopping);

        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
                dataToChart();
            }
        });

        Clear.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                clearForm((ViewGroup) findViewById(R.id.editScreenXML));
            }
        });
    }

    private void clearForm(ViewGroup group) {
        for (int i = 0, count = group.getChildCount(); i < count; ++i) {
            View view = group.getChildAt(i);
            if (view instanceof EditText) {
                ((EditText) view).setText("");
            }

            if (view instanceof ViewGroup && (((ViewGroup) view).getChildCount() > 0))
                clearForm((ViewGroup) view);
        }
    }

    public boolean onTouchEvent(MotionEvent touchEvent) {
        switch (touchEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x1 = touchEvent.getX();
                y1 = touchEvent.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = touchEvent.getX();
                y2 = touchEvent.getY();
                if (x1 < x2) {
                    Intent i = new Intent(editScreen.this, HomeActivity.class);
                    startActivity(i);
                }
                break;
        }
        return false;
    }

    public void dataToChart() {
        Intent intent = new Intent(this, HomeActivity.class);

        EditText dEatOut = (EditText) findViewById(R.id.txt_budgetEatingOut);
        EditText dEntertainment = (EditText) findViewById(R.id.txt_budgetEntertainment);
        EditText dExpenses = (EditText) findViewById(R.id.txt_budgetExpenses);
        EditText dGroceries = (EditText) findViewById(R.id.txt_budgetGroceries);
        EditText dShopping = (EditText) findViewById(R.id.txt_budgetShopping);

        rEatOut = Integer.parseInt(dEatOut.getText().toString());
        rEntertainment = Integer.parseInt(dEntertainment.getText().toString());
        rExpenses = Integer.parseInt(dExpenses.getText().toString());
        rGroceries = Integer.parseInt(dGroceries.getText().toString());
        rShopping = Integer.parseInt(dShopping.getText().toString());

        intent.putExtra(EXTRA_NUMBER_EatOut, rEatOut);
        intent.putExtra(EXTRA_NUMBER_Entertainment, rEntertainment);
        intent.putExtra(EXTRA_NUMBER_Expenses, rExpenses);
        intent.putExtra(EXTRA_NUMBER_Groceries, rGroceries);
        intent.putExtra(EXTRA_NUMBER_Shopping, rShopping);

        startActivity(intent);
    }

    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(EXTRA_NUMBER_EatOut, rEatOut);
        editor.putInt(EXTRA_NUMBER_Entertainment, rEntertainment);
        editor.putInt(EXTRA_NUMBER_Expenses, rExpenses);
        editor.putInt(EXTRA_NUMBER_Groceries, rGroceries);
        editor.putInt(EXTRA_NUMBER_Shopping, rShopping);

        editor.apply();
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        saveEatOutB = sharedPreferences.getInt(EXTRA_NUMBER_EatOut, 0);
        saveEntertainmentB = sharedPreferences.getInt(EXTRA_NUMBER_Entertainment, 0);
        saveExpensesB = sharedPreferences.getInt(EXTRA_NUMBER_Expenses, 0);
        saveGroceriesB = sharedPreferences.getInt(EXTRA_NUMBER_Groceries, 0);
        saveShoppingB = sharedPreferences.getInt(EXTRA_NUMBER_Shopping, 0);
    }

    public void update() {
        EditText eatOutB = (EditText) findViewById(R.id.txt_budgetEatingOut);
        EditText entertainmentB = (EditText) findViewById(R.id.txt_budgetEntertainment);
        EditText expensesB = (EditText) findViewById(R.id.txt_budgetExpenses);
        EditText groceriesB = (EditText) findViewById(R.id.txt_budgetGroceries);
        EditText shoppingB = (EditText) findViewById(R.id.txt_budgetShopping);

        eatOutB.setText(String.valueOf(saveEatOutB));
        entertainmentB.setText(String.valueOf(saveEntertainmentB));
        expensesB.setText(String.valueOf(saveExpensesB));
        groceriesB.setText(String.valueOf(saveGroceriesB));
        shoppingB.setText(String.valueOf(saveShoppingB));
    }
}

