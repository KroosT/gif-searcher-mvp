package com.qulix.shestakaa.gifsearchermvp.utils;

public class StringUtils {

    public static boolean isNotNullOrBlank(final String arg) {
        Validator.isArgNotNull(arg, "arg");
        return !arg.trim().isEmpty();
    }

}
