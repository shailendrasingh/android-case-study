package com.target.dealbrowserpoc.dealbrowser.modules.deals;

import android.app.Activity;
import android.os.Bundle;

import com.target.dealbrowserpoc.dealbrowser.R;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Target");
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new DealListFragment())
                    .commit();
        }
    }

}
