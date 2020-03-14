package com.example.a40397559;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
/*
* This activity will display for 3 (3000ms) seconds before changing the activity to
* HomeActivity.java.
 */
public class MainActivity extends AppCompatActivity {

    private static int splashTimeout = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Creates intent to change to a new activity
                Intent homeIntent = new Intent(MainActivity.this, HomeActivity.class);
                // Changes to new activity
                startActivity(homeIntent);
                finish();
            }

        }, splashTimeout);
    }
}
