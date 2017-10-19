package com.qulix.shestakaa.gifsearchermvp.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.qulix.shestakaa.gifsearchermvp.R;
import com.qulix.shestakaa.gifsearchermvp.model.ModelImpl;
import com.qulix.shestakaa.gifsearchermvp.presenter.Presenter;

public class MainActivity extends AppCompatActivity {

    private Presenter mPresenter;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        final ViewImpl view = new ViewImpl(findViewById(R.id.root));
        mPresenter = new Presenter(new ModelImpl(), view);
        view.registerPresenter(mPresenter);
    }

    @Override
    protected void onStop() {
        mPresenter.onStop();
        super.onStop();
    }
}
