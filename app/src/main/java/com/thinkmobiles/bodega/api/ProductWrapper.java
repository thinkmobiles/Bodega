package com.thinkmobiles.bodega.api;

import android.os.Parcel;
import android.os.Parcelable;

import com.cristaliza.mvc.models.estrella.Product;

import java.io.Serializable;
import java.util.List;

/**
 * Created by denis on 23.10.15.
 */
public class ProductWrapper implements Parcelable {

    private String id = null;
    private String name = null;
    private String category = null;
    private List<String> families = null;
    private List<String> familyImages = null;
    private String image = null;
    private String imageSmall = null;
    private String imageDescription = null;
    private String backgroundImage = null;
    private String EAN = null;
    private List<String> packaging = null;
    private String update = null;
    private List<String> options = null;
    private List<String> optionsImages = null;
    private List<String> galleriesImages = null;

    public ProductWrapper() {
    }

    public ProductWrapper(Product product) {
        id = product.getId();
        name = product.getName();
        category = product.getCategory();
        families = product.getFamilies();
        familyImages = product.getFamilyImages();
        image = product.getImage();
        imageSmall = product.getImageSmall();
        imageDescription = product.getImageDescription();
        backgroundImage = product.getBackgroundImage();
        EAN = product.getEAN();
        packaging = product.getPackaging();
        update = product.getUpdate();
        options = product.getOptions();
        optionsImages = product.getOptionsImages();
        galleriesImages = product.getGalleriesImages();
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<String> getFamilies() {
        return this.families;
    }

    public void setFamily(List<String> families) {
        this.families = families;
    }

    public List<String> getFamilyImages() {
        return this.familyImages;
    }

    public void setFamilyImages(List<String> familyImages) {
        this.familyImages = familyImages;
    }

    public String getBackgroundImage() {
        return this.backgroundImage;
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEAN() {
        return this.EAN;
    }

    public void setEAN(String eAN) {
        this.EAN = eAN;
    }

    public List<String> getPackaging() {
        return this.packaging;
    }

    public void setPackaging(List<String> packaging) {
        this.packaging = packaging;
    }

    public String getImageDescription() {
        return this.imageDescription;
    }

    public void setImageDescription(String imageDescription) {
        this.imageDescription = imageDescription;
    }

    public String getImageSmall() {
        String img = this.imageSmall;
        if(img == null || "".equals(this.imageSmall)) {
            img = this.image;
        }

        return img;
    }

    public void setImageSmall(String imageSmall) {
        this.imageSmall = imageSmall;
    }

    public String getUpdate() {
        return this.update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }

    public List<String> getOptions() {
        return this.options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public List<String> getOptionsImages() {
        return this.optionsImages;
    }

    public void setOptionsImages(List<String> optionsImages) {
        this.optionsImages = optionsImages;
    }

    public List<String> getGalleriesImages() {
        return this.galleriesImages;
    }

    public void setGalleriesImages(List<String> galleriesImages) {
        this.galleriesImages = galleriesImages;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.category);
        dest.writeStringList(this.families);
        dest.writeStringList(this.familyImages);
        dest.writeString(this.image);
        dest.writeString(this.imageSmall);
        dest.writeString(this.imageDescription);
        dest.writeString(this.backgroundImage);
        dest.writeString(this.EAN);
        dest.writeStringList(this.packaging);
        dest.writeString(this.update);
        dest.writeStringList(this.options);
        dest.writeStringList(this.optionsImages);
        dest.writeStringList(this.galleriesImages);
    }

    protected ProductWrapper(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.category = in.readString();
        this.families = in.createStringArrayList();
        this.familyImages = in.createStringArrayList();
        this.image = in.readString();
        this.imageSmall = in.readString();
        this.imageDescription = in.readString();
        this.backgroundImage = in.readString();
        this.EAN = in.readString();
        this.packaging = in.createStringArrayList();
        this.update = in.readString();
        this.options = in.createStringArrayList();
        this.optionsImages = in.createStringArrayList();
        this.galleriesImages = in.createStringArrayList();
    }

    public static final Creator<ProductWrapper> CREATOR = new Creator<ProductWrapper>() {
        public ProductWrapper createFromParcel(Parcel source) {
            return new ProductWrapper(source);
        }

        public ProductWrapper[] newArray(int size) {
            return new ProductWrapper[size];
        }
    };
}
