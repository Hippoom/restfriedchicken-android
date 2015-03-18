package com.restfriedchicken.android.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Link {
    private String rel;
    private String href;

    public String getRel() {
        return rel;
    }

    public String getHref() {
        return href;
    }
}
