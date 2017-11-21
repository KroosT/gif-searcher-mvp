package com.qulix.shestakaa.gifsearchermvp;

import com.qulix.shestakaa.gifsearchermvp.view.gifdetails.component.GifDetailsComponent;
import com.qulix.shestakaa.gifsearchermvp.view.gifdetails.module.GifDetailsModule;
import com.qulix.shestakaa.gifsearchermvp.view.main.component.MainFragmentComponent;
import com.qulix.shestakaa.gifsearchermvp.view.main.module.MainFragmentModule;
import com.qulix.shestakaa.gifsearchermvp.view.offline.component.OfflineComponent;
import com.qulix.shestakaa.gifsearchermvp.view.offline.module.OfflineModule;

import dagger.Component;

@Component(modules = AppModule.class)
public interface AppComponent {
    MainFragmentComponent plus(final MainFragmentModule module);
    GifDetailsComponent plus(final GifDetailsModule module);
    OfflineComponent plus(final OfflineModule module);
}
