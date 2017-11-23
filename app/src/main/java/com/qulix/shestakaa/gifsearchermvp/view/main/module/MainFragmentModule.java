package com.qulix.shestakaa.gifsearchermvp.view.main.module;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;

import com.qulix.shestakaa.gifsearchermvp.model.main.MainModelImpl;
import com.qulix.shestakaa.gifsearchermvp.presenter.main.Presenter;
import com.qulix.shestakaa.gifsearchermvp.presenter.main.Router;

import dagger.Module;
import dagger.Provides;

@Module
public class MainFragmentModule {

    private final Fragment mFragment;

    public MainFragmentModule(final Fragment fragment) {
        mFragment = fragment;
    }

    @Provides
    Presenter providePresenter(final MainModelImpl model, final Router router) {
        return new Presenter(model, router);
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
