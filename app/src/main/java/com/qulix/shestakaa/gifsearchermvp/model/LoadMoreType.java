package com.qulix.shestakaa.gifsearchermvp.model;

public enum LoadMoreType {
    SCROLL("0"), BUTTON("1");

    private final String mValue;

    LoadMoreType(final String value) {
        mValue = value;
    }

    public String getValue() {
        return mValue;
    }
}
