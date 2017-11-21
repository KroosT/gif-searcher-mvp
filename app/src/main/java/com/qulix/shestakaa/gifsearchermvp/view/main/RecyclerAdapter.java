package com.qulix.shestakaa.gifsearchermvp.view.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.qulix.shestakaa.gifsearchermvp.R;
import com.qulix.shestakaa.gifsearchermvp.utils.AdapterData;
import com.qulix.shestakaa.gifsearchermvp.utils.MainScreenListener;
import com.qulix.shestakaa.gifsearchermvp.utils.Validator;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int CONTENT = 0;
    private static final int BUTTON = 1;
    private static final int PROGRESS_BAR = 2;

    private AdapterData mAdapterData;
    private final Context mContext;
    private final MainScreenListener mMainScreenListener;

    RecyclerAdapter(final Context context,
                    final AdapterData data,
                    final MainScreenListener mainScreenListener) {
        Validator.isArgNotNull(context, "context");
        Validator.isArgNotNull(data, "data");
        Validator.isArgNotNull(mainScreenListener, "mainScreenListener");

        mAdapterData = data;
        mContext = context;
        mMainScreenListener = mainScreenListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        switch (viewType) {
            case BUTTON:
                return new ButtonHolder(inflateFromLayout(parent, R.layout.load_more));
            case PROGRESS_BAR:
                return new ProgressBarHolder(inflateFromLayout(parent, R.layout.progress_bar));
            case CONTENT:
            default:
                return new ImageHolder(inflateFromLayout(parent, R.layout.list_item));
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final int type = getItemViewType(position);
        if (type == CONTENT) {
            final String gifUrl = mAdapterData.getUrls().get(position);
            final ImageHolder imageHolder = (ImageHolder) holder;
            Glide.with(mContext)
                 .load(gifUrl)
                 .apply(RequestOptions.placeholderOf(R.mipmap.ic_downloading))
                 .into(imageHolder.mImageView);

            imageHolder.mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    mMainScreenListener.onGifClicked(gifUrl);
                }
            });
        } else if (type == BUTTON) {
            ((ButtonHolder) holder).mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    mMainScreenListener.onLoadMoreButtonClicked();
                }
            });
        }
    }

    @Override
    public int getItemViewType(final int position) {
        if (position >= mAdapterData.getUrls().size()) {
            if (mAdapterData.isButtonPresents()) {
                return BUTTON;
            }
            if (mAdapterData.isProgressBarPresents()) {
                return PROGRESS_BAR;
            }
        }
        return CONTENT;
    }

    @Override
    public int getItemCount() {
        return mAdapterData.size();
    }

    public void updateData(final AdapterData data) {
        Validator.isArgNotNull(data, "data");
        mAdapterData = new AdapterData(data);
    }

    private View inflateFromLayout(final ViewGroup parent, final int layout) {
        return LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
    }

    private class ImageHolder extends RecyclerView.ViewHolder {

        private final ImageView mImageView;

        ImageHolder(@Nonnull final View itemView) {
            super(itemView);
            Validator.isArgNotNull(itemView, "itemView");
            mImageView = itemView.findViewById(R.id.imageView);
        }
    }

    private class ButtonHolder extends RecyclerView.ViewHolder {

        private final Button mButton;

        ButtonHolder(@Nonnull final View itemView) {
            super(itemView);
            Validator.isArgNotNull(itemView, "itemView");
            mButton = itemView.findViewById(R.id.loadMoreButton);
        }
    }

    private class ProgressBarHolder extends RecyclerView.ViewHolder {

        private final ProgressBar mProgressBar;

        ProgressBarHolder(@Nonnull final View itemView) {
            super(itemView);
            Validator.isArgNotNull(itemView, "itemView");
            mProgressBar = itemView.findViewById(R.id.progressBar);
        }
    }
}