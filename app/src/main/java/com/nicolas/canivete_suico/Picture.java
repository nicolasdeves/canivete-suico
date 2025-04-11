package com.nicolas.canivete_suico;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Picture {
    private String base64;
    private String date;

    public Picture(String base64, String date) {
        this.base64 = base64;
        this.date = date;
    }

    public Picture(String base64) {
        this.base64 = base64;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        this.date = sdf.format(new Date());
    }

    public String getBase64() {
        return this.base64;
    }

    public String getDate() {
        return this.date;
    }
}
