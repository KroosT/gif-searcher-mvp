package com.qulix.shestakaa.gifsearchermvp.view;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.qulix.shestakaa.gifsearchermvp.R;
import com.qulix.shestakaa.gifsearchermvp.model.API.Data;
import com.qulix.shestakaa.gifsearchermvp.model.Model;
import com.qulix.shestakaa.gifsearchermvp.presenter.Presenter;
import com.qulix.shestakaa.gifsearchermvp.utils.Validator;
import com.qulix.shestakaa.gifsearchermvp.utils.ViewUtils;
import com.yalantis.jellytoolbar.listener.JellyListener;
import com.yalantis.jellytoolbar.widget.JellyToolbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainView {

    Presenter mPresenter;
    RecyclerAdapter mAdapter;
    RecyclerView mRecyclerView;
    JellyToolbar mJellyToolbar;
    TextView mTitleTextView;
    TextView mToolbarTextView;
    AppCompatEditText mEditText;
    String mRequest;
    List<Data> mDataList;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mPresenter = new Presenter(new Model(), this);

        mTitleTextView = findViewById(R.id.title);
        mTitleTextView.setText(R.string.trending);
        mToolbarTextView = findViewById(R.id.toolbar_title);

        mToolbarTextView.setOnClickListener(onToolbarTitleClick);

        mJellyToolbar = findViewById(R.id.toolbar);
        mJellyToolbar.setJellyListener(jellyListener);

        mEditText = (AppCompatEditText) LayoutInflater.from(this).inflate(R.layout.edit_text, null);
        mEditText.setBackgroundResource(R.color.colorTransparent);
        mEditText.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
        mJellyToolbar.setContentView(mEditText);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mDataList = new ArrayList<>();
        mAdapter = new RecyclerAdapter(this, mDataList);
        mRecyclerView.setAdapter(mAdapter);
    }

    private final JellyListener jellyListener = new JellyListener() {
        @Override
        public void onCancelIconClicked() {
            ViewUtils.hideSoftKeyboard(mEditText);
            final Editable request = mEditText.getText();
            if (!TextUtils.isEmpty(request)) {
                mRequest = request.toString();
                request.clear();
            }
            mJellyToolbar.collapse();
            mTitleTextView.setText(getString(R.string.gifs_for, mRequest));
            mPresenter.getByRequest(mRequest);
        }

        @Override
        public void onToolbarExpandingStarted() {
            super.onToolbarExpandingStarted();
            ViewUtils.showSoftKeyboard(mEditText);
            mToolbarTextView.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onToolbarCollapsingStarted() {
            super.onToolbarCollapsingStarted();
            mToolbarTextView.setVisibility(View.VISIBLE);
        }
    };

    View.OnClickListener onToolbarTitleClick = new View.OnClickListener() {
        @Override
        public void onClick(final View view) {
            mPresenter.getTrending();
            mTitleTextView.setText(R.string.trending);
        }
    };

    @Override
    public void updateData(@NonNull final List<Data> data) {
        Validator.isArgNotNull(data, "data");
        mAdapter.updateData(data);
    }

    @Override
    public void showError() {
        Toast.makeText(this,
                       "Something went wrong. Check your connection to Internet",
                       Toast.LENGTH_SHORT)
             .show();
    }

    @Override
    public void showNoGifsError() {
        Toast.makeText(this,
                "No gifs for such request found."
                , Toast.LENGTH_SHORT)
                .show();
    }
}
