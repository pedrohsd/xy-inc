package br.com.xyinc.baas.config;


import br.com.xyinc.baas.BaasApplication;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.text.SimpleDateFormat;


@Configuration
public class SerializationConfiguration {

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JSR310Module());
        mapper.setDateFormat(new SimpleDateFormat( BaasApplication.DATE_FORMAT));
        return mapper;
    }
}
