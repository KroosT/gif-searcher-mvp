package com.qulix.shestakaa.gifsearchermvp.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.qulix.shestakaa.gifsearchermvp.R;

public class Router {

    private static final String FRAGMENT_TAG = "fragment";
    private static final Router sRouter = new Router();
    private FragmentManager mFragmentManager;

    private Router() {

    }

    public static Router getRouter() {
        return sRouter;
    }

    public void setFragmentManager(final FragmentManager fragmentManager) {
        Validator.isArgNotNull(fragmentManager, "fragmentManager");
        mFragmentManager = fragmentManager;
    }

    public void startFragment(final Fragment fragment) {
        Validator.isArgNotNull(fragment, "fragment");
        mFragmentManager.beginTransaction()
                        .replace(R.id.fragment, fragment)
                        .addToBackStack(FRAGMENT_TAG)
                        .commit();
    }

}
