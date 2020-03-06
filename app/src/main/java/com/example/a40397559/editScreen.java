package com.example.a40397559;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class editScreen extends AppCompatActivity {

    float x1, x2, y1, y2;

    OnClickListener() {
        @Override
        public void onClick (View v){
            txt_budgetEatingOut.getText().clear();
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

    reset.setOnClickListener(new

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_screen);
        Button reset = (Button) findViewById(R.id.btn_reset);
    });
}

