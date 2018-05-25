package com.target.dealbrowserpoc.dealbrowser.core.dataprovider;

import android.content.Context;

import com.bluelinelabs.logansquare.ParameterizedType;
import com.target.dealbrowserpoc.dealbrowser.core.models.DealItem;
import com.target.dealbrowserpoc.dealbrowser.core.network.RestClient;
import com.target.dealbrowserpoc.dealbrowser.ui.Content;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by CropIn-Shailendra on 5/24/2018.
 */

public class TargetRepository {

    private Context context;
    private RestClient restClient;
    private final String endPoint = "http://target-deals.herokuapp.com/api/deals";

    public TargetRepository(Context context) {
        this.context = context;
        restClient = new RestClient(context);
    }

    public List<DealItem> getDeals() {
        List<DealItem> dealItemList = new ArrayList<>();
        if (restClient.isOnline()) {
            ParameterizedType<Content<DealItem>> parameterizedType = new ParameterizedType<Content<DealItem>>() {
                @Override
                public boolean equals(Object o) {
                    return super.equals(o);
                }
            };
            HashMap<String, Object> response = restClient.get(endPoint, parameterizedType);
            if (restClient.isSuccessful(response)) {
                Content<DealItem> dealItemContent = (Content<DealItem>) restClient.getData(response);
                dealItemList = dealItemContent.getData();
            }
        }
        return dealItemList;
    }
}
