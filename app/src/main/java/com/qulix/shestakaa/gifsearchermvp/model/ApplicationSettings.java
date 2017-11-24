package com.qulix.shestakaa.gifsearchermvp.model;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.qulix.shestakaa.gifsearchermvp.R;
import com.qulix.shestakaa.gifsearchermvp.utils.Validator;

import javax.inject.Inject;
import javax.inject.Singleton;

import static com.qulix.shestakaa.gifsearchermvp.model.LoadMoreType.BUTTON;

@Singleton
public class ApplicationSettings {

    private final SharedPreferences mPreferences;
    private final String mKey;

    @Inject
    public ApplicationSettings(final Application application) {
        Validator.isArgNotNull(application, "application");

        mPreferences = PreferenceManager.getDefaultSharedPreferences(application);
        mKey = application.getResources().getString(R.string.pref_key);
    }

    public LoadMoreType getLoadMoreType() {
        final String type = mPreferences.getString(mKey, null);

        Validator.isArgNotNull(type, "type");

        for (final LoadMoreType loadMoreType : LoadMoreType.values()) {
            if (type.equalsIgnoreCase(loadMoreType.getValue())) {
                return loadMoreType;
            }
        }
        return BUTTON;
    }
}
