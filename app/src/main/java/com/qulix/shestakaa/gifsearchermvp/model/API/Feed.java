package com.qulix.shestakaa.gifsearchermvp.model.API;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public final class Feed {

    @SerializedName("data")
    private final List<Data> mData;

    public Feed(final List<Data> data) {
        mData = new ArrayList<>(data);
    }

    public List<Data> getData() {
        return Collections.unmodifiableList(mData);
    }
}
