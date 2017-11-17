package com.qulix.shestakaa.gifsearchermvp.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.qulix.shestakaa.gifsearchermvp.R;
import com.qulix.shestakaa.gifsearchermvp.utils.Validator;

import static com.qulix.shestakaa.gifsearchermvp.model.LoadMoreType.BUTTON;

public class ApplicationSettings {

    private final SharedPreferences mPreferences;
    private final String mKey;

    public ApplicationSettings(final Context context) {
        Validator.isArgNotNull(context, "context");

        mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        mKey = context.getResources().getString(R.string.pref_key);
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
