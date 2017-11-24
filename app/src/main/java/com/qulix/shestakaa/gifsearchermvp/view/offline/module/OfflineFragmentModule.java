package com.qulix.shestakaa.gifsearchermvp.view.offline.module;

import android.content.Context;
import android.support.v4.app.FragmentManager;

import com.qulix.shestakaa.gifsearchermvp.model.offline.OfflineModel;
import com.qulix.shestakaa.gifsearchermvp.model.offline.OfflineModelImpl;
import com.qulix.shestakaa.gifsearchermvp.view.offline.OfflineFragment;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module(includes = OfflineFragmentModule.Declarations.class)
public class OfflineFragmentModule {

    private final OfflineFragment mFragment;

    public OfflineFragmentModule(final OfflineFragment fragment) {
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
        OfflineModel bindsMainModel(OfflineModelImpl model);
    }
}
