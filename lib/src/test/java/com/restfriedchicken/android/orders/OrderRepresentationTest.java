package com.restfriedchicken.android.orders;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restfriedchicken.rest.orders.OrderRepresentation;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class OrderRepresentationTest {

    @Test
    public void should_be_available_to_make_payment_given_payment_link() throws IOException {

        OrderRepresentation order = objectMapper().readValue(fileContent("order-available-to-make-payment.json"), OrderRepresentation.class);

        assertTrue("The order should suggest payment making", order.isAvailableToMakePayment());
    }

    private File fileContent(String fileName) {
        return new File(getClass().getClassLoader().getResource(fileName).getFile());
    }

    @Test
    public void should_be_available_to_cancel_given_cancel_link() throws IOException {
        OrderRepresentation order = objectMapper().readValue(fileContent("order-available-to-cancel.json"), OrderRepresentation.class);

        assertTrue("The order should suggest cancel", order.isAvailableToCancel());
    }

    protected ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }
}
