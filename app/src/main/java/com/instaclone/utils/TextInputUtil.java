package com.instaclone.utils;

import android.widget.EditText;

public class TextInputUtil {

    public static void setError(EditText email, String string) {

        email.setError(string);


    }

    public static void setDisableError(EditText email) {

        email.setError(null);

    }
}
