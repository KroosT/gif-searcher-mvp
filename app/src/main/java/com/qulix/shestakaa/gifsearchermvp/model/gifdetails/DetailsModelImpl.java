package com.qulix.shestakaa.gifsearchermvp.model.gifdetails;

import com.qulix.shestakaa.gifsearchermvp.utils.Validator;

import javax.annotation.Nonnull;

public class DetailsModelImpl implements DetailsModelInterface {

    public DetailsModelImpl() {

    }

    @Override
    public void saveGifOnSdcard(@Nonnull final String url) {
        Validator.isArgNotNull(url, "url");
    }
}
