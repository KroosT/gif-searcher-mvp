package com.qulix.shestakaa.gifsearchermvp.model.API;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Nullable;

public class Images {

    @SerializedName("original")
    private final Original mOriginal;

    Images(@Nullable final Images images) {
        if (images == null) {
            mOriginal = new Original();
        } else {
            mOriginal = images.getOriginal();
        }
    }

    public Original getOriginal() {
        return new Original(mOriginal);
    }
}
