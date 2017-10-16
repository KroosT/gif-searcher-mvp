package com.qulix.shestakaa.gifsearchermvp.model.API;

import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("images")
    private Images mImages;

    public Images getImages() {
        return new Images(mImages);
    }

}