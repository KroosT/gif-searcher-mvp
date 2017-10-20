package com.qulix.shestakaa.gifsearchermvp.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.qulix.shestakaa.gifsearchermvp.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        final MainFragment mainFragment = (MainFragment) getSupportFragmentManager()
                                                            .findFragmentById(R.id.mainFragment);
        if (mainFragment == null) {
            getSupportFragmentManager().beginTransaction()
                                       .add(R.id.mainFragment, new MainFragment())
                                       .commit();
        }
    }
}
