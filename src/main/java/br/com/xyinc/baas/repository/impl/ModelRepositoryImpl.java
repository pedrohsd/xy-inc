package br.com.xyinc.baas.repository.impl;


import br.com.xyinc.baas.repository.ModelRepository;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;


@Repository
public class ModelRepositoryImpl implements ModelRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    private static final int ID_LENGTH = 24;


    @Override
    public void save( String name, Object model ) {
        if(!((LinkedHashMap) model).containsKey( "_id" )) {
            ( (LinkedHashMap) model ).put( "_id", UUID.randomUUID().toString() );
        }
        mongoTemplate.save( model, name );
    }

    @Override
    public void delete( String name, String id, Object model ) {
        mongoTemplate.remove( model, name );
    }

    @Override
    public <T> List<T> findAll( String name, Class<T> clazz ) {
        return mongoTemplate.findAll( clazz, name );
    }


    @Override
    public <T> T findById( String name, String id, Class<T> clazz ) {
        return mongoTemplate.findById( id, clazz, name );
    }

}
