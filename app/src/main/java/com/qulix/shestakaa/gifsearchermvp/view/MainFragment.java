package com.qulix.shestakaa.gifsearchermvp.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qulix.shestakaa.gifsearchermvp.R;
import com.qulix.shestakaa.gifsearchermvp.model.ModelImpl;
import com.qulix.shestakaa.gifsearchermvp.presenter.Presenter;

public class MainFragment extends Fragment {

    private Presenter mPresenter;

    public MainFragment() {

    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_main, container, false);

        final ViewImpl viewImpl = new ViewImpl(view.findViewById(R.id.root),
                                                getActivity().getSupportFragmentManager());
        mPresenter = new Presenter(new ModelImpl(), viewImpl);
        viewImpl.registerPresenter(mPresenter);

        return view;
    }

    @Override
    public void onStop() {
        mPresenter.onStop();
        super.onStop();
    }
}
