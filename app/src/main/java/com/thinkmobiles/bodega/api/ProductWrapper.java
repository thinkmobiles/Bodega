package com.thinkmobiles.bodega.api;

import com.cristaliza.mvc.models.estrella.Product;

import java.io.Serializable;
import java.util.List;

/**
 * Created by denis on 23.10.15.
 */
public class ProductWrapper implements Serializable {
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
}
