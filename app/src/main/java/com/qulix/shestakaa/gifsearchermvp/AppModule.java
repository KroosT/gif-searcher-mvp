package com.qulix.shestakaa.gifsearchermvp;

import android.app.Application;

import dagger.Module;
import dagger.Provides;

@Module
class AppModule {

    private final GSApplication mApplication;

    public AppModule(final GSApplication application) {
        mApplication = application;
    }

    @Provides
    Application provideApplicationContext() {
        return mApplication;
    }

}
