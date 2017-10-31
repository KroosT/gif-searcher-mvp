package com.qulix.shestakaa.gifsearchermvp.presenter.offline;

import android.support.v4.app.FragmentManager;

import com.qulix.shestakaa.gifsearchermvp.R;
import com.qulix.shestakaa.gifsearchermvp.utils.Validator;
import com.qulix.shestakaa.gifsearchermvp.view.MainFragment;
import com.qulix.shestakaa.gifsearchermvp.view.gifdetails.GifDetailsFragment;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class OfflineRouter {

    private final FragmentManager mFragmentManager;
    private final static String TAG = "fragment";

    public OfflineRouter(final FragmentManager fragmentManager) {
        Validator.isArgNotNull(fragmentManager, "fragmentManager");

        mFragmentManager = fragmentManager;
    }


    public void goToMainScreen() {
        mFragmentManager.beginTransaction()
                        .replace(R.id.fragment, MainFragment.newInstance())
                        .addToBackStack(TAG)
                        .commit();
    }

    public void goToDetailsScreen(final String url) {
        Validator.isArgNotNull(url, "url");
        mFragmentManager.beginTransaction()
                        .replace(R.id.fragment, GifDetailsFragment.newInstance(url))
                        .addToBackStack(TAG)
                        .commit();
    }
}