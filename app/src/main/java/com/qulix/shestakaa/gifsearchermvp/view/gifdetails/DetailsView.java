package com.qulix.shestakaa.gifsearchermvp.view.gifdetails;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public interface DetailsView {

    void showGif(final String url);
    void showSuccess();
    void showError();
}
