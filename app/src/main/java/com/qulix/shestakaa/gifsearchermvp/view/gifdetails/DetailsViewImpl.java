package com.qulix.shestakaa.gifsearchermvp.view.gifdetails;

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
public class DetailsViewImpl implements DetailsViewInterface {

    private static final String SUCCESS_MESSAGE = "Success";
    private static final String ERROR_MESSAGE = "Something went wrong. Cannot save gif.";
    private final View mView;
    private final String mUrl;
    private DetailsPresenter mDetailsPresenter;

    public DetailsViewImpl(final View view, final String url) {
        Validator.isArgNotNull(view, "view");
        Validator.isArgNotNull(url, "url");
        mView = view;
        mUrl = url;
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

    public void registerPresenter(final DetailsPresenter detailsPresenter) {
        Validator.isArgNotNull(detailsPresenter, "detailsPresenter");
        mDetailsPresenter = detailsPresenter;
        afterPresenterRegistered();
    }

    private void afterPresenterRegistered() {
        mDetailsPresenter.onShowGif(mUrl);
        final Button buttonSave = mView.findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                mDetailsPresenter.onSaveGif(mView.getContext(), mUrl);
            }
        });
    }
}
