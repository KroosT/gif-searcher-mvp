package com.qulix.shestakaa.gifsearchermvp.view;

import android.graphics.Color;
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
import com.qulix.shestakaa.gifsearchermvp.presenter.Presenter;
import com.qulix.shestakaa.gifsearchermvp.utils.Validator;
import com.qulix.shestakaa.gifsearchermvp.utils.ViewUtils;
import com.yalantis.jellytoolbar.listener.JellyListener;
import com.yalantis.jellytoolbar.widget.JellyToolbar;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

public class ViewImpl implements ViewInterface {

    private final View mView;
    private final TextView mToolbarTextView;
    private final JellyToolbar mJellyToolbar;
    private final TextView mTitleTextView;
    private final AppCompatEditText mEditText;
    private Presenter mPresenter;
    private String mRequest = "";
    private final RecyclerAdapter mAdapter;

    public ViewImpl(final View view) {

        mView = view;

        mTitleTextView = view.findViewById(R.id.title);
        mTitleTextView.setText(R.string.trending);

        mToolbarTextView = view.findViewById(R.id.toolbar_title);
        mToolbarTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                mPresenter.onTitleClicked();
                mTitleTextView.setText(R.string.trending);
            }
        });

        mJellyToolbar = view.findViewById(R.id.toolbar);
        mJellyToolbar.setJellyListener(createJellyListener());

        mEditText = (AppCompatEditText) LayoutInflater.from(view.getContext())
                                                      .inflate(R.layout.edit_text, null);
        mEditText.setBackgroundResource(R.color.colorTransparent);
        mEditText.setTextColor(Color.WHITE);
        mJellyToolbar.setContentView(mEditText);

        final RecyclerView recyclerView = mView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(mView.getContext()));

        final List<Data> dataList = new ArrayList<>();
        mAdapter = new RecyclerAdapter(mView.getContext(), dataList);
        recyclerView.setAdapter(mAdapter);

    }

    public void registerPresenter(final Presenter presenter) {
        mPresenter = presenter;
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
                    mTitleTextView.setText(mView.getContext().getString(R.string.gifs_for, mRequest));
                    mPresenter.onCloseIconClicked(mRequest);
                }
                mJellyToolbar.collapse();
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
    public void updateData(@Nonnull final List<Data> data) {
        Validator.isArgNotNull(data, "data");
        mAdapter.updateData(data);
    }

    @Override
    public void showError() {
        Toast.makeText(mView.getContext(),
                "Something went wrong. Check your connection to Internet",
                Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showNoGifsError() {
        Toast.makeText(mView.getContext(),
                "No gifs for such request found.",
                Toast.LENGTH_SHORT).show();
    }
}
