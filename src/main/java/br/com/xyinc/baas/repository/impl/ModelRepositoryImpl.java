package br.com.xyinc.baas.repository.impl;


import br.com.xyinc.baas.repository.ModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class ModelRepositoryImpl implements ModelRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void save( String name, Object model ) {
        mongoTemplate.save( model, name );
    }

    @Override
    public void delete( String name, Object model ) {
        mongoTemplate.remove( model, name );
    }

    @Override
    public <T> List<T> findAll( String name, Class<T> clazz ) {
        return mongoTemplate.findAll( clazz, name );
    }

}
