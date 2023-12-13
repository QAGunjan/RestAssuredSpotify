
package com.spotify.oauth2.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)

public class Item {

    @JsonProperty("added_at")
    private String addedAt;
    @JsonProperty("added_by")
    private AddedBy addedBy;
    @JsonProperty("is_local")
    private Boolean isLocal;
    @JsonProperty("primary_color")
    private Object primaryColor;
    @JsonProperty("track")
    private Track track;
    @JsonProperty("video_thumbnail")
    private VideoThumbnail videoThumbnail;

    @JsonProperty("added_at")
    public String getAddedAt() {
        return addedAt;
    }

    @JsonProperty("added_at")
    public void setAddedAt(String addedAt) {
        this.addedAt = addedAt;
    }

    @JsonProperty("added_by")
    public AddedBy getAddedBy() {
        return addedBy;
    }

    @JsonProperty("added_by")
    public void setAddedBy(AddedBy addedBy) {
        this.addedBy = addedBy;
    }

    @JsonProperty("is_local")
    public Boolean getIsLocal() {
        return isLocal;
    }

    @JsonProperty("is_local")
    public void setIsLocal(Boolean isLocal) {
        this.isLocal = isLocal;
    }

    @JsonProperty("primary_color")
    public Object getPrimaryColor() {
        return primaryColor;
    }

    @JsonProperty("primary_color")
    public void setPrimaryColor(Object primaryColor) {
        this.primaryColor = primaryColor;
    }

    @JsonProperty("track")
    public Track getTrack() {
        return track;
    }

    @JsonProperty("track")
    public void setTrack(Track track) {
        this.track = track;
    }

    @JsonProperty("video_thumbnail")
    public VideoThumbnail getVideoThumbnail() {
        return videoThumbnail;
    }

    @JsonProperty("video_thumbnail")
    public void setVideoThumbnail(VideoThumbnail videoThumbnail) {
        this.videoThumbnail = videoThumbnail;
    }

}
