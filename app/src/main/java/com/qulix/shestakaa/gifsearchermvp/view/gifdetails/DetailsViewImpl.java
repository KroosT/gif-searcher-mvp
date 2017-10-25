package com.qulix.shestakaa.gifsearchermvp.view.gifdetails;

import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.qulix.shestakaa.gifsearchermvp.R;
import com.qulix.shestakaa.gifsearchermvp.presenter.gifdetails.DetailsPresenter;
import com.qulix.shestakaa.gifsearchermvp.utils.Validator;

import javax.annotation.ParametersAreNonnullByDefault;

import static com.qulix.shestakaa.gifsearchermvp.utils.StringConstants.CONNECTION_ERROR;
import static com.qulix.shestakaa.gifsearchermvp.utils.StringConstants.ERROR_MESSAGE;
import static com.qulix.shestakaa.gifsearchermvp.utils.StringConstants.GO_OFFLINE;
import static com.qulix.shestakaa.gifsearchermvp.utils.StringConstants.SUCCESS_MESSAGE;

@ParametersAreNonnullByDefault
public class DetailsViewImpl implements DetailsView {

    private final View mView;
    private final String mUrl;
    private final DetailsPresenter mDetailsPresenter;
    private final Snackbar mSnackbar;

    public DetailsViewImpl(final View view, final String url, final DetailsPresenter presenter) {
        Validator.isArgNotNull(view, "view");
        Validator.isArgNotNull(url, "url");
        Validator.isArgNotNull(presenter, "presenter");

        mView = view;
        mUrl = url;
        mDetailsPresenter = presenter;
        mDetailsPresenter.onViewBind(this);

        mDetailsPresenter.onShowGif(mUrl);

        final Button buttonSave = mView.findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                mDetailsPresenter.onSaveGif(mUrl);
            }
        });

        final View.OnClickListener onSnackBarClick = new View.OnClickListener() {
            @Override
            public void onClick(final android.view.View v) {
                mDetailsPresenter.onSwitchToOffline();
            }
        };
        mSnackbar = Snackbar.make(mView, CONNECTION_ERROR, Snackbar.LENGTH_INDEFINITE)
                            .setAction(GO_OFFLINE, onSnackBarClick);
    }

    @Override
    public void showGif(final String url) {
        Validator.isArgNotNull(url, "url");
        final ImageView imageView = mView.findViewById(R.id.detailsImageView);
        Glide.with(mView.getContext())
             .load(url)
             .apply(RequestOptions.placeholderOf(R.mipmap.ic_downloading))
             .into(imageView);
    }

    @Override
    public void showSuccess() {
        Toast.makeText(mView.getContext(), SUCCESS_MESSAGE, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError() {
        Toast.makeText(mView.getContext(), ERROR_MESSAGE,
                       Toast.LENGTH_SHORT).show();
    }

    public void showOfflineModeSuggestion() {
        mSnackbar.show();
    }

    public void dismissOfflineModeSuggestion() {
        mSnackbar.dismiss();
    }
}
