package com.qulix.shestakaa.gifsearchermvp.view.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

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
        final FragmentTransaction transaction = mFragmentManager.beginTransaction();

        if (findViewById(R.id.dual_pane) == null) {
            if (fragmentMaster != null) {
                transaction.remove(fragmentMaster);
            }
            if (fragmentDetail != null) {
                transaction.remove(fragmentDetail);
            }
            transaction.replace(R.id.fragment, MainFragment.newInstance());
        } else {
            if (fragment != null) {
                transaction.remove(fragment);
            }
            transaction.replace(R.id.fragmentMaster, MainFragment.newInstance());
        }
        transaction.commit();
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
