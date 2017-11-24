package com.qulix.shestakaa.gifsearchermvp;

import com.qulix.shestakaa.gifsearchermvp.view.gifdetails.component.GifDetailsFragmentComponent;
import com.qulix.shestakaa.gifsearchermvp.view.gifdetails.module.GifDetailsFragmentModule;
import com.qulix.shestakaa.gifsearchermvp.view.main.component.MainFragmentComponent;
import com.qulix.shestakaa.gifsearchermvp.view.main.module.MainFragmentModule;
import com.qulix.shestakaa.gifsearchermvp.view.offline.component.OfflineFragmentComponent;
import com.qulix.shestakaa.gifsearchermvp.view.offline.module.OfflineFragmentModule;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = AppModule.class)
@Singleton
public interface AppComponent {
    void inject(GSApplication application);
    MainFragmentComponent plus(final MainFragmentModule module);
    GifDetailsFragmentComponent plus(final GifDetailsFragmentModule module);
    OfflineFragmentComponent plus(final OfflineFragmentModule module);
}
