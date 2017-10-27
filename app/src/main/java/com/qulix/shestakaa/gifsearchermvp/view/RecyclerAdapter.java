package com.qulix.shestakaa.gifsearchermvp.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.qulix.shestakaa.gifsearchermvp.R;
import com.qulix.shestakaa.gifsearchermvp.model.API.Data;
import com.qulix.shestakaa.gifsearchermvp.utils.MainScreenListener;
import com.qulix.shestakaa.gifsearchermvp.utils.Validator;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static com.qulix.shestakaa.gifsearchermvp.utils.StringConstants.LOAD_MORE_BUTTON_TYPE;
import static com.qulix.shestakaa.gifsearchermvp.utils.StringConstants.LOAD_MORE_SCROLL_TYPE;

@ParametersAreNonnullByDefault
class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private static final int CONTENT_TYPE = 0;
    private static final int BUTTON_TYPE = 1;
    private static final int DEFAULT_GIF_COUNT_LIMIT = 25;

    private final List<Data> mData;
    private final Context mContext;
    private final MainScreenListener mMainScreenListener;
    private int mTotalCount = 0;
    private boolean mWasEndReached = false;

    RecyclerAdapter(final Context context,
                    final List<Data> data,
                    final MainScreenListener mainScreenListener) {
        Validator.isArgNotNull(context, "context");
        Validator.isArgNotNull(data, "data");
        Validator.isArgNotNull(mainScreenListener, "mainScreenListener");

        mData = data;
        mContext = context;
        mMainScreenListener = mainScreenListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view;
        switch (viewType) {
            case CONTENT_TYPE:
                view = LayoutInflater.from(parent.getContext())
                                     .inflate(R.layout.list_item, parent, false);
                break;
            case BUTTON_TYPE:
            default:
                view = LayoutInflater.from(parent.getContext())
                                     .inflate(R.layout.load_more, parent, false);
        }
        return new MyViewHolder(view);
    }

    @Override
    public int getItemViewType(final int position) {
        if (position < getItemCount() - 1) {
            return CONTENT_TYPE;
        } else {
            return BUTTON_TYPE;
        }
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        if (getItemViewType(position) == BUTTON_TYPE) {

            if (checkButtonIsVisible(holder.mLoadMoreButton)) {
                holder.mLoadMoreButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        holder.mLoadMoreButton.setVisibility(INVISIBLE);
                        mMainScreenListener.onLoadMoreButtonClicked();
                    }
                });
            }

        } else {
            final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(mContext);
            final String loadMoreType = sp.getString(mContext.getResources()
                                                             .getString(R.string.pref_key),
                                                     LOAD_MORE_BUTTON_TYPE);

            if (loadMoreType.equalsIgnoreCase(LOAD_MORE_SCROLL_TYPE)
                    && position == mData.size() - DEFAULT_GIF_COUNT_LIMIT / 2
                    && !mWasEndReached) {
                mMainScreenListener.onLoadMoreButtonClicked();
            }

            final String gifUrl = mData.get(position).getImages().getOriginal().getUrl();
            Glide.with(mContext)
                 .load(gifUrl)
                 .apply(RequestOptions.placeholderOf(R.mipmap.ic_downloading))
                 .into(holder.mImageView);

            holder.mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    mMainScreenListener.onGifClicked(gifUrl);
                }
            });

        }

    }


    @Override
    public int getItemCount() {
        return mData.size() + 1;
    }

    private boolean checkButtonIsVisible(final Button loadMoreButton) {
        Validator.isArgNotNull(loadMoreButton, "loadMoreButton");

        int visibility = loadMoreButton.getVisibility();
        boolean isVisible = visibility == VISIBLE;
        if (wasEndReached()) {
            if (visibility == VISIBLE) {
                visibility = INVISIBLE;
                isVisible = false;
            }
        } else {
            if (visibility == INVISIBLE) {
                visibility = VISIBLE;
                isVisible = true;
            }
        }
        loadMoreButton.setVisibility(visibility);
        return isVisible;
    }

    private boolean wasEndReached() {
        return mData.size() == mTotalCount;
    }

    public void updateData(final List<Data> data, final int totalCount) {
        Validator.isArgNotNull(data, "data");
        if (mTotalCount != totalCount) {
            mWasEndReached = false;
            mTotalCount = totalCount;
        }
        mData.clear();
        mData.addAll(data);
        if (mTotalCount == mData.size()) {
            mWasEndReached = true;
        }
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private final ImageView mImageView;
        private final Button mLoadMoreButton;

        MyViewHolder(@Nonnull final View itemView) {
            super(itemView);
            Validator.isArgNotNull(itemView, "itemView");
            mImageView = itemView.findViewById(R.id.imageView);
            mLoadMoreButton = itemView.findViewById(R.id.loadMoreButton);
        }
    }
}