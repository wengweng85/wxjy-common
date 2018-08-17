package com.insigma.weixin.pojo;


public class Image {

    private String MediaId;

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }

    @Override
    public String toString() {
        return "Image [MediaId=" + MediaId + "]";
    }
    
}