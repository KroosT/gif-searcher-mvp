package com.qulix.shestakaa.gifsearchermvp.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import javax.annotation.Nonnull;

public class ViewUtils {

    public static void showSoftKeyboard(@Nonnull final View view){
        Validator.isArgNotNull(view, "view");
        if(view.requestFocus()){
            final InputMethodManager imm =(InputMethodManager) view.getContext().
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    public static void hideSoftKeyboard(@Nonnull final View view){
        Validator.isArgNotNull(view, "view");
        final InputMethodManager imm =(InputMethodManager) view.getContext().
                getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
