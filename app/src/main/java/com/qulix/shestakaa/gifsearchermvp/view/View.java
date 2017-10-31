package com.qulix.shestakaa.gifsearchermvp.view;

import com.qulix.shestakaa.gifsearchermvp.utils.AdapterData;

import java.util.List;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public interface View {

    void updateData(final AdapterData urls);
    void showError();
    void showNoGifsError();
    void showOfflineModeSuggestion();
    void dismissOfflineModeSuggestion();
    void showButton();
    void showProgressBar();
    void showDataEnded();
}
