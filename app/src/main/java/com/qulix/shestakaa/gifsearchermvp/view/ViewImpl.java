package com.qulix.shestakaa.gifsearchermvp.view;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.qulix.shestakaa.gifsearchermvp.R;
import com.qulix.shestakaa.gifsearchermvp.presenter.Presenter;
import com.qulix.shestakaa.gifsearchermvp.utils.AdapterData;
import com.qulix.shestakaa.gifsearchermvp.utils.MainScreenListener;
import com.qulix.shestakaa.gifsearchermvp.utils.StringUtils;
import com.qulix.shestakaa.gifsearchermvp.utils.Validator;
import com.qulix.shestakaa.gifsearchermvp.utils.ViewUtils;
import com.yalantis.jellytoolbar.listener.JellyListener;
import com.yalantis.jellytoolbar.widget.JellyToolbar;

import javax.annotation.ParametersAreNonnullByDefault;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

@ParametersAreNonnullByDefault
public class ViewImpl implements View {

    private final android.view.View mView;
    private final JellyToolbar mJellyToolbar;
    private final TextView mTitleTextView;
    private final AppCompatEditText mEditText;
    private final RecyclerAdapter mAdapter;
    private final RecyclerView mRecyclerView;
    private final TextWatcher mWatcher;
    private final Presenter mPresenter;
    private final Snackbar mSnackbar;
    private final TextView mEmptyTextView;
    private final ProgressBar mMainProgressBar;
    private final LinearLayout mErrorView;
    private AdapterData mAdapterData;
    private final Handler mHandler;
    private String mRequest;


    public ViewImpl(final android.view.View view, final Presenter presenter) {
        Validator.isArgNotNull(view, "view");
        Validator.isArgNotNull(presenter, "presenter");

        mView = view;
        mPresenter = presenter;
        mRequest = "";

        final Context context = view.getContext();
        mTitleTextView = view.findViewById(R.id.title);
        mTitleTextView.setText(R.string.trending);

        mEmptyTextView = view.findViewById(R.id.emptyTextView);
        mMainProgressBar = view.findViewById(R.id.mainProgressBar);
        mErrorView = view.findViewById(R.id.errorView);

        final Button tryAgainButton = view.findViewById(R.id.repeat);
        tryAgainButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final android.view.View v) {
                mPresenter.repeatPreviousRequest();
            }
        });

        mJellyToolbar = view.findViewById(R.id.toolbar);
        mJellyToolbar.setJellyListener(createJellyListener());

        mEditText = (AppCompatEditText) LayoutInflater.from(context)
                                                      .inflate(R.layout.edit_text,
                                                               new LinearLayout(context),
                                                               false);

        mEditText.setBackgroundResource(R.color.colorTransparent);
        mEditText.setTextColor(Color.WHITE);

        mHandler = new Handler();
        mWatcher = initTextWatcher(mHandler);

        mJellyToolbar.setContentView(mEditText);

        mRecyclerView = mView.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.addOnScrollListener(mPresenter.createOnScrollListener());

        mAdapterData = new AdapterData();
        final MainScreenListener mainScreenListener = new MainScreenListener() {

            @Override
            public void onLoadMoreButtonClicked() {
                mPresenter.onLoadMoreClicked();
            }

            @Override
            public void onGifClicked(final String arg) {
                Validator.isArgNotNull(arg, "arg");
                Validator.isArgNotNull(mPresenter, "mPresenter");

                mPresenter.onGifClicked(arg);
            }
        };

        mAdapter = new RecyclerAdapter(context, mAdapterData, mainScreenListener);
        mRecyclerView.setAdapter(mAdapter);

        final OnClickListener onSnackBarClick = new OnClickListener() {
            @Override
            public void onClick(final android.view.View v) {
                mPresenter.switchToOffline();
            }
        };

        mSnackbar = Snackbar.make(mView, R.string.connection_error, Snackbar.LENGTH_INDEFINITE)
                            .setAction(R.string.go_offline, onSnackBarClick)
                            .setActionTextColor(ContextCompat.getColor(context,
                                                                       R.color.colorPrimary));

        mPresenter.bindView(this);
    }

    @Override
    public void updateData(final AdapterData data) {
        Validator.isArgNotNull(data, "data");
        if (!data.getUrls().isEmpty()) {
            mRecyclerView.setVisibility(VISIBLE);
            mEmptyTextView.setVisibility(GONE);
            mErrorView.setVisibility(GONE);
            mMainProgressBar.setVisibility(GONE);
        }
        data.setButtonPresents(mAdapterData.isButtonPresents());
        mAdapter.updateData(data);
        mAdapter.notifyDataSetChanged();
        mAdapterData = new AdapterData(data);
        replaceTitle();
    }

    @Override
    public void showError() {
        if (mAdapterData.getUrls().isEmpty()) {
            mRecyclerView.setVisibility(GONE);
            mMainProgressBar.setVisibility(GONE);
            mErrorView.setVisibility(VISIBLE);
        }
    }

    @Override
    public void showNoGifsError() {
        Toast.makeText(mView.getContext(), R.string.no_gifs_error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showOfflineModeSuggestion() {
        mSnackbar.show();
    }

    @Override
    public void dismissOfflineModeSuggestion() {
        mSnackbar.dismiss();
    }

    @Override
    public void showButton() {
        mAdapterData.setProgressBarPresents(false);
        mAdapterData.setButtonPresents(true);
        mAdapter.updateData(mAdapterData);
        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                mAdapter.notifyItemChanged(mAdapterData.size());
            }
        });
    }

    @Override
    public void showProgressBar() {
        mAdapterData.setButtonPresents(false);
        mAdapterData.setProgressBarPresents(true);
        mAdapter.updateData(mAdapterData);
        mAdapter.notifyItemChanged(mAdapterData.size());
    }

    @Override
    public void showMainProgressBar() {
        mRecyclerView.setVisibility(GONE);
        mErrorView.setVisibility(GONE);
        mMainProgressBar.setVisibility(VISIBLE);
    }

    @Override
    public void showDataEnded() {
        mAdapterData.setButtonPresents(false);
        mAdapter.updateData(mAdapterData);
        mAdapter.notifyItemChanged(mAdapterData.size());
        Toast.makeText(mView.getContext(), R.string.data_ended, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showBlankScreen() {
        mRecyclerView.setVisibility(GONE);
        mMainProgressBar.setVisibility(GONE);
        mEmptyTextView.setVisibility(VISIBLE);
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
                mTitleTextView.setVisibility(INVISIBLE);
                mEditText.addTextChangedListener(mWatcher);
                ViewUtils.showSoftKeyboard(mEditText);
            }

            @Override
            public void onToolbarCollapsingStarted() {
                super.onToolbarCollapsingStarted();
                mTitleTextView.setVisibility(VISIBLE);
                mRecyclerView.scrollToPosition(0);
                mEditText.removeTextChangedListener(mWatcher);
                mEditText.getText().clear();
            }
        };
    }

    private void replaceTitle() {
        final Context context = mView.getContext();
        final String searchText = context.getString(R.string.gifs_for, mRequest);
        final String trendingText = context.getString(R.string.trending);

        final String title = StringUtils.isNotNullOrBlank(mRequest) ? searchText.trim()
                                                                    : trendingText;
        mTitleTextView.setText(title);
    }

    private TextWatcher initTextWatcher(final Handler handler) {
        return new TextWatcher() {

            private final static int DELAY = 300;

            public void afterTextChanged(final Editable s) {
                handler.removeCallbacksAndMessages(null);
                final String request = s.toString();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        execute(request);
                    }
                }, DELAY);

                updateIcon(request);
            }

            @Override
            public void beforeTextChanged(final CharSequence s, final int start, final int count, final int after) { /*ignored*/ }
            @Override
            public void onTextChanged(final CharSequence s, final int start, final int before, final int count) { /*ignored*/ }
        };
    }

    private void execute(final String request) {
        mPresenter.onTextInputChanged(request);
        mRequest = request;
    }

    private void updateIcon(final String request) {

        final int iconResId = StringUtils.isNotNullOrBlank(request) ? R.mipmap.ic_done
                                                                    : R.mipmap.ic_close;
        mJellyToolbar.setCancelIconRes(iconResId);

    }

    public void stopWatcher() {
        mHandler.removeCallbacksAndMessages(null);
    }
}
