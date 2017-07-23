package com.innovations.aguilar.pocketstrats;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

/**
 * Created by Ruben on 7/3/2017.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button modes;
    String logMsg = "PocketStrats : ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main_layout);
        modes=(Button)findViewById(R.id.button_modes);
        modes.setOnClickListener(this);
        Log.d(logMsg, "onCreate()");
    }

    public void nextFlow() {
        setContentView(R.layout.mode_selection_layout);
        Log.d(logMsg, "nextFlow()");
    }


    /** Called when the activity is about to become visible. */
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(logMsg, "The onStart() event");
    }

    /** Called when the activity has become visible. */
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(logMsg, "The onResume() event");
    }

    /** Called when another activity is taking focus. */
    @Override
    protected void onPause() {
        super.onPause();
        Log.d(logMsg, "The onPause() event");
    }

    /** Called when the activity is no longer visible. */
    @Override
    protected void onStop() {
        super.onStop();
        Log.d(logMsg, "The onStop() event");
    }

    /** Called just before the activity is destroyed. */
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(logMsg, "The onDestroy() event");
    }

    @Override
    public void onClick(View view) {
        Log.d(logMsg, "The onClick() event");
        nextFlow();
    }
}
