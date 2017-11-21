package com.qulix.shestakaa.gifsearchermvp.view.main.component;

import com.qulix.shestakaa.gifsearchermvp.view.main.MainFragment;
import com.qulix.shestakaa.gifsearchermvp.view.main.module.MainFragmentModule;

import dagger.Subcomponent;


@Subcomponent(modules = MainFragmentModule.class)
public interface MainFragmentComponent {
    void inject(final MainFragment fragment);
}
