package com.qulix.shestakaa.gifsearchermvp.view.preferences;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.annotation.Nullable;

public class PrefActivity extends PreferenceActivity {

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction()
                            .replace(android.R.id.content, new PrefFragment())
                            .commit();
    }
}
