package com.qulix.shestakaa.gifsearchermvp.view.preferences;


import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;

import com.qulix.shestakaa.gifsearchermvp.R;

public class PrefFragment extends PreferenceFragment {


    public PrefFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);
    }
}
