package com.tiendat.voliotest.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Section {
    @SerializedName("section_type")
    @Expose
    private int type;
    @SerializedName("content")
    @Expose
    private DetailContent content;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public DetailContent getContent() {
        return content;
    }

    public void setContent(DetailContent content) {
        this.content = content;
    }
}
