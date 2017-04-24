package br.com.xyinc.baas.it;


import br.com.xyinc.baas.BaasApplication;
import br.com.xyinc.baas.domain.AttributeType;
import br.com.xyinc.baas.domain.MetaAttribute;
import br.com.xyinc.baas.domain.MetaModel;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.nio.charset.Charset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public abstract class AbstractRestIT extends AbstractBaseIT {

    protected MediaType contentType = new MediaType( MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName( "utf8" ) );


    public static byte[] convertObjectToJsonBytes(Object object)
        throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable( MapperFeature.USE_ANNOTATIONS);

        JavaTimeModule module = new JavaTimeModule();
        module.addSerializer(ZonedDateTime.class, new ZonedDateTimeSerializer(DateTimeFormatter.ofPattern( BaasApplication.DATE_FORMAT )));
        mapper.registerModule(module);

        return mapper.writeValueAsBytes(object);
    }

    public MetaModel createMetaModel(String name) {

        List<MetaAttribute> metaAttributes = new ArrayList<>( );
        metaAttributes.add( new MetaAttribute( "name", AttributeType.STRING ) );
        metaAttributes.add( new MetaAttribute( "price", AttributeType.DECIMAL ) );
        metaAttributes.add( new MetaAttribute( "builtAt", AttributeType.DATE ) );
        metaAttributes.add( new MetaAttribute( "number", AttributeType.INTEGER ) );

        if(name == null || name.isEmpty()) {
            name = RandomStringUtils.randomAlphabetic( 5 );
        }
        MetaModel metaModel = new MetaModel( null, name, metaAttributes );

        return metaModel;
    }

    public Map createModel() {

        Map model = new LinkedHashMap(  );
        model.put( "name", RandomStringUtils.randomAlphabetic( 5 ) );
        model.put( "price", 4300.5324 );
        model.put( "builtAt", "2011-04-08T09:00:00.000" );
        model.put( "number", 3000 );

        return model;
    }

}