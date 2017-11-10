package com.innovations.aguilar.pocketstrats.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.innovations.aguilar.pocketstrats.R;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainActivity extends AppCompatActivity {
    protected static Logger log = LoggerFactory.getLogger(MainActivity.class);

    Container container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.main_pane_container);
            container = (Container) findViewById(R.id.layout_main_container);
            log.debug("onCreate()");
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

    public static Supplier<Container> generateContainerRef(final View v) {
        return Suppliers.memoize(new Supplier<Container>() {
            @Override
            public Container get() {
                Container containerRef = (Container)((MainActivity)v.getContext()).findViewById(R.id.layout_main_container);
                Preconditions.checkNotNull(containerRef,
                        "containerRef is null, ensure container is not referenced before onCreate finishes (e.g. onFinishInflate).");
                return containerRef;
            }
        });
    }
}
