package com.qulix.shestakaa.gifsearchermvp.view.gifdetails;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qulix.shestakaa.gifsearchermvp.R;
import com.qulix.shestakaa.gifsearchermvp.model.gifdetails.DetailsModelImpl;
import com.qulix.shestakaa.gifsearchermvp.presenter.gifdetails.DetailsPresenter;
import com.qulix.shestakaa.gifsearchermvp.utils.Validator;

public class GifDetailsFragment extends Fragment {

    public static final String GIF_URL = "gifUrl";
    private DetailsPresenter mPresenter;

    public GifDetailsFragment() {

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_gif_details, container, false);
        mPresenter = new DetailsPresenter(new DetailsModelImpl(getContext()));
        final DetailsViewImpl viewImpl = new DetailsViewImpl(view.findViewById(R.id.rootDetails),
                                                             extractArgument(GIF_URL),
                                                             mPresenter);

        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        mPresenter.onCancelSaving();
    }

    public static GifDetailsFragment newInstance(final String url) {
        Validator.isArgNotNull(url, "url");

        final GifDetailsFragment gifDetailsFragment = new GifDetailsFragment();
        final Bundle bundle = new Bundle();
        bundle.putString(GIF_URL, url);
        gifDetailsFragment.setArguments(bundle);

        return gifDetailsFragment;
    }

    private String extractArgument(final String key) {
        Validator.isArgNotNull(key, "key");

        final String arg = getArguments().getString(key);
        Validator.isArgNotNull(arg, "arg");

        return arg;
    }
}
