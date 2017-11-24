package com.qulix.shestakaa.gifsearchermvp.view.main.module;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.qulix.shestakaa.gifsearchermvp.model.main.MainModel;
import com.qulix.shestakaa.gifsearchermvp.model.main.MainModelImpl;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module(includes = MainFragmentModule.Declarations.class)
public class MainFragmentModule {

    private final Fragment mFragment;

    public MainFragmentModule(final Fragment fragment) {
        mFragment = fragment;
    }

    @Provides
    FragmentManager provideFragmentManager() {
        return mFragment.getFragmentManager();
    }

    @Provides
    Context provideContext() {
        return mFragment.getContext();
    }

    @Module
    public interface Declarations {
        @Binds
        MainModel bindsMainModel(MainModelImpl model);
    }
}
