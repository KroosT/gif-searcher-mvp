package com.qulix.shestakaa.gifsearchermvp.view.offline;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.qulix.shestakaa.gifsearchermvp.R;
import com.qulix.shestakaa.gifsearchermvp.utils.Validator;

import java.util.List;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class OfflineAdapter extends RecyclerView.Adapter<OfflineAdapter.MyViewHolder> {

    private final List<byte[]> mBytes;
    private final Context mContext;

    public OfflineAdapter(final Context context, final List<byte[]> bytes) {
        Validator.isArgNotNull(context, "context");
        Validator.isArgNotNull(bytes, "bytes");

        mBytes = bytes;
        mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                                        .inflate(R.layout.list_item, parent, false);
        return new OfflineAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final byte[] gifBytes = mBytes.get(position);
        Glide.with(mContext)
             .asGif()
             .load(gifBytes)
             .apply(RequestOptions.placeholderOf(R.mipmap.ic_downloading))
             .into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mBytes.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final ImageView mImageView;

        MyViewHolder(final View itemView) {
            super(itemView);
            Validator.isArgNotNull(itemView, "itemView");
            mImageView = itemView.findViewById(R.id.imageView);
        }
    }

    public void updateDate(final List<byte[]> bytes) {
        Validator.isArgNotNull(bytes, "bytes");
        mBytes.clear();
        mBytes.addAll(bytes);
        System.out.println(mBytes);
        notifyDataSetChanged();
    }
}
