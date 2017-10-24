package com.qulix.shestakaa.gifsearchermvp.model.offline;

import android.content.Context;
import android.os.AsyncTask;

import com.qulix.shestakaa.gifsearchermvp.utils.Loadable;
import com.qulix.shestakaa.gifsearchermvp.utils.Validator;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
class GifLoader extends AsyncTask<Void, Void, List<byte[]>> {

    private static final String SAVED_GIFS_FOLDER = "/SavedGifs/";
    private final Context mContext;
    private final Loadable mRequest;

    public GifLoader(final Context context, final Loadable loadable) {
        Validator.isArgNotNull(context, "context");
        Validator.isArgNotNull(loadable, "loadable");

        mContext = context;
        mRequest = loadable;
    }

    @Override
    protected List<byte[]> doInBackground(final Void... params) {

        final List<byte[]> gifsBytes = new ArrayList<>();
        try {
            final File dir = new File(mContext.getFilesDir()
                    + File.separator + SAVED_GIFS_FOLDER);
            File[] listFiles = null;
            if (dir.exists()) {
                listFiles = dir.listFiles();
            }
            if (listFiles == null) {
                return new ArrayList<>();
            }
            for (final File f : listFiles) {
                gifsBytes.add(IOUtils.toByteArray(f.toURI()));
            }
        } catch (final IOException e) {
            return null;
        }
        return gifsBytes;
    }

    @Override
    protected void onPostExecute(final List<byte[]> result) {
        mRequest.onLoad(result);
    }
}
