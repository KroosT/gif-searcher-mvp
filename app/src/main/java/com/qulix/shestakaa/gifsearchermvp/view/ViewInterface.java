package com.qulix.shestakaa.gifsearchermvp.view;

import android.support.annotation.NonNull;

import com.qulix.shestakaa.gifsearchermvp.model.API.Data;

import java.util.List;

public interface ViewInterface {

    void updateData(@NonNull final List<Data> urls);

    void showError();

    void showNoGifsError();
}
