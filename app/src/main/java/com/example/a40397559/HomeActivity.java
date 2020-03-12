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

public class HomeActivity extends AppCompatActivity {

    public static final String EXTRA_NUMBER_SAVE_EatOut = "com.example.a40397559.EXTRA_NUMBER_SAVE_EatOut";
    public static final String EXTRA_NUMBER_SAVE_Entertainment = "com.example.a40397559.EXTRA_NUMBER_SAVE_Entertainment";
    public static final String EXTRA_NUMBER_SAVE_Expenses = "com.example.a40397559.EXTRA_NUMBER_SAVE_Expenses";
    public static final String EXTRA_NUMBER_SAVE_Groceries = "com.example.a40397559.EXTRA_NUMBER_SAVE_Groceries";
    public static final String EXTRA_NUMBER_SAVE_Shopping = "com.example.a40397559.EXTRA_NUMBER_SAVE_Shopping";
    public static final String SHARED_PREFS = "com.example.a40397559.SHAREDPREFS";

    private int rEatOut;
    private int rEntertainment;
    private int rExpenses;
    private int rGroceries;
    private int rShopping;

    PieChart pieChart;
    float x1, x2, y1, y2;
    List<Integer> yData = new ArrayList<>();
    private String[] xData = {"Eating Out", "Entertainment", "Expenses", "Groceries", "Shopping"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent intent = getIntent();
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
        pieChart = (PieChart) findViewById(R.id.idPiechart);

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

        addData();

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

                Toast.makeText(HomeActivity.this, "Category: " + category + "\nRemaining: " + remaining, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });
        saveData();
    }

    private void addData() {
        ArrayList<PieEntry> yEntries = new ArrayList<>();
        ArrayList<String> xEntries = new ArrayList<>();

        for (int i = 0; i < yData.size(); i++) {
            yEntries.add(new PieEntry(yData.get(i), i));
        }

        for (int i = 0; i < xData.length; i++) {
            xEntries.add(xData[i]);
        }

        PieDataSet pieDataSet = new PieDataSet(yEntries, "Remaining budget");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
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
                if (x1 > x2) {
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
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(EXTRA_NUMBER_SAVE_EatOut, rEatOut);
        editor.putInt(EXTRA_NUMBER_SAVE_Entertainment, rEntertainment);
        editor.putInt(EXTRA_NUMBER_SAVE_Expenses, rExpenses);
        editor.putInt(EXTRA_NUMBER_SAVE_Groceries, rGroceries);
        editor.putInt(EXTRA_NUMBER_SAVE_Shopping, rShopping);

        editor.apply();
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);

        rEatOut = sharedPreferences.getInt(EXTRA_NUMBER_SAVE_EatOut, 0);
        rEntertainment = sharedPreferences.getInt(EXTRA_NUMBER_SAVE_Entertainment, 0);
        rExpenses = sharedPreferences.getInt(EXTRA_NUMBER_SAVE_Expenses, 0);
        rGroceries = sharedPreferences.getInt(EXTRA_NUMBER_SAVE_Groceries, 0);
        rShopping = sharedPreferences.getInt(EXTRA_NUMBER_SAVE_Shopping, 0);
    }

    public void update() {
        yData.add(rEatOut);
        yData.add(rEntertainment);
        yData.add(rExpenses);
        yData.add(rGroceries);
        yData.add(rShopping);
    }
}
