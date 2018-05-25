package com.target.dealbrowserpoc.dealbrowser.ui;

import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.target.dealbrowserpoc.dealbrowser.core.models.DealItem;

import java.util.List;

/**
 * Created by CropIn-Shailendra on 5/24/2018.
 */

@JsonObject(fieldDetectionPolicy = JsonObject.FieldDetectionPolicy.NONPRIVATE_FIELDS_AND_ACCESSORS)
public class Content<T extends DealItem> {
    private List<T> data;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
