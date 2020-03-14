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
/*
* This activity will allow the user to enter in data such as the budget each category has and how
* much has been spent on each category.
* There are 2 buttons the user can use. The update button will
* will save the data in each EditText box, so if the user returns to this activity the data will be
* the same, it will then send the difference between the budget and spent to HomeActivity to use in
* the PieChart. The reset button will clear all data entered into the EditText boxes, but not default
* to any number so if you accidentally press both reset and update it wont clear all your data.
* You can also swipe back to the previous activity, HomeActivity, by gesturing (moving your "finger")
* left to right.
* References:
*   https://www.youtube.com/watch?v=kKqZoS4THnY
*   https://www.youtube.com/watch?v=fJEFZ6EOM9o
*   https://www.youtube.com/watch?v=eL69kj-_Wvs
*   https://stackoverflow.com/questions/5740708/android-clearing-all-edittext-fields-with-clear-button
 */
public class editScreen extends AppCompatActivity {

    float x1, x2;

    // Creates String variables to save the preferences so you can return to the activity with no changes
    public static final String SHARED_PREFS = "com.example.a40397559.SHAREDPREFS";

    // Creates String variables to save data so you can return to the activity with no changes
    // Variables for sending data to HomeActivity to be entered to PieChart
    public static final String EXTRA_NUMBER_SAVE_EatOut = "com.example.a40397559.EXTRA_NUMBER_SAVE_EatOut";
    public static final String EXTRA_NUMBER_SAVE_Entertainment = "com.example.a40397559.EXTRA_NUMBER_SAVE_Entertainment";
    public static final String EXTRA_NUMBER_SAVE_Expenses = "com.example.a40397559.EXTRA_NUMBER_SAVE_Expenses";
    public static final String EXTRA_NUMBER_SAVE_Groceries = "com.example.a40397559.EXTRA_NUMBER_SAVE_Groceries";
    public static final String EXTRA_NUMBER_SAVE_Shopping = "com.example.a40397559.EXTRA_NUMBER_SAVE_Shopping";

    // Variables for saving budget EditText boxes' data
    public static final String EXTRA_NUMBER_EatOut = "com.example.a40397559.EXTRA_NUMBER_EatOut";
    public static final String EXTRA_NUMBER_Entertainment = "com.example.a40397559.EXTRA_NUMBER_Entertainment";
    public static final String EXTRA_NUMBER_Expenses = "com.example.a40397559.EXTRA_NUMBER_Expenses";
    public static final String EXTRA_NUMBER_Groceries = "com.example.a40397559.EXTRA_NUMBER_Groceries";
    public static final String EXTRA_NUMBER_Shopping = "com.example.a40397559.EXTRA_NUMBER_Shopping";

    // Variables for saving spent EditText boxes' data
    public static final String EXTRA_NUMBER_EatOut2 = "com.example.a40397559.EXTRA_NUMBER_EatOut2";
    public static final String EXTRA_NUMBER_Entertainment2 = "com.example.a40397559.EXTRA_NUMBER_Entertainment2";
    public static final String EXTRA_NUMBER_Expenses2 = "com.example.a40397559.EXTRA_NUMBER_Expenses2";
    public static final String EXTRA_NUMBER_Groceries2 = "com.example.a40397559.EXTRA_NUMBER_Groceries2";
    public static final String EXTRA_NUMBER_Shopping2 = "com.example.a40397559.EXTRA_NUMBER_Shopping2";


    // Creates integer variables
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

        // Tries to run methods loadData and update. If any errors occur they are caught and the
        // application doesnt crash.
        try {
            loadData();
            update();
        } catch (Exception e) {

        }

        // Assigns Clear and Update to the corrosponding button
        Button Clear = findViewById(R.id.btn_reset);
        Button Update = findViewById(R.id.btn_update);

        // If the update button is pressed it will run the methods saveData and dataToChart
        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
                dataToChart();
            }
        });

        // If the clear button is pressed the method clearText will be ran
        Clear.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                clearText((ViewGroup) findViewById(R.id.editScreenXML));
            }
        });
    }

    /*
    * The clearText method iterates through all the children in the layout, works even if the
    * layouts are nested, and clears all the data for every instance of EditText.
     */
    private void clearText(ViewGroup group) {
        for (int i = 0, count = group.getChildCount(); i < count; ++i) {
            View view = group.getChildAt(i);
            if (view instanceof EditText) {
                ((EditText) view).setText("");
            }

            if (view instanceof ViewGroup && (((ViewGroup) view).getChildCount() > 0))
                clearText((ViewGroup) view);
        }
    }

    public boolean onTouchEvent(MotionEvent touchEvent) {
        switch (touchEvent.getAction()) {
            case MotionEvent.ACTION_DOWN: // Gets points of where you first touch the screen
                x1 = touchEvent.getX();
                break;
            case MotionEvent.ACTION_UP: // Gets points of where you leave the screen
                x2 = touchEvent.getX();
                if (x1 < x2) { // Checks if you you have swiped back (right)
                    Intent i = new Intent(editScreen.this, HomeActivity.class);
                    startActivity(i); // Starts activity
                }
                break;
        }
        return false;
    }

    public void dataToChart() {

        // Creates intent to go to a new activity
        Intent intent = new Intent(this, HomeActivity.class);

        // Assigns each variable to corrosponding EditText box
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

        // Takes the text from each EditText box and turns it into a integer. The removes the value
        // of the spent category from the budget category and assigns the value to the variable
        // "rVariable"
        int rEatOut = Integer.parseInt(bEatOut.getText().toString()) - Integer.parseInt(sEatOut.getText().toString());
        int rEntertainment = Integer.parseInt(bEntertainment.getText().toString()) - Integer.parseInt(sEntertainment.getText().toString());
        int rExpenses = Integer.parseInt(bExpenses.getText().toString()) - Integer.parseInt(sExpenses.getText().toString());
        int rGroceries = Integer.parseInt(bGroceries.getText().toString()) - Integer.parseInt(sGroceries.getText().toString());
        int rShopping = Integer.parseInt(bShopping.getText().toString()) - Integer.parseInt(sShopping.getText().toString());

        // Creates an intent that the data will be send to the activity which will start when
        // startActivity(intent) is run
        intent.putExtra(EXTRA_NUMBER_SAVE_EatOut, rEatOut);
        intent.putExtra(EXTRA_NUMBER_SAVE_Entertainment, rEntertainment);
        intent.putExtra(EXTRA_NUMBER_SAVE_Expenses, rExpenses);
        intent.putExtra(EXTRA_NUMBER_SAVE_Groceries, rGroceries);
        intent.putExtra(EXTRA_NUMBER_SAVE_Shopping, rShopping);

        // Starts intent: Changing activity and sending data
        startActivity(intent);
    }

    public void saveData() {
        // Makes the shared preferences private
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Assigns all of the budget EditText boxes to the corresponding variable
        EditText dEatOut = (EditText) findViewById(R.id.txt_budgetEatingOut);
        EditText dEntertainment = (EditText) findViewById(R.id.txt_budgetEntertainment);
        EditText dExpenses = (EditText) findViewById(R.id.txt_budgetExpenses);
        EditText dGroceries = (EditText) findViewById(R.id.txt_budgetGroceries);
        EditText dShopping = (EditText) findViewById(R.id.txt_budgetShopping);

        // Assigns all of the spent EditText boxes to the corresponding variable
        EditText dEatOut2 = (EditText) findViewById(R.id.txt_spentEatingOut);
        EditText dEntertainment2 = (EditText) findViewById(R.id.txt_spentEntertainment);
        EditText dExpenses2 = (EditText) findViewById(R.id.txt_spentExpenses);
        EditText dGroceries2 = (EditText) findViewById(R.id.txt_spentGroceries);
        EditText dShopping2 = (EditText) findViewById(R.id.txt_spentShopping);

        // Takes the text from all the budget EditText boxes and assigns the value, cast to an
        // integer, to the corrosponding variable
        saveEatOutB = Integer.parseInt(dEatOut.getText().toString());
        saveEntertainmentB = Integer.parseInt(dEntertainment.getText().toString());
        saveExpensesB = Integer.parseInt(dExpenses.getText().toString());
        saveGroceriesB = Integer.parseInt(dGroceries.getText().toString());
        saveShoppingB = Integer.parseInt(dShopping.getText().toString());

        // Takes the text from all the spent EditText boxes and assigns the value, cast to an
        // integer, to the corrosponding variable
        saveEatOutS = Integer.parseInt(dEatOut2.getText().toString());
        saveEntertainmentS = Integer.parseInt(dEntertainment2.getText().toString());
        saveExpensesS = Integer.parseInt(dExpenses2.getText().toString());
        saveGroceriesS = Integer.parseInt(dGroceries2.getText().toString());
        saveShoppingS = Integer.parseInt(dShopping2.getText().toString());

        // Stores all the data so it can be used the next time the activity is accessed
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
        // Makes the shared preferences private
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);

        // Loads the data from the previous session. If there is no data it is defaulted to 0
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
        // Assigns all of the budget EditText boxes to the corresponding variable
        EditText eatOutB = (EditText) findViewById(R.id.txt_budgetEatingOut);
        EditText entertainmentB = (EditText) findViewById(R.id.txt_budgetEntertainment);
        EditText expensesB = (EditText) findViewById(R.id.txt_budgetExpenses);
        EditText groceriesB = (EditText) findViewById(R.id.txt_budgetGroceries);
        EditText shoppingB = (EditText) findViewById(R.id.txt_budgetShopping);

        // Assigns all of the spent EditText boxes to the corresponding variable
        EditText eatOutS = (EditText) findViewById(R.id.txt_spentEatingOut);
        EditText entertainmentS = (EditText) findViewById(R.id.txt_spentEntertainment);
        EditText expensesS = (EditText) findViewById(R.id.txt_spentExpenses);
        EditText groceriesS = (EditText) findViewById(R.id.txt_spentGroceries);
        EditText shoppingS = (EditText) findViewById(R.id.txt_spentShopping);

        // Assigns all the data from the previous session to the corrosponding EditText boxes
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

