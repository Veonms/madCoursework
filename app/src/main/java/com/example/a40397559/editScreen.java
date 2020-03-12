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

    public static final String SHARED_PREFS = "com.example.a40397559.SHAREDPREFS";

    public static final String EXTRA_NUMBER_SAVE_EatOut = "com.example.a40397559.EXTRA_NUMBER_SAVE_EatOut";
    public static final String EXTRA_NUMBER_SAVE_Entertainment = "com.example.a40397559.EXTRA_NUMBER_SAVE_Entertainment";
    public static final String EXTRA_NUMBER_SAVE_Expenses = "com.example.a40397559.EXTRA_NUMBER_SAVE_Expenses";
    public static final String EXTRA_NUMBER_SAVE_Groceries = "com.example.a40397559.EXTRA_NUMBER_SAVE_Groceries";
    public static final String EXTRA_NUMBER_SAVE_Shopping = "com.example.a40397559.EXTRA_NUMBER_SAVE_Shopping";

    public static final String EXTRA_NUMBER_EatOut = "com.example.a40397559.EXTRA_NUMBER_EatOut";
    public static final String EXTRA_NUMBER_Entertainment = "com.example.a40397559.EXTRA_NUMBER_Entertainment";
    public static final String EXTRA_NUMBER_Expenses = "com.example.a40397559.EXTRA_NUMBER_Expenses";
    public static final String EXTRA_NUMBER_Groceries = "com.example.a40397559.EXTRA_NUMBER_Groceries";
    public static final String EXTRA_NUMBER_Shopping = "com.example.a40397559.EXTRA_NUMBER_Shopping";

    public static final String EXTRA_NUMBER_EatOut2 = "com.example.a40397559.EXTRA_NUMBER_EatOut2";
    public static final String EXTRA_NUMBER_Entertainment2 = "com.example.a40397559.EXTRA_NUMBER_Entertainment2";
    public static final String EXTRA_NUMBER_Expenses2 = "com.example.a40397559.EXTRA_NUMBER_Expenses2";
    public static final String EXTRA_NUMBER_Groceries2 = "com.example.a40397559.EXTRA_NUMBER_Groceries2";
    public static final String EXTRA_NUMBER_Shopping2 = "com.example.a40397559.EXTRA_NUMBER_Shopping2";


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

        EditText bEatOut = (EditText) findViewById(R.id.txt_budgetEatingOut);
        EditText bEntertainment = (EditText) findViewById(R.id.txt_budgetEntertainment);
        EditText bExpenses = (EditText) findViewById(R.id.txt_budgetExpenses);
        EditText bGroceries = (EditText) findViewById(R.id.txt_budgetGroceries);
        EditText bShopping = (EditText) findViewById(R.id.txt_budgetShopping);
        EditText sEatOut = (EditText) findViewById(R.id.txt_spentEatingOut);
        EditText sEntertainment = (EditText) findViewById(R.id.txt_spentEntertainment);
        EditText sExpenses = (EditText) findViewById(R.id.txt_spentExpenses);
        EditText sGroceries = (EditText) findViewById(R.id.txt_spentGroceries);
        EditText sShopping = (EditText) findViewById(R.id.txt_spentShopping);

        int rEatOut = Integer.parseInt(bEatOut.getText().toString()) - Integer.parseInt(sEatOut.getText().toString());
        int rEntertainment = Integer.parseInt(bEntertainment.getText().toString()) - Integer.parseInt(sEntertainment.getText().toString());
        int rExpenses = Integer.parseInt(bExpenses.getText().toString()) - Integer.parseInt(sExpenses.getText().toString());
        int rGroceries = Integer.parseInt(bGroceries.getText().toString()) - Integer.parseInt(sGroceries.getText().toString());
        int rShopping = Integer.parseInt(bShopping.getText().toString()) - Integer.parseInt(sShopping.getText().toString());

        intent.putExtra(EXTRA_NUMBER_SAVE_EatOut, rEatOut);
        intent.putExtra(EXTRA_NUMBER_SAVE_Entertainment, rEntertainment);
        intent.putExtra(EXTRA_NUMBER_SAVE_Expenses, rExpenses);
        intent.putExtra(EXTRA_NUMBER_SAVE_Groceries, rGroceries);
        intent.putExtra(EXTRA_NUMBER_SAVE_Shopping, rShopping);

        startActivity(intent);
    }

    public void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        EditText dEatOut = (EditText) findViewById(R.id.txt_budgetEatingOut);
        EditText dEntertainment = (EditText) findViewById(R.id.txt_budgetEntertainment);
        EditText dExpenses = (EditText) findViewById(R.id.txt_budgetExpenses);
        EditText dGroceries = (EditText) findViewById(R.id.txt_budgetGroceries);
        EditText dShopping = (EditText) findViewById(R.id.txt_budgetShopping);

        EditText dEatOut2 = (EditText) findViewById(R.id.txt_spentEatingOut);
        EditText dEntertainment2 = (EditText) findViewById(R.id.txt_spentEntertainment);
        EditText dExpenses2 = (EditText) findViewById(R.id.txt_spentExpenses);
        EditText dGroceries2 = (EditText) findViewById(R.id.txt_spentGroceries);
        EditText dShopping2 = (EditText) findViewById(R.id.txt_spentShopping);

        saveEatOutB = Integer.parseInt(dEatOut.getText().toString());
        saveEntertainmentB = Integer.parseInt(dEntertainment.getText().toString());
        saveExpensesB = Integer.parseInt(dExpenses.getText().toString());
        saveGroceriesB = Integer.parseInt(dGroceries.getText().toString());
        saveShoppingB = Integer.parseInt(dShopping.getText().toString());

        saveEatOutS = Integer.parseInt(dEatOut2.getText().toString());
        saveEntertainmentS = Integer.parseInt(dEntertainment2.getText().toString());
        saveExpensesS = Integer.parseInt(dExpenses2.getText().toString());
        saveGroceriesS = Integer.parseInt(dGroceries2.getText().toString());
        saveShoppingS = Integer.parseInt(dShopping2.getText().toString());

        editor.putInt(EXTRA_NUMBER_EatOut, saveEatOutB);
        editor.putInt(EXTRA_NUMBER_Entertainment, saveEntertainmentB);
        editor.putInt(EXTRA_NUMBER_Expenses, saveExpensesB);
        editor.putInt(EXTRA_NUMBER_Groceries, saveGroceriesB);
        editor.putInt(EXTRA_NUMBER_Shopping, saveShoppingB);

        editor.putInt(EXTRA_NUMBER_EatOut2, saveEatOutS);
        editor.putInt(EXTRA_NUMBER_Entertainment2, saveEntertainmentS);
        editor.putInt(EXTRA_NUMBER_Expenses2, saveExpensesS);
        editor.putInt(EXTRA_NUMBER_Groceries2, saveGroceriesS);
        editor.putInt(EXTRA_NUMBER_Shopping2, saveShoppingS);

        editor.apply();
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);

        saveEatOutB = sharedPreferences.getInt(EXTRA_NUMBER_EatOut, 0);
        saveEntertainmentB = sharedPreferences.getInt(EXTRA_NUMBER_Entertainment, 0);
        saveExpensesB = sharedPreferences.getInt(EXTRA_NUMBER_Expenses, 0);
        saveGroceriesB = sharedPreferences.getInt(EXTRA_NUMBER_Groceries, 0);
        saveShoppingB = sharedPreferences.getInt(EXTRA_NUMBER_Shopping, 0);

        saveEatOutS = sharedPreferences.getInt(EXTRA_NUMBER_EatOut2, 0);
        saveEntertainmentS = sharedPreferences.getInt(EXTRA_NUMBER_Entertainment2, 0);
        saveExpensesS = sharedPreferences.getInt(EXTRA_NUMBER_Expenses2, 0);
        saveGroceriesS = sharedPreferences.getInt(EXTRA_NUMBER_Groceries2, 0);
        saveShoppingS = sharedPreferences.getInt(EXTRA_NUMBER_Shopping2, 0);
    }

    public void update() {
        EditText eatOutB = (EditText) findViewById(R.id.txt_budgetEatingOut);
        EditText entertainmentB = (EditText) findViewById(R.id.txt_budgetEntertainment);
        EditText expensesB = (EditText) findViewById(R.id.txt_budgetExpenses);
        EditText groceriesB = (EditText) findViewById(R.id.txt_budgetGroceries);
        EditText shoppingB = (EditText) findViewById(R.id.txt_budgetShopping);

        EditText eatOutS = (EditText) findViewById(R.id.txt_spentEatingOut);
        EditText entertainmentS = (EditText) findViewById(R.id.txt_spentEntertainment);
        EditText expensesS = (EditText) findViewById(R.id.txt_spentExpenses);
        EditText groceriesS = (EditText) findViewById(R.id.txt_spentGroceries);
        EditText shoppingS = (EditText) findViewById(R.id.txt_spentShopping);

        eatOutB.setText(String.valueOf(saveEatOutB));
        entertainmentB.setText(String.valueOf(saveEntertainmentB));
        expensesB.setText(String.valueOf(saveExpensesB));
        groceriesB.setText(String.valueOf(saveGroceriesB));
        shoppingB.setText(String.valueOf(saveShoppingB));

        eatOutS.setText(String.valueOf(saveEatOutS));
        entertainmentS.setText(String.valueOf(saveEntertainmentS));
        expensesS.setText(String.valueOf(saveExpensesS));
        groceriesS.setText(String.valueOf(saveGroceriesS));
        shoppingS.setText(String.valueOf(saveShoppingS));
    }
}

