package com.thinkmobiles.bodega.api;

/**
 * Created by illia on 29.10.15.
 */
public class ExtraWrapper {
    private String image;
    private String imageDescriprion;
    private String video;
    private String videoDescription;

    public ExtraWrapper() {

    }

    public ExtraWrapper(String _image, String _imageDescriprion, String _video, String _videoDescription) {
        image = _image;
        imageDescriprion = _imageDescriprion;
        video = _video;
        videoDescription = _videoDescription;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String _image) {
        image = _image;
    }

    public String getImageDescriprion() {
        return imageDescriprion;
    }

    public void setImageDescriprion(String _imageDescriprion) {
        imageDescriprion = _imageDescriprion;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String _video) {
        video = _video;
    }

    public String getVideoDescription() {
        return videoDescription;
    }

    public void setVideoDescription(String _videoDescription) {
        videoDescription = _videoDescription;
    }
}
