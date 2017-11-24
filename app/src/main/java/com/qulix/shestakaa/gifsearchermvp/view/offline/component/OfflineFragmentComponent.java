package com.qulix.shestakaa.gifsearchermvp.view.offline.component;

import com.qulix.shestakaa.gifsearchermvp.view.offline.OfflineFragment;
import com.qulix.shestakaa.gifsearchermvp.view.offline.module.OfflineFragmentModule;
import com.qulix.shestakaa.gifsearchermvp.view.offline.scope.OfflineFragmentScope;

import javax.inject.Singleton;

import dagger.Subcomponent;

@Subcomponent(modules = OfflineFragmentModule.class)
@OfflineFragmentScope
public interface OfflineFragmentComponent {
    void inject(final OfflineFragment fragment);
}
