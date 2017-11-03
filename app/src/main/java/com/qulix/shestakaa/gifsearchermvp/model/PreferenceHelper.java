package com.qulix.shestakaa.gifsearchermvp.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.qulix.shestakaa.gifsearchermvp.R;
import com.qulix.shestakaa.gifsearchermvp.utils.Validator;

import static com.qulix.shestakaa.gifsearchermvp.model.LoadMoreType.BUTTON;

public class PreferenceHelper {

    private final Context mContext;
    private final SharedPreferences mPreferences;

    public PreferenceHelper(final Context context) {
        Validator.isArgNotNull(context, "context");

        mContext = context;
        mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public LoadMoreType getLoadMoreType() {
        final String type = mPreferences.getString(
                mContext.getResources().getString(R.string.pref_key), null);

        Validator.isArgNotNull(type, "type");

        for (final LoadMoreType loadMoreType : LoadMoreType.values()) {
            if (type.equalsIgnoreCase(loadMoreType.getValue())) {
                return loadMoreType;
            }
        }
        return BUTTON;
    }
}
