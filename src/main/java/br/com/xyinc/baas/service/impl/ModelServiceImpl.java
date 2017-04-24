package br.com.xyinc.baas.service.impl;


import br.com.xyinc.baas.BaasApplication;
import br.com.xyinc.baas.domain.AttributeType;
import br.com.xyinc.baas.domain.MetaModel;
import br.com.xyinc.baas.repository.ModelRepository;
import br.com.xyinc.baas.service.MetaModelService;
import br.com.xyinc.baas.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import static br.com.xyinc.baas.domain.AttributeType.*;
import static br.com.xyinc.baas.util.PreCondition.isTrue;


@Service
public class ModelServiceImpl implements ModelService {

    @Autowired
    private MetaModelService metaModelService;

    @Autowired
    private ModelRepository modelRepository;


    @Override
    public void save( String name, Object model ) {
        MetaModel metaModel = metaModelService.findByName(name);
        validate(metaModel, model);
        modelRepository.save( name, model );
    }

    @Override
    public Object findById( String name, String id ) {
        return modelRepository.findById(name, id, metaModelService.getEntityClass(name));
    }


    @Override
    public List<?> findAll( String name ) {
        return modelRepository.findAll( name, metaModelService.getEntityClass(name) );
    }


    private void validate( MetaModel metaModel, Object model ) {

        Map<String, Object> attributes = (LinkedHashMap) model;

        attributes.forEach( ( k, v ) -> {
            final AtomicBoolean found = new AtomicBoolean( false );
            metaModel.getMetaAttributes().forEach( ma -> {
                if ( ma.getName().equals( k ) && !k.equals( "_id" )) {
                    found.set( true );
                    String errorMsg = "Invalid attribute type for %s. It must be of type %s and was %s";

                    if ( ma.getType().equals( INTEGER ) ) {
                        isTrue( v instanceof Integer || v instanceof Long, errorMsg, k, "integer", v.getClass() );
                    } else if ( ma.getType().equals( DECIMAL ) ) {
                        isTrue( v instanceof Float || v instanceof Double, errorMsg, k, "double", v.getClass() );
                    } else if ( ma.getType().equals( STRING ) ) {
                        isTrue( v instanceof String, errorMsg, k, "string", v.getClass() );
                    } else if ( ma.getType().equals( DATE ) ) {
                        try {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern( BaasApplication.DATE_FORMAT );
                            LocalDateTime.parse( (String) v, formatter );
                        } catch ( DateTimeParseException e ) {
                            throw new IllegalArgumentException( String
                                .format( "Could not parse text %s. Correct date format is %s", v, BaasApplication.DATE_FORMAT ) );
                        }
                    } else {
                        throw new IllegalArgumentException( String
                            .format( "Invalid type %s for %s. Permitted types: %s", v.getClass(), k, AttributeType.values() ) );
                    }
                }
            } );
            if ( !found.get() && !k.equals( "_id" )) {
                throw new IllegalArgumentException( String.format( "Invalid model attribute: %s. Allowed attributes: %s",
                    k,
                    metaModel.getMetaAttributes().stream().map( m -> "(" + m.getName() + "," + m.getType() + ")" ).collect( Collectors.toList() ) ) );
            }
        } );
    }

}
