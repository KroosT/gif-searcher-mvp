package com.qulix.shestakaa.gifsearchermvp.utils;

import java.util.List;

public interface Loadable {
    void onDataLoaded(final List<byte[]> data);
}
