package com.qulix.shestakaa.gifsearchermvp.view.offline.component;

import com.qulix.shestakaa.gifsearchermvp.view.offline.OfflineFragment;
import com.qulix.shestakaa.gifsearchermvp.view.offline.module.OfflineModule;

import dagger.Subcomponent;

@Subcomponent(modules = OfflineModule.class)
public interface OfflineComponent {
    void inject(final OfflineFragment fragment);
}
