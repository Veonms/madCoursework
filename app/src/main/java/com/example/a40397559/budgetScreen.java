package com.example.a40397559;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ExpandableListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class budgetScreen extends AppCompatActivity {
    ExpandableListView expandableListView;
    List<String> listGroup;
    HashMap<String, List<String>> listItem;
    MainAdapter adapter;

    float x1, x2, y1, y2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_screen);

        expandableListView = findViewById(R.id.expandableList);
        listGroup = new ArrayList<>();
        listItem = new HashMap<>();
        adapter = new MainAdapter(this, listGroup, listItem);
        expandableListView.setAdapter(adapter);
        initListData();
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
                    Intent i = new Intent(budgetScreen.this, HomeActivity.class);
                    startActivity(i);
                }
                break;
        }
        return false;
    }

    private void initListData() {
        listGroup.add(getString(R.string.EatingOut));
        listGroup.add(getString(R.string.Entertainment));
        listGroup.add(getString(R.string.Expenses));
        listGroup.add(getString(R.string.Groceries));
        listGroup.add(getString(R.string.Shopping));

        String[] array;

        List<String> eatingOutList = new ArrayList<>();
        array = getResources().getStringArray(R.array.EatingOut);
        for (String item : array) {
            eatingOutList.add(item);
        }

        List<String> entertainmentList = new ArrayList<>();
        array = getResources().getStringArray(R.array.Entertainment);
        for (String item : array) {
            entertainmentList.add(item);
        }

        List<String> expensesList = new ArrayList<>();
        array = getResources().getStringArray(R.array.Expenses);
        for (String item : array) {
            expensesList.add(item);
        }

        List<String> groceriesList = new ArrayList<>();
        array = getResources().getStringArray(R.array.Groceries);
        for (String item : array) {
            groceriesList.add(item);
        }

        List<String> shoppingList = new ArrayList<>();
        array = getResources().getStringArray(R.array.Shopping);
        for (String item : array) {
            shoppingList.add(item);
        }

        listItem.put(listGroup.get(0), eatingOutList);
        listItem.put(listGroup.get(1), entertainmentList);
        listItem.put(listGroup.get(2), expensesList);
        listItem.put(listGroup.get(3), groceriesList);
        listItem.put(listGroup.get(4), shoppingList);
        adapter.notifyDataSetChanged();
    }
}
