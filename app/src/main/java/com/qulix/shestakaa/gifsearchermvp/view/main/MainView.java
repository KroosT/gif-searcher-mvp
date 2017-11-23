package com.qulix.shestakaa.gifsearchermvp.view.main;

import com.qulix.shestakaa.gifsearchermvp.utils.AdapterData;

import java.util.List;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public interface MainView {

    void updateData(final AdapterData urls);
    void showError();
    void showNoGifsError();
    void showOfflineModeSuggestion();
    void dismissOfflineModeSuggestion();
    void showButton();
    void showProgressBar();
    void showMainProgressBar();
    void showDataEnded();
    void showBlankScreen();
}
