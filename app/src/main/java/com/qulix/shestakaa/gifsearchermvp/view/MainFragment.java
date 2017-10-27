package com.qulix.shestakaa.gifsearchermvp.view;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.qulix.shestakaa.gifsearchermvp.R;
import com.qulix.shestakaa.gifsearchermvp.model.ModelImpl;
import com.qulix.shestakaa.gifsearchermvp.presenter.Presenter;
import com.qulix.shestakaa.gifsearchermvp.presenter.Router;
import com.qulix.shestakaa.gifsearchermvp.utils.ConnectionStatus;
import com.qulix.shestakaa.gifsearchermvp.utils.NetworkObservable;
import com.qulix.shestakaa.gifsearchermvp.utils.NetworkStateReceiver;
import com.qulix.shestakaa.gifsearchermvp.view.preferences.PrefActivity;

import java.util.Observable;
import java.util.Observer;

public class MainFragment extends Fragment implements Observer {

    private Presenter mPresenter;
    private ViewImpl mView;

    public MainFragment() {

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_main, container, false);

        mPresenter = new Presenter(new ModelImpl(), new Router(getFragmentManager()));
        mView = new ViewImpl(view.findViewById(R.id.root), mPresenter);

        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                final Intent i = new Intent(getContext(), PrefActivity.class);
                startActivity(i);
                return true;
            case R.id.offlineModeButton:
                mPresenter.onSwitchToOffline();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        NetworkStateReceiver.getObservable().addObserver(this);
        super.onResume();
    }

    @Override
    public void onPause() {
        mPresenter.onStopRequest();
        mView.onStopWatcher();
        NetworkStateReceiver.getObservable().deleteObserver(this);
        super.onPause();
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

    public static MainFragment newInstance() {
        return new MainFragment();
    }
}
