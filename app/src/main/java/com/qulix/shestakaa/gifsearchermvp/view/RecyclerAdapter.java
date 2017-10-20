package com.qulix.shestakaa.gifsearchermvp.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.qulix.shestakaa.gifsearchermvp.R;
import com.qulix.shestakaa.gifsearchermvp.model.API.Data;

import java.util.List;

import javax.annotation.Nonnull;

import com.qulix.shestakaa.gifsearchermvp.presenter.Presenter;
import com.qulix.shestakaa.gifsearchermvp.utils.Validator;

class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private List<Data> mData;
    private final Context mContext;
    private Presenter mPresenter;

    RecyclerAdapter(final Context context, final List<Data> data) {
        mData = data;
        mContext = context;
    }

    public void setPresenter(final Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                                        .inflate(R.layout.list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final String gifUrl = mData.get(position).getImages().getOriginal().getUrl();
        Glide.with(mContext)
             .load(gifUrl)
             .apply(RequestOptions.placeholderOf(R.mipmap.ic_downloading))
             .into(holder.mImageView);

        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                mPresenter.onGifClick(gifUrl);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void updateData(@Nonnull final List<Data> data) {
        Validator.isArgNotNull(data, "data");
        mData = data;
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private final ImageView mImageView;

        MyViewHolder(@Nonnull final View itemView) {
            super(itemView);
            Validator.isArgNotNull(itemView, "itemView");
            mImageView = itemView.findViewById(R.id.imageView);
        }
    }
}