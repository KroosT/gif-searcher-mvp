package com.qulix.shestakaa.gifsearchermvp.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
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
import com.qulix.shestakaa.gifsearchermvp.model.ModelImpl;
import com.qulix.shestakaa.gifsearchermvp.presenter.Presenter;
import com.qulix.shestakaa.gifsearchermvp.utils.Validator;
import com.qulix.shestakaa.gifsearchermvp.utils.ViewUtils;
import com.yalantis.jellytoolbar.listener.JellyListener;
import com.yalantis.jellytoolbar.widget.JellyToolbar;

import java.util.ArrayList;
import java.util.List;

public class ViewImpl implements ViewInterface {

    private final Presenter mPresenter;
    private final RecyclerAdapter mAdapter;
    private final JellyToolbar mJellyToolbar;
    private final TextView mTitleTextView;
    private final TextView mToolbarTextView;
    private final AppCompatEditText mEditText;
    private final Activity mActivity;

    private String mRequest;

    private ViewImpl(final Activity activity) {

        mActivity = activity;
        mPresenter = new Presenter(new ModelImpl(), this);

        mTitleTextView = activity.findViewById(R.id.title);
        mTitleTextView.setText(R.string.trending);
        mToolbarTextView = activity.findViewById(R.id.toolbar_title);

        mToolbarTextView.setOnClickListener(createOnClickListener());

        mJellyToolbar = activity.findViewById(R.id.toolbar);
        mJellyToolbar.setJellyListener(createJellyListener());

        mEditText = (AppCompatEditText) LayoutInflater.from(activity).inflate(R.layout.edit_text,
                null);
        mEditText.setBackgroundResource(R.color.colorTransparent);
        mEditText.setTextColor(Color.WHITE);
        mJellyToolbar.setContentView(mEditText);

        final RecyclerView recyclerView = activity.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));

        final List<Data> dataList = new ArrayList<>();
        mAdapter = new RecyclerAdapter(activity, dataList);
        recyclerView.setAdapter(mAdapter);

    }

    public static void createView(final Activity activity) {
        new ViewImpl(activity);
    }

    private View.OnClickListener createOnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                mPresenter.getTrending();
                mTitleTextView.setText(R.string.trending);
            }
        };
    }

    private JellyListener createJellyListener() {
        return new JellyListener() {
            @Override
            public void onCancelIconClicked() {
                ViewUtils.hideSoftKeyboard(mEditText);
                final Editable request = mEditText.getText();
                if (!TextUtils.isEmpty(request)) {
                    mRequest = request.toString();
                    request.clear();
                }
                mJellyToolbar.collapse();
                mTitleTextView.setText(mActivity.getString(R.string.gifs_for, mRequest));
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
    }

    @Override
    public void updateData(@NonNull final List<Data> data) {
        Validator.isArgNotNull(data, "data");
        mAdapter.updateData(data);
    }

    @Override
    public void showError() {
        Toast.makeText(mActivity,
                "Something went wrong. Check your connection to Internet",
                Toast.LENGTH_SHORT)
             .show();
    }

    @Override
    public void showNoGifsError() {
        Toast.makeText(mActivity,
                "No gifs for such request found."
                , Toast.LENGTH_SHORT)
             .show();
    }
}
