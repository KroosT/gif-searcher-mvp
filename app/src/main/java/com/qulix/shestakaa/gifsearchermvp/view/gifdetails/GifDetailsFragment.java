package com.qulix.shestakaa.gifsearchermvp.view.gifdetails;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.qulix.shestakaa.gifsearchermvp.R;
import com.qulix.shestakaa.gifsearchermvp.model.gifdetails.DetailsModelImpl;
import com.qulix.shestakaa.gifsearchermvp.presenter.gifdetails.DetailsPresenter;
import com.qulix.shestakaa.gifsearchermvp.presenter.gifdetails.DetailsRouter;
import com.qulix.shestakaa.gifsearchermvp.utils.ConnectionStatus;
import com.qulix.shestakaa.gifsearchermvp.utils.NetworkObservable;
import com.qulix.shestakaa.gifsearchermvp.utils.NetworkStateReceiver;
import com.qulix.shestakaa.gifsearchermvp.utils.Validator;

import java.util.Observable;
import java.util.Observer;

public class GifDetailsFragment extends Fragment implements Observer {

    public static final String GIF_URL = "gifUrl";
    private DetailsPresenter mPresenter;
    private DetailsViewImpl mView;

    public GifDetailsFragment() {

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_gif_details, container, false);
        mPresenter = new DetailsPresenter(new DetailsModelImpl(getContext()),
                                          new DetailsRouter(getFragmentManager()));

        mView = new DetailsViewImpl(view.findViewById(R.id.rootDetails),
                                                             extractArgument(GIF_URL),
                                                             mPresenter);
        setHasOptionsMenu(true);
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
                mPresenter.onSwitchToMainScreen();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        NetworkStateReceiver.getObservable().addObserver(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.onCancelSaving();
        NetworkStateReceiver.getObservable().deleteObserver(this);
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
    public void onDestroy() {
        mPresenter.onViewUnbind();
        super.onDestroy();
    }

    @Override
    public void update(final Observable o, final Object arg) {
        if (((NetworkObservable) o).getConnectionStatus() == ConnectionStatus.CONNECTED) {
            mView.dismissOfflineModeSuggestion();
        } else {
            mView.showOfflineModeSuggestion();
        }
    }
}
