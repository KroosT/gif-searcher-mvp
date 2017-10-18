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

        mPresenter = new Presenter(
                            new ModelImpl(),
                            new ViewImpl(findViewById(R.id.root), mPresenter));
    }

}
