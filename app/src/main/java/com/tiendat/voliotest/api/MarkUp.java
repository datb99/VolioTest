package com.tiendat.voliotest.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MarkUp {
    @SerializedName("markup_type")
    @Expose
    private int type;
    @SerializedName("start")
    @Expose
    private int start;
    @SerializedName("end")
    @Expose
    private int end;
    @SerializedName("href")
    @Expose
    private String href;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
}
