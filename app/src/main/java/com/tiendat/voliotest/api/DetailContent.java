package com.tiendat.voliotest.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DetailContent {
    private String title;
    private String description;
    private String datePublish;

    @SerializedName("href")
    @Expose
    private String href;
    @SerializedName("caption")
    @Expose
    private String caption;
    @SerializedName("main_color")
    @Expose
    private String mainColor;
    @SerializedName("original_width")
    @Expose
    private int originalWidth;
    @SerializedName("original_height")
    @Expose
    private int originalHeight;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("markups")
    @Expose
    private List<MarkUp> markUps;
    @SerializedName("duration")
    @Expose
    private int duration;
    @SerializedName("preview_image")
    @Expose
    private Image prevImg;


    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getMainColor() {
        return mainColor;
    }

    public void setMainColor(String mainColor) {
        this.mainColor = mainColor;
    }

    public int getOriginalWidth() {
        return originalWidth;
    }

    public void setOriginalWidth(int originalWidth) {
        this.originalWidth = originalWidth;
    }

    public int getOriginalHeight() {
        return originalHeight;
    }

    public void setOriginalHeight(int originalHeight) {
        this.originalHeight = originalHeight;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<MarkUp> getMarkUps() {
        return markUps;
    }

    public void setMarkUps(List<MarkUp> markUps) {
        this.markUps = markUps;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Image getPrevImg() {
        return prevImg;
    }

    public void setPrevImg(Image prevImg) {
        this.prevImg = prevImg;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDatePublish() {
        return datePublish;
    }

    public void setDatePublish(String datePublish) {
        this.datePublish = datePublish;
    }
}
