package com.qulix.shestakaa.gifsearchermvp.model.API;

import com.google.gson.annotations.SerializedName;
import com.qulix.shestakaa.gifsearchermvp.utils.Validator;

import javax.annotation.Nullable;

public class Images {

    @SerializedName("original")
    private final Original mOriginal;

    public Images() {
        mOriginal = new Original();
    }

    public Original getOriginal() {
        Validator.isArgNotNull(mOriginal, "mOriginal");
        return mOriginal;
    }
}
