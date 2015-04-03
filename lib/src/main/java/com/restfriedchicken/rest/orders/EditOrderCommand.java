package com.restfriedchicken.rest.orders;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect
public class EditOrderCommand {
    @JsonProperty("amount")
    private String amount;

    public EditOrderCommand(String amount) {
        this.amount = amount;
    }
}
