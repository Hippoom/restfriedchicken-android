package com.restfriedchicken.rest.onlinetxn;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect
public class MakeOnlineTxnCommand {
    @JsonProperty("amount")
    private String amount;

    @JsonProperty("cc_num")
    private String creditCardNumber;

    @JsonProperty("cc_expire_date")
    private String creditCardExpireDate;

    @JsonProperty("cc_cvv2")
    private String creditCardCvv2;

    public MakeOnlineTxnCommand(String amount, String creditCardNumber, String creditCardExpireDate, String creditCardCvv2) {
        this.amount = amount;
        this.creditCardNumber = creditCardNumber;
        this.creditCardExpireDate = creditCardExpireDate;
        this.creditCardCvv2 = creditCardCvv2;
    }
}
