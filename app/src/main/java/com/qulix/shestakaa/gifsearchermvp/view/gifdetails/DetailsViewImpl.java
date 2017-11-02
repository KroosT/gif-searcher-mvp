package com.qulix.shestakaa.gifsearchermvp.view.gifdetails;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
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

        final Button buttonSave = mView.findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                mDetailsPresenter.onSaveButtonClicked(mUrl);
            }
        });

        final View.OnClickListener onSnackBarClick = new View.OnClickListener() {
            @Override
            public void onClick(final android.view.View v) {
                mDetailsPresenter.onSnackBarClicked();
            }
        };

        final Context context = mView.getContext();
        mSnackbar = Snackbar.make(mView, R.string.connection_error, Snackbar.LENGTH_INDEFINITE)
                            .setAction(R.string.go_offline, onSnackBarClick)
                            .setActionTextColor(ContextCompat.getColor(context,
                                                                       R.color.colorPrimary));
        mDetailsPresenter.bindView(this, url);
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
        Toast.makeText(mView.getContext(), R.string.success_message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError() {
        Toast.makeText(mView.getContext(), R.string.error_message,
                       Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showDownloading() {
        Toast.makeText(mView.getContext(), R.string.downloading, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showOfflineModeSuggestion() {
        mSnackbar.show();
    }

    @Override
    public void dismissOfflineModeSuggestion() {
        mSnackbar.dismiss();
    }
}
