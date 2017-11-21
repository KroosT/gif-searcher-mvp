package com.qulix.shestakaa.gifsearchermvp;

import com.qulix.shestakaa.gifsearchermvp.model.ApplicationSettings;
import com.qulix.shestakaa.gifsearchermvp.model.LoaderFactory;
import com.qulix.shestakaa.gifsearchermvp.model.NetworkStateManager;

import dagger.Module;
import dagger.Provides;

@Module
class AppModule {

    private final GSApplication mApplication;

    public AppModule(final GSApplication application) {
        mApplication = application;
    }

    @Provides
    ApplicationSettings provideApplicationSettings() {
        return new ApplicationSettings(mApplication);
    }

    @Provides
    NetworkStateManager provideNetworkStateManager() {
        return mApplication.getNetworkStateManager();
    }

    @Provides
    LoaderFactory provideLoaderFactory() {
        return new LoaderFactory(mApplication);
    }

}
