package com.qulix.shestakaa.gifsearchermvp.view.offline.module;

import android.content.Context;
import android.support.v4.app.FragmentManager;

import com.qulix.shestakaa.gifsearchermvp.model.offline.OfflineModelImpl;
import com.qulix.shestakaa.gifsearchermvp.presenter.offline.OfflinePresenter;
import com.qulix.shestakaa.gifsearchermvp.presenter.offline.OfflineRouter;
import com.qulix.shestakaa.gifsearchermvp.view.offline.OfflineFragment;

import dagger.Module;
import dagger.Provides;

@Module
public class OfflineModule {

    private final OfflineFragment mFragment;

    public OfflineModule(final OfflineFragment fragment) {
        mFragment = fragment;
    }

    @Provides
    OfflinePresenter provideOfflinePresenter(final OfflineModelImpl model, final OfflineRouter router) {
        return new OfflinePresenter(model, router);
    }

    @Provides
    FragmentManager provideFragmentManager() {
        return mFragment.getFragmentManager();
    }

    @Provides
    Context provideContext() {
        return mFragment.getContext();
    }
}
