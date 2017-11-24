package com.qulix.shestakaa.gifsearchermvp.view.main.component;

import com.qulix.shestakaa.gifsearchermvp.view.main.MainFragment;
import com.qulix.shestakaa.gifsearchermvp.view.main.module.MainFragmentModule;
import com.qulix.shestakaa.gifsearchermvp.view.main.scope.MainFragmentScope;

import dagger.Subcomponent;

@Subcomponent(modules = MainFragmentModule.class)
@MainFragmentScope
public interface MainFragmentComponent {
    void inject(final MainFragment fragment);
}
