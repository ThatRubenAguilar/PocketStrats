package com.innovations.aguilar.pocketstrats;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

public class LoadActivity extends AppCompatActivity {

    ProgressBar progBar;
    String logMsg = "PocketStrats : ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        progBar = (ProgressBar)findViewById(R.id.progressBar);
        progBar.postDelayed(new SwitchRunnable(this), 3000);
        Log.d(logMsg, "onCreate()");
    }

    public void nextFlow() {
        setContentView(R.layout.activity_main);
        Log.d(logMsg, "nextFlow()");
    }

    class SwitchRunnable implements Runnable {

        private LoadActivity activity;

        public SwitchRunnable(LoadActivity activity) {

            this.activity = activity;
        }

        @Override
        public void run() {
            Log.d(logMsg, "switchRunnable");
            activity.nextFlow();
        }
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
}
