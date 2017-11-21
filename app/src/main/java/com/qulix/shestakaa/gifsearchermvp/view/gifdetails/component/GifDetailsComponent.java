package com.qulix.shestakaa.gifsearchermvp.view.gifdetails.component;

import com.qulix.shestakaa.gifsearchermvp.view.gifdetails.GifDetailsFragment;
import com.qulix.shestakaa.gifsearchermvp.view.gifdetails.module.GifDetailsModule;

import dagger.Subcomponent;

@Subcomponent(modules = GifDetailsModule.class)
public interface GifDetailsComponent {
    void inject(final GifDetailsFragment fragment);
}
