package br.com.xyinc.baas.config;


import br.com.xyinc.baas.repository.StringToLocalDateTimeConverter;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.convert.CustomConversions;

import java.util.ArrayList;
import java.util.List;


@Configuration
public class ConversionConfiguration extends AbstractMongoConfiguration {

    private final List<Converter<?, ?>> converters = new ArrayList<>();

    @Override
    protected String getDatabaseName() {
        return "test";
    }

    @Override
    public Mongo mongo() throws Exception {
        return new MongoClient("127.0.0.1", 27017);
    }

    @Override
    public CustomConversions customConversions() {
        converters.add(new StringToLocalDateTimeConverter());
        return new CustomConversions(converters);
    }

}
