package com.example.a40397559;

import android.content.Intent;
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

    PieChart pieChart;
    float x1, x2, y1, y2;
    List<Integer> yData = new ArrayList<>();
    private String[] xData = {"Eating Out", "Entertainment", "Expenses", "Groceries", "Shopping"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent intent = getIntent();

        int rEatOut = intent.getIntExtra(editScreen.EXTRA_NUMBER_EatOut, 0);
        int rEntertainment = intent.getIntExtra(editScreen.EXTRA_NUMBER_Entertainment, 0);
        int rExpenses = intent.getIntExtra(editScreen.EXTRA_NUMBER_Expenses, 0);
        int rGroceries = intent.getIntExtra(editScreen.EXTRA_NUMBER_Groceries, 0);
        int rShopping = intent.getIntExtra(editScreen.EXTRA_NUMBER_Shopping, 0);

        yData.add(rEatOut);
        yData.add(rEntertainment);
        yData.add(rExpenses);
        yData.add(rGroceries);
        yData.add(rShopping);

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
}
