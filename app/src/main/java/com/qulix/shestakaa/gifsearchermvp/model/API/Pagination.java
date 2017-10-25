package com.qulix.shestakaa.gifsearchermvp.model.API;

import com.google.gson.annotations.SerializedName;
import com.qulix.shestakaa.gifsearchermvp.utils.Validator;

public class Pagination {

    @SerializedName("total_count")
    private final int mTotalCount;

    public Pagination() { mTotalCount = 0; }

    public int getTotalCount() {
        Validator.isArgNotNull(mTotalCount, "mTotalCount");
        return mTotalCount;
    }
}
