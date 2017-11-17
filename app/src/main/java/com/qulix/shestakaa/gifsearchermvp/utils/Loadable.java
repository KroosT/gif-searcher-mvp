package com.qulix.shestakaa.gifsearchermvp.utils;

import java.util.List;

import javax.annotation.Nonnull;

public interface Loadable {
    void onDataLoaded(@Nonnull final List<byte[]> data);
}
