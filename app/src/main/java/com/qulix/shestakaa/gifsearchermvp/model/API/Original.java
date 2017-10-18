package com.qulix.shestakaa.gifsearchermvp.model.API;

import com.google.gson.annotations.SerializedName;
import com.qulix.shestakaa.gifsearchermvp.utils.Validator;

import javax.annotation.Nonnull;

public class Original {

    @Nonnull
    @SerializedName("url")
    private final String mUrl;

    public Original() {
        mUrl = "";
    }

    public String getUrl() {
        Validator.isArgNotNull(mUrl, "mUrl");
        return mUrl;
    }
}