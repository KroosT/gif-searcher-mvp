package com.qulix.shestakaa.gifsearchermvp.view.gifdetails.module;

import android.support.v4.app.FragmentManager;

import com.qulix.shestakaa.gifsearchermvp.model.gifdetails.DetailsModelImpl;
import com.qulix.shestakaa.gifsearchermvp.presenter.gifdetails.DetailsPresenter;
import com.qulix.shestakaa.gifsearchermvp.presenter.gifdetails.DetailsRouter;
import com.qulix.shestakaa.gifsearchermvp.view.gifdetails.GifDetailsFragment;

import dagger.Module;
import dagger.Provides;

@Module
public class GifDetailsModule {

    private final GifDetailsFragment mFragment;

    public GifDetailsModule(final GifDetailsFragment fragment) {
        mFragment = fragment;
    }

    @Provides
    DetailsPresenter provideDetailsPresenter(final DetailsModelImpl model, final DetailsRouter router) {
        return new DetailsPresenter(model, router);
    }

    @Provides
    FragmentManager provideFragmentManager() {
        return mFragment.getFragmentManager();
    }

}
