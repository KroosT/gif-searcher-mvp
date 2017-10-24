package com.qulix.shestakaa.gifsearchermvp.presenter.gifdetails;

import com.qulix.shestakaa.gifsearchermvp.model.gifdetails.DetailsModelInterface;
import com.qulix.shestakaa.gifsearchermvp.utils.Cancelable;
import com.qulix.shestakaa.gifsearchermvp.utils.Downloadable;
import com.qulix.shestakaa.gifsearchermvp.utils.Validator;
import com.qulix.shestakaa.gifsearchermvp.view.gifdetails.DetailsViewInterface;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class DetailsPresenter {

    private final DetailsModelInterface mModel;
    private final DetailsViewInterface mView;
    private Cancelable mCancelable;

    public DetailsPresenter(final DetailsModelInterface model,
                            final DetailsViewInterface view) {
        Validator.isArgNotNull(model, "model");
        Validator.isArgNotNull(view, "view");

        mView = view;
        mModel = model;
    }

    public void onShowGif(final String url) {
        Validator.isArgNotNull(url, "url");
        mView.showGif(url);
    }

    public void onSaveGif(final String url) {
        Validator.isArgNotNull(url, "url");

        final Downloadable downloadable = new Downloadable() {
            @Override
            public void onDownload(final boolean result) {
                if (result) {
                    mView.showSuccess();
                } else {
                    mView.showError();
                }
            }
        };

        mCancelable = mModel.saveGifByUrl(url, downloadable);
    }

    public void onCancelSaving() {
        if (mCancelable != null) {
            mCancelable.onCancel();
        }
    }
}
