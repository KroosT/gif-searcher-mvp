package com.qulix.shestakaa.gifsearchermvp.presenter;

import android.support.v4.app.FragmentManager;

import com.qulix.shestakaa.gifsearchermvp.R;
import com.qulix.shestakaa.gifsearchermvp.utils.Validator;
import com.qulix.shestakaa.gifsearchermvp.view.gifdetails.GifDetailsFragment;
import com.qulix.shestakaa.gifsearchermvp.view.offline.OfflineFragment;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class Router {

    private final FragmentManager mFragmentManager;
    private final static String FRAGMENT_TAG = "offline_fragment";

    public Router(final FragmentManager fragmentManager) {
        Validator.isArgNotNull(fragmentManager, "fragmentManager");
        mFragmentManager = fragmentManager;
    }


    public void goToOfflineScreen() {
        mFragmentManager.beginTransaction()
                        .replace(R.id.fragment, OfflineFragment.newInstance())
                        .addToBackStack(FRAGMENT_TAG)
                        .commit();
    }

    public void goToDetailsScreen(final String url) {
        Validator.isArgNotNull(url, "url");
        mFragmentManager.beginTransaction()
                        .replace(R.id.fragment, GifDetailsFragment.newInstance(url))
                        .addToBackStack(FRAGMENT_TAG)
                        .commit();
    }
}