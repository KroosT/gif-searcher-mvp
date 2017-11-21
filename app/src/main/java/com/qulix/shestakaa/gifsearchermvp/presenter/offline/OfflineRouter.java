package com.qulix.shestakaa.gifsearchermvp.presenter.offline;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.qulix.shestakaa.gifsearchermvp.R;
import com.qulix.shestakaa.gifsearchermvp.utils.Validator;
import com.qulix.shestakaa.gifsearchermvp.view.gifdetails.GifDetailsFragment;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.inject.Inject;

@ParametersAreNonnullByDefault
public class OfflineRouter {

    private final FragmentManager mFragmentManager;
    private final View mView;
    private final static String TAG = "fragment";

    @Inject
    public OfflineRouter(final FragmentManager fragmentManager, final Context context) {
        Validator.isArgNotNull(fragmentManager, "fragmentManager");
        Validator.isArgNotNull(context, "context");

        mFragmentManager = fragmentManager;
        mView = LayoutInflater.from(context)
                              .inflate(R.layout.main, new LinearLayout(context), false);
    }


    public void goToMainScreen() {
        mFragmentManager.popBackStack();
    }

    public void goToDetailsScreen(final String url) {
        Validator.isArgNotNull(url, "url");
        mFragmentManager.beginTransaction()
                        .replace(R.id.fragment, GifDetailsFragment.newInstance(url))
                        .addToBackStack(TAG)
                        .commit();
    }

    private int getMainContainerViewId() {
        if (isSinglePaneMode()) {
            return R.id.fragment;
        } else {
            return R.id.fragmentMaster;
        }
    }

    private boolean isSinglePaneMode() {
        return mView.findViewById(R.id.fragment) != null;
    }
}