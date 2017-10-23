package com.qulix.shestakaa.gifsearchermvp.model.gifdetails;

import android.content.Context;
import android.os.AsyncTask;

import com.qulix.shestakaa.gifsearchermvp.utils.Cancelable;
import com.qulix.shestakaa.gifsearchermvp.utils.Downloadable;
import com.qulix.shestakaa.gifsearchermvp.utils.Validator;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class DetailsModelImpl implements DetailsModelInterface {

    @Override
    public Cancelable saveGifByUrl(final Context context,
                                   final String url,
                                   final Downloadable downloadable) {
        Validator.isArgNotNull(url, "url");

        final GifDownloader gifDownloader = new GifDownloader(context, downloadable);
        gifDownloader.execute(url);

        return new Cancelable() {
            @Override
            public void onCancel() {
                gifDownloader.cancel(true);
            }
        };
    }

    private class GifDownloader extends AsyncTask<String, Void, Boolean> {

        private static final String FILENAME = "gif_";
        private static final String SAVED_GIFS_FOLDER = "/SavedGifs/";
        public static final String EXTENSION = ".gif";
        private final Downloadable mDownloadable;
        private final Context mContext;

        public GifDownloader(final Context context, final Downloadable downloadable) {
            mDownloadable = downloadable;
            mContext = context;
        }

        @Override
        protected Boolean doInBackground(final String... urls) {
            try {
                final URL u = new URL(urls[0]);
                final InputStream in = u.openStream();
                final File saveFileDirectory = new File(mContext.getFilesDir()
                        + SAVED_GIFS_FOLDER);
                saveFileDirectory.mkdirs();
                System.out.println(saveFileDirectory.getPath()
                        + File.separator
                        + FILENAME);
                final File saveFile = new File(saveFileDirectory.getPath()
                        + File.separator
                        + FILENAME
                        + new SimpleDateFormat("yyyyMMdd_HHmmss-SSS", Locale.US)
                        .format(new Date())
                        + EXTENSION);
                saveFile.createNewFile();
                final FileOutputStream out = new FileOutputStream(saveFile);

                IOUtils.copy(in, out);
                out.close();
                in.close();
            } catch (final IOException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean result) {
            mDownloadable.onDownload(result);
        }
    }
}
