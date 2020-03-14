package com.example.a40397559;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.List;
/*
* This activity displays the data to the user in the form of a PieChart. The user is able to
* interact with the PieChar. If they click on a slice it will display the category and the amount
* remaining. The user is able to rotate the PieChart, keeping the functionality. The data for the
* PieChart comes from the activity editScreen.
* References:
*   https://www.youtube.com/watch?v=kKqZoS4THnY
*   https://www.youtube.com/watch?v=fJEFZ6EOM9o
*   https://weeklycoding.com/mpandroidchart/
*   https://github.com/PhilJay/MPAndroidChart/
 */
public class HomeActivity extends AppCompatActivity {

    //// Creates String variables to save data so you can return to the activity with no changes
    public static final String EXTRA_NUMBER_SAVE_EatOut = "com.example.a40397559.EXTRA_NUMBER_SAVE_EatOut";
    public static final String EXTRA_NUMBER_SAVE_Entertainment = "com.example.a40397559.EXTRA_NUMBER_SAVE_Entertainment";
    public static final String EXTRA_NUMBER_SAVE_Expenses = "com.example.a40397559.EXTRA_NUMBER_SAVE_Expenses";
    public static final String EXTRA_NUMBER_SAVE_Groceries = "com.example.a40397559.EXTRA_NUMBER_SAVE_Groceries";
    public static final String EXTRA_NUMBER_SAVE_Shopping = "com.example.a40397559.EXTRA_NUMBER_SAVE_Shopping";

    // Creates String variables to save the preferences so you can return to the activity with no changes
    public static final String SHARED_PREFS = "com.example.a40397559.SHAREDPREFS";

    // Creates integer variables
    private int rEatOut;
    private int rEntertainment;
    private int rExpenses;
    private int rGroceries;
    private int rShopping;

    PieChart pieChart;

    float x1, x2;

    // Creates an arrayList so data can be added to the PieChart
    List<Integer> yData = new ArrayList<>();

    // Creates categories
    private String[] xData = {"Eating Out", "Entertainment", "Expenses", "Groceries", "Shopping"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent intent = getIntent();
        // Tries to get data from edit screen then run the method update. If not will run the method
        //loadData and update.
        try {
            rEatOut = intent.getIntExtra(editScreen.EXTRA_NUMBER_SAVE_EatOut, 0);
            rEntertainment = intent.getIntExtra(editScreen.EXTRA_NUMBER_SAVE_Entertainment, 0);
            rExpenses = intent.getIntExtra(editScreen.EXTRA_NUMBER_SAVE_Expenses, 0);
            rGroceries = intent.getIntExtra(editScreen.EXTRA_NUMBER_SAVE_Groceries, 0);
            rShopping = intent.getIntExtra(editScreen.EXTRA_NUMBER_SAVE_Shopping, 0);

            if (rEatOut == 0 && rEntertainment == 0 && rExpenses == 0
                    && rGroceries == 0 && rShopping == 0) {
                throw new Exception();
            }
            update();

        } catch (Exception e) {
            loadData();
            update();
        }
        // Creates reference of PieChart
        pieChart = (PieChart) findViewById(R.id.idPiechart);

        // Sets values for the pieChart
        pieChart.setHoleRadius(60f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterText("Remaining budget");
        pieChart.setCenterTextSize(20);
        pieChart.setDrawEntryLabels(true);

        // Removes the description
        Description description = pieChart.getDescription();
        description.setEnabled(false);

        // Removes the legend
        Legend legend = pieChart.getLegend();
        legend.setEnabled(false);

        // Adds data to the PieChart
        addData();

        // When a slice of the PieChart is clicked it will display the category and remaining amount
        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {

                int pos1 = e.toString().indexOf("(sum): ");
                final String remaining = e.toString().substring(pos1 + 18);

                for (int i = 0; i < yData.size(); i++) {
                    if (yData.get(i) == Float.parseFloat(remaining)) {
                        pos1 = i;
                        break;
                    }
                }
                final String category = xData[pos1];

                // Displays category and remaining amount to the user
                Toast.makeText(HomeActivity.this, "Category: " + category + "\nRemaining: " + remaining, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });
        saveData();
    }

    private void addData() {
        // Creates arrayLists
        ArrayList<PieEntry> yEntries = new ArrayList<>();
        ArrayList<String> xEntries = new ArrayList<>();

        // Adds categories
        for (int i = 0; i < yData.size(); i++) {
            yEntries.add(new PieEntry(yData.get(i), i));
        }

        // Adds remaining amounts
        for (int i = 0; i < xData.length; i++) {
            xEntries.add(xData[i]);
        }

        PieDataSet pieDataSet = new PieDataSet(yEntries, "Remaining budget");

        // Sets visual variables
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }

    public boolean onTouchEvent(MotionEvent touchEvent) {
        switch (touchEvent.getAction()) {
            case MotionEvent.ACTION_DOWN: // Gets points of where you first touch the screen
                x1 = touchEvent.getX();
                break;
            case MotionEvent.ACTION_UP: // Gets points of where you leave the screen
                x2 = touchEvent.getX();
                if (x1 > x2) { // Checks if you you have swiped to the next activity (left)
                    Intent i = new Intent(HomeActivity.this, budgetScreen.class);
                    startActivity(i);
                }
                break;
        }
        return false;
    }

    public void sendMessage(View view) {
        Intent intent = new Intent(HomeActivity.this, editScreen.class);
        startActivity(intent);
    }

    public void saveData() {
        // Makes the shared preferences private
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Stores all the data so it can be used the next time the activity is accessed
        editor.putInt(EXTRA_NUMBER_SAVE_EatOut, rEatOut);
        editor.putInt(EXTRA_NUMBER_SAVE_Entertainment, rEntertainment);
        editor.putInt(EXTRA_NUMBER_SAVE_Expenses, rExpenses);
        editor.putInt(EXTRA_NUMBER_SAVE_Groceries, rGroceries);
        editor.putInt(EXTRA_NUMBER_SAVE_Shopping, rShopping);

        editor.apply();
    }

    public void loadData() {
        // Makes the shared preferences private
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);

        // Loads the data from the previous session. If there is no data it is defaulted to 0
        rEatOut = sharedPreferences.getInt(EXTRA_NUMBER_SAVE_EatOut, 0);
        rEntertainment = sharedPreferences.getInt(EXTRA_NUMBER_SAVE_Entertainment, 0);
        rExpenses = sharedPreferences.getInt(EXTRA_NUMBER_SAVE_Expenses, 0);
        rGroceries = sharedPreferences.getInt(EXTRA_NUMBER_SAVE_Groceries, 0);
        rShopping = sharedPreferences.getInt(EXTRA_NUMBER_SAVE_Shopping, 0);
    }

    public void update() {
        // Adds all data from previous session to the PieChart
        yData.add(rEatOut);
        yData.add(rEntertainment);
        yData.add(rExpenses);
        yData.add(rGroceries);
        yData.add(rShopping);
    }
}
