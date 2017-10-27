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
import android.view.LayoutInflater;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qulix.shestakaa.gifsearchermvp.R;
import com.qulix.shestakaa.gifsearchermvp.model.API.Data;
import com.qulix.shestakaa.gifsearchermvp.presenter.Presenter;
import com.qulix.shestakaa.gifsearchermvp.utils.CancelableTextWatcher;
import com.qulix.shestakaa.gifsearchermvp.utils.ConnectionStatus;
import com.qulix.shestakaa.gifsearchermvp.utils.MainScreenListener;
import com.qulix.shestakaa.gifsearchermvp.utils.NetworkStateReceiver;
import com.qulix.shestakaa.gifsearchermvp.utils.StringUtils;
import com.qulix.shestakaa.gifsearchermvp.utils.Validator;
import com.qulix.shestakaa.gifsearchermvp.utils.ViewUtils;
import com.yalantis.jellytoolbar.listener.JellyListener;
import com.yalantis.jellytoolbar.widget.JellyToolbar;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.ParametersAreNonnullByDefault;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static com.qulix.shestakaa.gifsearchermvp.utils.StringConstants.CONNECTION_ERROR;
import static com.qulix.shestakaa.gifsearchermvp.utils.StringConstants.DOWNLOADING_STATUS;
import static com.qulix.shestakaa.gifsearchermvp.utils.StringConstants.GIFS_ENDED;
import static com.qulix.shestakaa.gifsearchermvp.utils.StringConstants.GO_OFFLINE;
import static com.qulix.shestakaa.gifsearchermvp.utils.StringConstants.NO_GIFS_ERROR;

@ParametersAreNonnullByDefault
public class ViewImpl implements View {

    private final android.view.View mView;
    private final JellyToolbar mJellyToolbar;
    private final TextView mTitleTextView;
    private final AppCompatEditText mEditText;
    private final RecyclerAdapter mAdapter;
    private final CancelableTextWatcher mWatcher;
    private final Presenter mPresenter;
    private final Snackbar mSnackbar;


    public ViewImpl(final android.view.View view, final Presenter presenter) {
        Validator.isArgNotNull(view, "view");
        Validator.isArgNotNull(presenter, "presenter");

        mView = view;
        mPresenter = presenter;
        mPresenter.onViewBind(this);

        mTitleTextView = view.findViewById(R.id.title);
        mTitleTextView.setText(R.string.trending);

        mJellyToolbar = view.findViewById(R.id.toolbar);
        mJellyToolbar.setJellyListener(createJellyListener());

        mEditText = (AppCompatEditText) LayoutInflater.from(view.getContext())
                                                      .inflate(R.layout.edit_text,
                                                               new LinearLayout(mView.getContext()),
                                                               false);

        mEditText.setBackgroundResource(R.color.colorTransparent);
        mEditText.setTextColor(Color.WHITE);
        mWatcher = initTextWatcher();
        mEditText.addTextChangedListener(mWatcher);

        mJellyToolbar.setContentView(mEditText);

        final RecyclerView recyclerView = mView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(mView.getContext()));

        final List<Data> dataList = new ArrayList<>();
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

        mAdapter = new RecyclerAdapter(mView.getContext(), dataList, mainScreenListener);
        recyclerView.setAdapter(mAdapter);

        final OnClickListener onSnackBarClick = new OnClickListener() {
            @Override
            public void onClick(final android.view.View v) {
                mPresenter.onSwitchToOffline();
            }
        };

        final Context context = mView.getContext();
        mSnackbar = Snackbar.make(mView, CONNECTION_ERROR, Snackbar.LENGTH_INDEFINITE)
                            .setAction(GO_OFFLINE, onSnackBarClick)
                            .setActionTextColor(ContextCompat.getColor(context,
                                                                       R.color.colorPrimary));
        final ConnectionStatus connectionStatus = NetworkStateReceiver.getObservable()
                                                                      .getConnectionStatus();
        if (connectionStatus == ConnectionStatus.NO_CONNECTION) {
            mSnackbar.show();
        }

    }

    @Override
    public void updateData(final List<Data> data, final int totalCount) {
        Validator.isArgNotNull(data, "data");
        mAdapter.updateData(data, totalCount);
    }

    @Override
    public void showError() {
        if (!mSnackbar.isShown()) {
            Toast.makeText(mView.getContext(), CONNECTION_ERROR, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void showNoGifsError() {
        Toast.makeText(mView.getContext(), NO_GIFS_ERROR, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showGifsEndedInfo() {
        Toast.makeText(mView.getContext(), GIFS_ENDED, Toast.LENGTH_SHORT).show();
    }

    public void showOfflineModeSuggestion() {
        mSnackbar.show();
    }

    public void dismissOfflineModeSuggestion() {
        mSnackbar.dismiss();
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
                ViewUtils.showSoftKeyboard(mEditText);
            }

            @Override
            public void onToolbarCollapsingStarted() {
                super.onToolbarCollapsingStarted();
                mTitleTextView.setVisibility(VISIBLE);
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

            @Override
            public void beforeTextChanged(final CharSequence s, final int start, final int count, final int after) { /*ignored*/ }
            @Override
            public void onTextChanged(final CharSequence s, final int start, final int before, final int count) { /*ignored*/ }
        };
    }

    private void execute(final String request) {
        if (StringUtils.isNotNullOrBlank(request)) {
            mPresenter.onTextInputChanged(request);
        } else {
            mPresenter.onMainScreenSet();
        }
    }

    private void updateUI(final String request) {

        final Context context = mView.getContext();
        final String searchText = context.getString(R.string.gifs_for, request);
        final String trendingText = context.getString(R.string.trending);

        final String title = StringUtils.isNotNullOrBlank(request) ? searchText.trim()
                                                                   : trendingText;
        final int iconResId = StringUtils.isNotNullOrBlank(request) ? R.mipmap.ic_done
                                                                    : R.mipmap.ic_close;
        mTitleTextView.setText(title);
        mJellyToolbar.setCancelIconRes(iconResId);
    }

    public void onStopWatcher() {
        mWatcher.onCancelCallbacks();
    }
}
