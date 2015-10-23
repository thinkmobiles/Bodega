package com.thinkmobiles.bodega.api;

import android.os.Parcel;
import android.os.Parcelable;

import com.cristaliza.mvc.models.estrella.Item;

import java.io.Serializable;
import java.util.List;

/**
 * Created by denis on 23.10.15.
 */
public class ItemWrapper implements Serializable {

    private String id = null;
    private String name = null;
    private String sublevel = null;
    private String icon = null;
    private String backgroundImage = null;
    private String extraBackgroundImage = null;
    private boolean isProduct = false;
    private String update = null;
    private String fichaCata = null;
    private String logo = null;
    private List<String> prizes = null;
    private List<String> extraImages = null;
    private List<String> extraImagesDescription = null;
    private List<String> extraVideos = null;
    private List<String> extraVideosDescription = null;
    private String pdf = null;
    private String description = null;
    private String report = null;

    public ItemWrapper() {
    }

    public ItemWrapper(Item item) {
        id = item.getId();
        name = item.getName();
        sublevel = item.getSublevel();
        icon = item.getIcon();
        backgroundImage = item.getBackgroundImage();
        extraBackgroundImage = item.getExtraBackgroundImage();
        isProduct = item.isProduct();
        update = item.getUpdate();
        fichaCata = item.getFichaCata();
        logo = item.getLogo();
        prizes = item.getPrizes();
        extraImages = item.getExtraImages();
        extraImagesDescription = item.getExtraImagesDescription();
        extraVideos = item.getExtraVideos();
        extraVideosDescription = item.getExtraImagesDescription();
        pdf = item.getPdf();
        description = item.getDescription();
        report = item.getReport();
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

    public String getSublevel() {
        return this.sublevel;
    }

    public void setSublevel(String sublevel) {
        this.sublevel = sublevel;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getExtraBackgroundImage() {
        return this.extraBackgroundImage;
    }

    public void setExtraBackgroundImage(String extraBackgroundImage) {
        this.extraBackgroundImage = extraBackgroundImage;
    }

    public boolean isProduct() {
        return this.isProduct;
    }

    public void setProduct(boolean isProduct) {
        this.isProduct = isProduct;
    }

    public String getUpdate() {
        return this.update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }

    public String getBackgroundImage() {
        return this.backgroundImage;
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public String getFichaCata() {
        return this.fichaCata;
    }

    public void setFichaCata(String fichaCata) {
        this.fichaCata = fichaCata;
    }

    public String getLogo() {
        return this.logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public List<String> getPrizes() {
        return this.prizes;
    }

    public void setPrizes(List<String> prizes) {
        this.prizes = prizes;
    }

    public List<String> getExtraImages() {
        return this.extraImages;
    }

    public void setExtraImages(List<String> extraImages) {
        this.extraImages = extraImages;
    }

    public List<String> getExtraImagesDescription() {
        return this.extraImagesDescription;
    }

    public void setExtraImagesDescription(List<String> extraImagesDescription) {
        this.extraImagesDescription = extraImagesDescription;
    }

    public List<String> getExtraVideos() {
        return this.extraVideos;
    }

    public void setExtraVideos(List<String> extraVideos) {
        this.extraVideos = extraVideos;
    }

    public List<String> getExtraVideosDescripton() {
        return this.extraVideosDescription;
    }

    public void setExtraVideosDescription(List<String> extraVideosDescription) {
        this.extraVideosDescription = extraVideosDescription;
    }

    public String getPdf() {
        return this.pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReport() {
        return this.report;
    }

    public void setReport(String report) {
        this.report = report;
    }
}
