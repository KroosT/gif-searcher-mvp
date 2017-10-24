package com.qulix.shestakaa.gifsearchermvp.view;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.LayoutInflater;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.qulix.shestakaa.gifsearchermvp.R;
import com.qulix.shestakaa.gifsearchermvp.model.API.Data;
import com.qulix.shestakaa.gifsearchermvp.presenter.Presenter;
import com.qulix.shestakaa.gifsearchermvp.utils.AnswerProvider;
import com.qulix.shestakaa.gifsearchermvp.utils.CancelableTextWatcher;
import com.qulix.shestakaa.gifsearchermvp.utils.Validator;
import com.qulix.shestakaa.gifsearchermvp.utils.ViewUtils;
import com.yalantis.jellytoolbar.listener.JellyListener;
import com.yalantis.jellytoolbar.widget.JellyToolbar;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class ViewImpl implements View {

    private static final String CONNECTION_ERROR = "Something went wrong. Check your Internet connection";
    private static final String NO_GIFS_ERROR = "No gifs for such request found.";

    private final android.view.View mView;
    private final TextView mToolbarTextView;
    private final JellyToolbar mJellyToolbar;
    private final TextView mTitleTextView;
    private final ImageButton mOffline;
    private final AppCompatEditText mEditText;
    private final RecyclerAdapter mAdapter;
    private final CancelableTextWatcher mWatcher;
    private final Presenter mPresenter;


    public ViewImpl(final android.view.View view, final Presenter presenter) {
        Validator.isArgNotNull(view, "view");
        Validator.isArgNotNull(presenter, "presenter");

        mView = view;
        mPresenter = presenter;
        mPresenter.onViewBind(this);

        mTitleTextView = view.findViewById(R.id.title);
        mTitleTextView.setText(R.string.trending);

        mToolbarTextView = view.findViewById(R.id.toolbar_title);
        mToolbarTextView.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(final android.view.View v) {
                mPresenter.onMainScreenSet();
                mTitleTextView.setText(R.string.trending);
            }
        });

        mOffline = view.findViewById(R.id.offlineModeButton);
        mOffline.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(final android.view.View v) {
                mPresenter.onSwitchToOffline();
            }
        });

        mJellyToolbar = view.findViewById(R.id.toolbar);
        mJellyToolbar.setJellyListener(createJellyListener());

        mEditText = (AppCompatEditText) LayoutInflater.from(view.getContext())
                                                      .inflate(R.layout.edit_text, null);
        mEditText.setBackgroundResource(R.color.colorTransparent);
        mEditText.setTextColor(Color.WHITE);
        mWatcher = initTextWatcher();
        mEditText.addTextChangedListener(mWatcher);

        mJellyToolbar.setContentView(mEditText);

        final RecyclerView recyclerView = mView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(mView.getContext()));

        final List<Data> dataList = new ArrayList<>();
        final AnswerProvider answerProvider = new AnswerProvider() {
            @Override
            public void onStringProvided(final String arg) {
                Validator.isArgNotNull(arg, "arg");
                Validator.isArgNotNull(mPresenter, "mPresenter");

                mPresenter.onGifClicked(arg);
            }
        };

        mAdapter = new RecyclerAdapter(mView.getContext(), dataList, answerProvider);
        recyclerView.setAdapter(mAdapter);

    }

    @Override
    public void updateData(@Nonnull final List<Data> data) {
        Validator.isArgNotNull(data, "data");
        mAdapter.updateData(data);
    }

    @Override
    public void showError() {
        Toast.makeText(mView.getContext(), CONNECTION_ERROR, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showNoGifsError() {
        Toast.makeText(mView.getContext(), NO_GIFS_ERROR, Toast.LENGTH_SHORT).show();
    }

    private JellyListener createJellyListener() {
        return new JellyListener() {
            @Override
            public void onCancelIconClicked() {
                ViewUtils.hideSoftKeyboard(mEditText);
                mJellyToolbar.collapse();
            }

            @Override
            public void onToolbarExpandingStarted() {
                super.onToolbarExpandingStarted();
                ViewUtils.showSoftKeyboard(mEditText);
                mToolbarTextView.setVisibility(android.view.View.INVISIBLE);
                mOffline.setVisibility(android.view.View.INVISIBLE);
            }

            @Override
            public void onToolbarCollapsingStarted() {
                super.onToolbarCollapsingStarted();
                mToolbarTextView.setVisibility(android.view.View.VISIBLE);
                mOffline.setVisibility(android.view.View.VISIBLE);
            }
        };
    }

    private CancelableTextWatcher initTextWatcher() {
        return new CancelableTextWatcher() {

            private final static int DELAY = 300;
            private final Handler mHandler = new Handler();

            @Override
            public void afterTextChanged(final Editable s) {
                final String request = s.toString();
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        execute(request);
                    }
                }, DELAY);

                updateUI(request);
            }

            @Override
            public void onCancelCallbacks() {
                mHandler.removeCallbacksAndMessages(null);
            }

            @Override public void beforeTextChanged(final CharSequence s, final int start, final int count, final int after) { /*ignored*/ }
            @Override public void onTextChanged(final CharSequence s, final int start, final int before, final int count) { /*ignored*/ }
        };
    }

    private void execute(final String request) {
        if (request.length() != 0) {
            mPresenter.onTextInputChanged(request);
        } else {
            mPresenter.onMainScreenSet();
        }
    }

    private void updateUI(final String request) {

        final Context context = mView.getContext();

        final String searchText = context.getString(R.string.gifs_for, request);
        final String trendingText = context.getString(R.string.trending);

        final String title = request.length() != 0 ? searchText
                                                   : trendingText;

        mTitleTextView.setText(title);
    }

    public void onStopWatcher() {
        mWatcher.onCancelCallbacks();
    }
}
