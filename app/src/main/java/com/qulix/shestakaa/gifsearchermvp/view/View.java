package com.qulix.shestakaa.gifsearchermvp.view;

import com.qulix.shestakaa.gifsearchermvp.model.API.Data;

import java.util.List;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public interface View {

    void updateData(final List<Data> urls);

    void showError();

    void showNoGifsError();

}
