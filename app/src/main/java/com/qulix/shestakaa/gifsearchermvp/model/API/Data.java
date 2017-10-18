package com.qulix.shestakaa.gifsearchermvp.model.API;

import com.google.gson.annotations.SerializedName;
import com.qulix.shestakaa.gifsearchermvp.utils.Validator;

public class Data {

    @SerializedName("images")
    private final Images mImages;

    public Data() {
        mImages = new Images();
    }

    public Images getImages() {
        Validator.isArgNotNull(mImages, "mImages");
        return mImages;
    }

}