package com.restfriedchicken.android.http;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restfriedchicken.android.orders.CancelMyOrderTask;
import com.restfriedchicken.android.orders.EditOrderTask;
import com.restfriedchicken.android.orders.GetMyOrderTask;
import com.restfriedchicken.android.orders.GetMyOrdersTask;
import com.restfriedchicken.android.orders.MakePaymentTask;

import dagger.Module;
import dagger.Provides;

@Module(injects = {GetMyOrdersTask.class, GetMyOrderTask.class, EditOrderTask.class, CancelMyOrderTask.class, MakePaymentTask.class})
public class HttpModule {

    @Provides
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }

}
