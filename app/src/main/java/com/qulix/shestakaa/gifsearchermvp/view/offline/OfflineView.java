package com.qulix.shestakaa.gifsearchermvp.view.offline;

import java.util.List;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public interface OfflineView {
    void showAvailableGifs(final List<byte[]> bytes);

    void showOnlineModeAvailable();

    void dismissOnlineModeAvailable();
}
