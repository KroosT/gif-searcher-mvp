package com.qulix.shestakaa.gifsearchermvp.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.qulix.shestakaa.gifsearchermvp.R;

public class MainActivity extends AppCompatActivity {

    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mFragmentManager = getSupportFragmentManager();
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setCustomView(R.layout.custom_action_bar_title);
        }

        setupFragments();
    }

    private void setupFragments() {
        final Fragment fragment = mFragmentManager.findFragmentById(R.id.fragment);
        final Fragment fragmentMaster = mFragmentManager.findFragmentById(R.id.fragmentMaster);
        final Fragment fragmentDetail = mFragmentManager.findFragmentById(R.id.fragmentDetail);

        if (findViewById(R.id.dual_pane) == null) {
            if (fragmentMaster != null) {
                mFragmentManager.beginTransaction().remove(fragmentMaster).commit();
            }
            if (fragmentDetail != null) {
                mFragmentManager.beginTransaction().remove(fragmentDetail).commit();
            }
            mFragmentManager.beginTransaction()
                            .replace(R.id.fragment, MainFragment.newInstance())
                            .commit();
        } else {
            if (fragment != null) {
                mFragmentManager.beginTransaction().remove(fragment).commit();
            }
            mFragmentManager.beginTransaction()
                            .replace(R.id.fragmentMaster, MainFragment.newInstance())
                            .commit();

        }
    }

    @Override
    public void onBackPressed() {
        final FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() != 0) {
            fm.popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}
