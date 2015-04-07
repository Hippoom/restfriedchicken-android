package com.restfriedchicken.android.http;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restfriedchicken.android.orders.CancelMyOrderTask;
import com.restfriedchicken.android.orders.EditOrderTask;
import com.restfriedchicken.android.orders.GetMyOrderTask;
import com.restfriedchicken.android.orders.GetMyOrdersTask;
import com.restfriedchicken.android.orders.MakePaymentTask;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

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

    @Provides
    public MappingJackson2HttpMessageConverter jsonMessageConverter(ObjectMapper objectMapper) {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(objectMapper);
        return converter;
    }

    @Provides
    public RestTemplate restTemplate(MappingJackson2HttpMessageConverter converter) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(converter);
        return restTemplate;
    }


}
