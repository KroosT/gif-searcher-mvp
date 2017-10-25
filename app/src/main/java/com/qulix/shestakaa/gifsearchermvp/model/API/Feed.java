package com.qulix.shestakaa.gifsearchermvp.model.API;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public final class Feed {

    @SerializedName("data")
    private final List<Data> mData;

    @SerializedName("pagination")
    private final Pagination mPagination;

    public Feed(final List<Data> data, final Pagination pagination) {
        mData = new ArrayList<>(data);
        mPagination = pagination;
    }

    public List<Data> getData() {
        return Collections.unmodifiableList(mData);
    }

    public Pagination getPagination() {
        return mPagination;
    }
}
