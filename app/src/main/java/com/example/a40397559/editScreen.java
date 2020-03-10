package com.example.a40397559;

import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_screen);

        Button Clear = findViewById(R.id.btn_reset);
        Button Update = findViewById(R.id.btn_update);

        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

        int rEatOut = Integer.parseInt(dEatOut.getText().toString());
        int rEntertainment = Integer.parseInt(dEntertainment.getText().toString());
        int rExpenses = Integer.parseInt(dExpenses.getText().toString());
        int rGroceries = Integer.parseInt(dGroceries.getText().toString());
        int rShopping = Integer.parseInt(dShopping.getText().toString());

        intent.putExtra(EXTRA_NUMBER_EatOut, rEatOut);
        intent.putExtra(EXTRA_NUMBER_Entertainment, rEntertainment);
        intent.putExtra(EXTRA_NUMBER_Expenses, rExpenses);
        intent.putExtra(EXTRA_NUMBER_Groceries, rGroceries);
        intent.putExtra(EXTRA_NUMBER_Shopping, rShopping);

        startActivity(intent);
    }
}

