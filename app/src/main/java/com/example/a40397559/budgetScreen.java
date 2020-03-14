package com.example.a40397559;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.diegodobelo.expandingview.ExpandingItem;
import com.diegodobelo.expandingview.ExpandingList;
/*
* This activity will allow users to enter their transactions and remove transactions. When a
* category is clicked it will expand with an animation displaying all items under that category.
* The user will be able to swipe right to go back to the previous activity (HomeActivity).
* References:
*   https://www.youtube.com/watch?v=kKqZoS4THnY
*   https://github.com/diegodobelo/AndroidExpandingViewLibrary
 */
public class budgetScreen extends AppCompatActivity {

    float x1, x2;
    private ExpandingList mExpandingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_screen);

        // Assigns the expanding list to the variable mExpandingList
        mExpandingList = findViewById(R.id.expanding_list);
        createItems();
    }

    private void createItems() {
        // Adds different categories to the expendable list with items under each category
        addItem("Eating Out", new String[]{"Macdonalds: £8", "Nandos: £14", "Uber Eats: £24"}, R.color.red, R.drawable.ic_icon);
        addItem("Entertainment", new String[]{"Cinema: £8", "Football: £24"}, R.color.red, R.drawable.ic_icon);
        addItem("Expenses", new String[]{"Bus pass: £52"}, R.color.red, R.drawable.ic_icon);
        addItem("Groceries", new String[]{"Lidl: £7", "Morrisons: £3"}, R.color.red, R.drawable.ic_icon);
        addItem("Shopping", new String[]{"Next: £7", "River Island: £3"}, R.color.red, R.drawable.ic_icon);
    }

    private void addItem(String title, String[] subItems, int colorRes, int iconRes) {
        final ExpandingItem item = mExpandingList.createNewItem(R.layout.expanding_layout);

        // Checks thee item isnt empty
        if (item != null) {
            // Assigns colour and image
            item.setIndicatorColorRes(colorRes);
            item.setIndicatorIconRes(iconRes);

            ((TextView) item.findViewById(R.id.title)).setText(title);

            // Creates multiple items
            item.createSubItems(subItems.length);
            for (int i = 0; i < item.getSubItemsCount(); i++) {
                final View view = item.getSubItemView(i);
                configureSubItem(item, view, subItems[i]);
            }

            // Allows the user to add more items to a category
            item.findViewById(R.id.add_more_sub_items).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showInsertDialog(new OnItemCreated() {
                        @Override
                        public void itemCreated(String title) {
                            View newSubItem = item.createSubItem();
                            configureSubItem(item, newSubItem, title);
                        }
                    });
                }
            });

            // Allows the user to remove items from a category
            item.findViewById(R.id.remove_item).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mExpandingList.removeItem(item);
                }
            });
        }
    }

    private void configureSubItem(final ExpandingItem item, final View view, String subTitle) {
        ((TextView) view.findViewById(R.id.sub_title)).setText(subTitle);
        view.findViewById(R.id.remove_sub_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.removeSubItem(view);
            }
        });
    }

    // Allows the user to add titles
    private void showInsertDialog(final OnItemCreated positive) {
        final EditText text = new EditText(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(text);
        builder.setTitle(R.string.enter_title);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                positive.itemCreated(text.getText().toString());
            }
        });
        builder.setNegativeButton(android.R.string.cancel, null);
        builder.show();
    }

    interface OnItemCreated {
        void itemCreated(String title);
    }

    public boolean onTouchEvent(MotionEvent touchEvent) {
        switch (touchEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:// Gets points of where you first touch the screen
                x1 = touchEvent.getX();
                break;
            case MotionEvent.ACTION_UP:// Gets points of where you last the screen
                x2 = touchEvent.getX();
                if (x1 < x2) { // Checks if you you have swiped back (right)
                    Intent i = new Intent(budgetScreen.this, HomeActivity.class);
                    startActivity(i);
                }
                break;
        }
        return false;
    }

}
