package com.qulix.shestakaa.gifsearchermvp.utils;

import javax.annotation.Nonnull;

public interface MainScreenListener {
    void onLoadMoreButtonClicked();
    void onGifClicked(@Nonnull final String arg);
}
