package com.restfriedchicken.rest.orders;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.restfriedchicken.rest.Link;

import java.util.ArrayList;
import java.util.List;


public class MyOrderRepresentation {
    @JsonProperty("tracking_id")
    private String trackingId;

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

    public boolean isAvailableToMakePayment() {
        for (Link link : links) {
            if ("payment".equals(link.getRel())) {
                return true;
            }
        }
        return false;
    }


    public boolean isAvailableToCancel() {
        for (Link link : links) {
            if ("cancel".equals(link.getRel())) {
                return true;
            }
        }
        return false;
    }

    public Link getCancelLink() {
        return getLink("cancel");
    }
}
