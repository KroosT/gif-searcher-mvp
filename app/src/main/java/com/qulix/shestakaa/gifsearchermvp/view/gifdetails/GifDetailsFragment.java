package com.qulix.shestakaa.gifsearchermvp.view.gifdetails;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.qulix.shestakaa.gifsearchermvp.GSApplication;
import com.qulix.shestakaa.gifsearchermvp.R;
import com.qulix.shestakaa.gifsearchermvp.model.LoaderFactory;
import com.qulix.shestakaa.gifsearchermvp.model.NetworkStateManager;
import com.qulix.shestakaa.gifsearchermvp.model.gifdetails.DetailsModelImpl;
import com.qulix.shestakaa.gifsearchermvp.presenter.gifdetails.DetailsPresenter;
import com.qulix.shestakaa.gifsearchermvp.presenter.gifdetails.DetailsRouter;
import com.qulix.shestakaa.gifsearchermvp.utils.Validator;

public class GifDetailsFragment extends Fragment {

    public static final String GIF_URL = "gifUrl";
    private DetailsPresenter mPresenter;
    private DetailsViewImpl mView;

    public GifDetailsFragment() {

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_gif_details, container, false);
        final NetworkStateManager manager = GSApplication.getInstance().getNetworkStateManager();
        final LoaderFactory loaderFactory = new LoaderFactory(getContext());

        mPresenter = new DetailsPresenter(new DetailsModelImpl(loaderFactory, manager),
                                          new DetailsRouter(getFragmentManager()));

        mView = new DetailsViewImpl(view.findViewById(R.id.rootDetails),
                                    extractArgument(GIF_URL),
                                    mPresenter);
        if (isSinglePaneMode()) {
            setHasOptionsMenu(true);
        }
        return view;
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
        inflater.inflate(R.menu.menu_details, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.detailsHome:
                mPresenter.switchToMainScreen();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        if (isSinglePaneMode()) {
            mPresenter.addObserver();
        }
        super.onResume();
    }

    @Override
    public void onPause() {
        mPresenter.cancelSaving();
        mPresenter.removeObserver();
        super.onPause();
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

    @Override
    public void onDestroyView() {
        mPresenter.unbindView();
        super.onDestroyView();
    }

    private boolean isSinglePaneMode() {
        return getActivity().findViewById(R.id.fragmentDetail) == null;
    }
}
