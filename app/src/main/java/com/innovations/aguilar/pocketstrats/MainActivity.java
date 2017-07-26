package com.innovations.aguilar.pocketstrats;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;

public class MainActivity extends AppCompatActivity {

    String logMsg = "PocketStrats : ";
    Container container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.main_pane_container);
            container = (Container) findViewById(R.id.layout_main_container);
            Log.d(logMsg, "onCreate()");
        }
    }

    public Container getContainer() {
        return container;
    }

    @Override
    public void onBackPressed() {
        boolean handled = container.onBackPressed();
        if (!handled)
            finish();
    }
}
