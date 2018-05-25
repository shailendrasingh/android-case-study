package com.target.dealbrowserpoc.dealbrowser.core.models;

import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.io.Serializable;

@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class DealItem implements Serializable {
    private int index;
    private String _id;
    private String guid;
    private String title;
    private String description;
    private String price;
    private String salePrice;
    private String image;
    private String aisle;

//    public DealItem(int index, String id, String title, String description, String originalPrice, String salePrice, int image, String aisle) {
//        this.index = index;
//        this._id = id;
//        this.title = title;
//        this.description = description;
//        this.price = originalPrice;
//        this.salePrice = salePrice;
//        this.image = image;
//        this.aisle = aisle;
//    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String originalPrice) {
        this.price = originalPrice;
    }

    public String getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(String salePrice) {
        this.salePrice = salePrice;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAisle() {
        return aisle;
    }

    public void setAisle(String aisle) {
        this.aisle = aisle;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    @Override
    public String toString() {
        return title;
    }
}