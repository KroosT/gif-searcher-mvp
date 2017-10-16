package com.qulix.shestakaa.gifsearchermvp.model.API;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class Feed {

    @SerializedName("data")
    private final List<Data> mData;

    public Feed(@Nullable final List<Data> data) {
        mData = data;
    }

    public List<Data> getData() {
        if (mData == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(mData);
    }
}
