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

    private DetailsPresenter mPresenter;

    public GifDetailsFragment() {

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {

        final String arg = getArguments().getString("gifUrl");
        Validator.isArgNotNull(arg, "arg");
        final View view = inflater.inflate(R.layout.fragment_gif_details, container, false);
        final DetailsViewImpl viewImpl = new DetailsViewImpl(view.findViewById(
                                                                            R.id.rootDetails),
                                                                            arg);
        mPresenter = new DetailsPresenter(new DetailsModelImpl(),
                                                                       viewImpl);
        viewImpl.registerPresenter(mPresenter);

        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        mPresenter.onCancelSaving();
    }
}
