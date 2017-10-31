package com.qulix.shestakaa.gifsearchermvp.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.qulix.shestakaa.gifsearchermvp.R;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setCustomView(R.layout.custom_action_bar_title);
        }

        setupFragments();
    }

    private void setupFragments() {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        final Fragment fragment = fragmentManager.findFragmentById(R.id.fragment);
        final Fragment fragmentMaster = fragmentManager.findFragmentById(R.id.fragmentMaster);
        final Fragment fragmentDetail = fragmentManager.findFragmentById(R.id.fragmentDetail);

        if (findViewById(R.id.dual_pane) == null) {
            if (fragmentMaster != null) {
                fragmentManager.beginTransaction().remove(fragmentMaster).commit();
            }
            if (fragmentDetail != null) {
                fragmentManager.beginTransaction().remove(fragmentDetail).commit();
            }
            if (fragment == null) {
                fragmentManager.beginTransaction()
                               .add(R.id.fragment, MainFragment.newInstance())
                               .commit();
            }
        } else {
            if (fragment != null) {
                fragmentManager.beginTransaction().remove(fragment).commit();
            }
            if (fragmentMaster == null) {
                fragmentManager.beginTransaction()
                               .add(R.id.fragmentMaster, MainFragment.newInstance())
                               .commit();
            }
            if (fragmentDetail == null) {
                fragmentManager.beginTransaction()
                               .add(R.id.fragmentDetail, new Fragment())
                               .commit();
            }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
