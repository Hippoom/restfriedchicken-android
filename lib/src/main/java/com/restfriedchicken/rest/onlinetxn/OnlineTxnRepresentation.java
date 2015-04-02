package com.restfriedchicken.rest.onlinetxn;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.restfriedchicken.rest.Link;

import java.util.ArrayList;
import java.util.List;


public class OnlineTxnRepresentation {
    @JsonProperty("tracking_id")
    private String trackingId;

    private String amount;

    private String status;

    @JsonProperty("_links")
    private List<Link> links = new ArrayList<Link>();

    public String getTrackingId() {
        return trackingId;
    }

    public String getStatus() {
        return status;
    }


    public Link getLink(String rel) {
        for (Link link : links) {
            if (rel.equals(link.getRel())) {
                return link;
            }
        }
        return null;
    }

    public String getAmount() {
        return amount;
    }
}
