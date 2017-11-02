package com.qulix.shestakaa.gifsearchermvp.presenter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.qulix.shestakaa.gifsearchermvp.R;
import com.qulix.shestakaa.gifsearchermvp.utils.Validator;
import com.qulix.shestakaa.gifsearchermvp.view.MainFragment;
import com.qulix.shestakaa.gifsearchermvp.view.gifdetails.GifDetailsFragment;
import com.qulix.shestakaa.gifsearchermvp.view.offline.OfflineFragment;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class Router {

    private final FragmentManager mFragmentManager;
    private final View mView;
    private final static String TAG = "fragment";

    public Router(final FragmentManager fragmentManager, final Context context) {
        Validator.isArgNotNull(fragmentManager, "fragmentManager");
        Validator.isArgNotNull(context, "context");

        mFragmentManager = fragmentManager;
        mView = LayoutInflater.from(context)
                              .inflate(R.layout.main, new LinearLayout(context), false);
    }

    public void goToOfflineScreen() {
        final FragmentTransaction transaction =
                mFragmentManager.beginTransaction()
                                .replace(getOfflineContainerViewId(), OfflineFragment.newInstance())
                                .addToBackStack(TAG);
        if (!isSinglePaneMode()) {
            final Fragment fragment = mFragmentManager.findFragmentById(R.id.fragmentDetail);
            if (fragment != null) {
                transaction.remove(fragment);
            }
        }
        transaction.commit();
    }

    public void goToDetailsScreen(final String url) {
        Validator.isArgNotNull(url, "url");
        final FragmentTransaction transaction =
                mFragmentManager.beginTransaction()
                                .replace(getDetailContainerViewId(),
                                         GifDetailsFragment.newInstance(url));
        if (isSinglePaneMode()) {
            transaction.addToBackStack(TAG);
        }
        transaction.commit();
    }

    private int getOfflineContainerViewId() {
        if (isSinglePaneMode()) {
            return R.id.fragment;
        } else {
            return R.id.fragmentMaster;
        }
    }

    private int getDetailContainerViewId() {
        if (isSinglePaneMode()) {
            return R.id.fragment;
        } else {
            return R.id.fragmentDetail;
        }
    }

    private boolean isSinglePaneMode() {
        return mView.findViewById(R.id.fragment) != null;
    }
}