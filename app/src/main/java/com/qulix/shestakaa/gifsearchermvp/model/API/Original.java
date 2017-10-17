package com.qulix.shestakaa.gifsearchermvp.model.API;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Nullable;

public class Original {

    @SerializedName("url")
    private final String mUrl;

    Original() {
        mUrl = "";
    }

    Original(@Nullable final Original original) {
        if (original == null) {
            mUrl = "";
        } else {
            mUrl = original.getUrl();
        }
    }

    public String getUrl() {
        if (mUrl == null) {
            return "";
        }
        return mUrl;
    }
}