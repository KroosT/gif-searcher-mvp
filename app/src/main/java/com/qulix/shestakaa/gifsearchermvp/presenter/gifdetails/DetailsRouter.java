package com.qulix.shestakaa.gifsearchermvp.presenter.gifdetails;

import android.support.v4.app.FragmentManager;

import com.qulix.shestakaa.gifsearchermvp.R;
import com.qulix.shestakaa.gifsearchermvp.utils.Validator;
import com.qulix.shestakaa.gifsearchermvp.view.main.MainFragment;
import com.qulix.shestakaa.gifsearchermvp.view.offline.OfflineFragment;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.inject.Inject;

@ParametersAreNonnullByDefault
public class DetailsRouter {

    private final FragmentManager mFragmentManager;
    private final static String TAG = "fragment";

    @Inject
    public DetailsRouter(final FragmentManager fragmentManager) {
        Validator.isArgNotNull(fragmentManager, "fragmentManager");

        mFragmentManager = fragmentManager;
    }


    public void goToOfflineScreen() {
        mFragmentManager.beginTransaction()
                        .replace(R.id.fragment, OfflineFragment.newInstance())
                        .addToBackStack(TAG)
                        .commit();
    }

    public void goToMainScreen() {
        mFragmentManager.beginTransaction()
                        .replace(R.id.fragment, MainFragment.newInstance())
                        .addToBackStack(TAG)
                        .commit();
    }
}