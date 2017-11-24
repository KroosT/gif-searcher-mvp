package com.qulix.shestakaa.gifsearchermvp.view.gifdetails.module;

import android.support.v4.app.FragmentManager;

import com.qulix.shestakaa.gifsearchermvp.model.gifdetails.DetailsModel;
import com.qulix.shestakaa.gifsearchermvp.model.gifdetails.DetailsModelImpl;
import com.qulix.shestakaa.gifsearchermvp.presenter.gifdetails.DetailsPresenter;
import com.qulix.shestakaa.gifsearchermvp.presenter.gifdetails.DetailsRouter;
import com.qulix.shestakaa.gifsearchermvp.view.gifdetails.GifDetailsFragment;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module(includes = GifDetailsFragmentModule.Declarations.class)
public class GifDetailsFragmentModule {

    private final GifDetailsFragment mFragment;

    public GifDetailsFragmentModule(final GifDetailsFragment fragment) {
        mFragment = fragment;
    }

    @Provides
    FragmentManager provideFragmentManager() {
        return mFragment.getFragmentManager();
    }

    @Module
    public interface Declarations {
        @Binds
        DetailsModel bindsMainModel(DetailsModelImpl model);
    }

}
