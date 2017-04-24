package br.com.xyinc.baas.repository;


import br.com.xyinc.baas.exception.CollectionAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;


@Component
public class RepositoryHelper {

    @Autowired
    private MongoTemplate mongoTemplate;


    public void createCollection( String name ) {
        if(mongoTemplate.collectionExists( name )) {
            throw new CollectionAlreadyExistsException(name);
        }

        mongoTemplate.createCollection( name );
    }

    public void dropCollection( String name ) {
        mongoTemplate.dropCollection( name );
    }

}
