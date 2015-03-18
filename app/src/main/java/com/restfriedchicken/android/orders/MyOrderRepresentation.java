package com.restfriedchicken.android.orders;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.restfriedchicken.android.rest.Link;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MyOrderRepresentation {
    @JsonProperty("tracking_id")
    private String trackingId;

    @JsonProperty("_links")
    private List<Link> links = new ArrayList<>();

    public String getTrackingId() {
        return trackingId;
    }

    public boolean isPayable() {
        for (Link link: links) {
            if ("payment".equals(link.getRel())) {
                return true;
            }
        }
        return false;
    }
}
