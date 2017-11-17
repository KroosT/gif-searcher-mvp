package com.qulix.shestakaa.gifsearchermvp.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class AdapterData {

    private List<String> mUrls;
    private boolean mIsButtonPresents;
    private boolean mIsProgressBarPresents;

    public AdapterData() {

        mUrls = new ArrayList<>();
        mIsButtonPresents = false;
        mIsProgressBarPresents = false;
    }

    public AdapterData(final AdapterData data) {
        Validator.isArgNotNull(data, "data");

        mUrls = data.getUrls();
        mIsButtonPresents = data.isButtonPresents();
        mIsProgressBarPresents = data.isProgressBarPresents();
    }

    public int size() {
        int additional = 0;
        if(mIsButtonPresents) {
            additional += 1;
        }
        if (mIsProgressBarPresents) {
            additional += 1;
        }
        return mUrls.size() + additional;
    }

    public List<String> getUrls() {
        return Collections.unmodifiableList(mUrls);
    }

    public boolean isProgressBarPresents() {
        return mIsProgressBarPresents;
    }

    public boolean isButtonPresents() {

        return mIsButtonPresents;
    }

    public void setUrls(final List<String> urls) {
        Validator.isArgNotNull(urls, "urls");
        mUrls = new ArrayList<>(urls);
    }

    public void setButtonPresents(final boolean buttonPresents) {
        Validator.isArgNotNull(buttonPresents, "buttonPresents");
        mIsButtonPresents = buttonPresents;
    }

    public void setProgressBarPresents(final boolean progressBarPresents) {
        Validator.isArgNotNull(progressBarPresents, "progressBarPresents");
        mIsProgressBarPresents = progressBarPresents;
    }
}
