package com.qulix.shestakaa.gifsearchermvp.presenter.gifdetails;

import com.qulix.shestakaa.gifsearchermvp.model.gifdetails.DetailsModel;
import com.qulix.shestakaa.gifsearchermvp.utils.Cancelable;
import com.qulix.shestakaa.gifsearchermvp.utils.Downloadable;
import com.qulix.shestakaa.gifsearchermvp.utils.Validator;
import com.qulix.shestakaa.gifsearchermvp.view.gifdetails.DetailsView;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class DetailsPresenter {

    private final DetailsModel mModel;
    private DetailsView mView;
    private Cancelable mRequest;

    public DetailsPresenter(final DetailsModel model) {
        Validator.isArgNotNull(model, "model");
        mModel = model;
    }

    public void onViewBind(final DetailsView view) {
        Validator.isArgNotNull(view, "view");
        mView = view;
    }

    public void onViewUnbind() {
        mView = null;
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

        mRequest = mModel.saveGifByUrl(url, downloadable);
    }

    public void onCancelSaving() {
        if (mRequest != null) {
            mRequest.onCancel();
        }
    }
}
