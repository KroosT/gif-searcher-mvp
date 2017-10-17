package com.qulix.shestakaa.gifsearchermvp.model.API;

import com.google.gson.annotations.SerializedName;
import com.qulix.shestakaa.gifsearchermvp.utils.Validator;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

public final class Feed {

    @SerializedName("data")
    private final List<Data> mData;

    public Feed(@Nonnull final List<Data> data) {
        Validator.isArgNotNull(data, "data");
        mData = new ArrayList<>(data);
    }

    public List<Data> getData() {
        return new ArrayList<>(mData);
    }
}
