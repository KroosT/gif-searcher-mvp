package com.qulix.shestakaa.gifsearchermvp.view.gifdetails.component;

import com.qulix.shestakaa.gifsearchermvp.view.gifdetails.GifDetailsFragment;
import com.qulix.shestakaa.gifsearchermvp.view.gifdetails.module.GifDetailsFragmentModule;
import com.qulix.shestakaa.gifsearchermvp.view.gifdetails.scope.DetailsFragmentScope;

import javax.inject.Singleton;

import dagger.Subcomponent;

@Subcomponent(modules = GifDetailsFragmentModule.class)
@DetailsFragmentScope
public interface GifDetailsFragmentComponent {
    void inject(final GifDetailsFragment fragment);
}
