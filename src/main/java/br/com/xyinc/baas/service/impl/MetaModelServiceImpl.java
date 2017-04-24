package br.com.xyinc.baas.service.impl;


import br.com.xyinc.baas.domain.MetaModel;
import br.com.xyinc.baas.exception.MetaModelAlreadyExistsException;
import br.com.xyinc.baas.exception.MetaModelNotFoundException;
import br.com.xyinc.baas.repository.MetaModelRepository;
import br.com.xyinc.baas.repository.RepositoryHelper;
import br.com.xyinc.baas.service.MetaModelService;
import br.com.xyinc.baas.util.PojoGenerator;
import javassist.CannotCompileException;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
public class MetaModelServiceImpl implements MetaModelService {

    private static final Logger log = LoggerFactory.getLogger(MetaModelServiceImpl.class);

    @Autowired
    private MetaModelRepository metaModelRepository;

    @Autowired
    private RepositoryHelper repositoryHelper;

    private Map<String, Class<?>> entities = new HashMap<>( );


    @Override
    public MetaModel create( MetaModel metaModel ) {

        Optional<MetaModel> existent = metaModelRepository.findByName( metaModel.getName() );
        if ( existent.isPresent() ) {
            throw new MetaModelAlreadyExistsException( metaModel.getName() );
        }

        mapEntity(metaModel);
        repositoryHelper.createCollection( metaModel.getName() );
        return metaModelRepository.save( metaModel );
    }

    @Override
    public MetaModel delete( String id ) {
        MetaModel metaModel = findById( id );
        metaModelRepository.delete( metaModel );
        repositoryHelper.dropCollection( metaModel.getName() );
        return metaModel;
    }


    @Override
    public List<MetaModel> findAll() {
        return metaModelRepository.findAll();
    }


    @Override
    public MetaModel findById( String id ) {
        Optional<MetaModel> result = metaModelRepository.findOne(id);
        return result.orElseThrow(() -> new MetaModelNotFoundException(String.format("No model entry found with id: <%s>", id)));
    }

    @Override
    public MetaModel findByName( String name ) {
        Optional<MetaModel> result = metaModelRepository.findByName( name );
        return result.orElseThrow(() -> new MetaModelNotFoundException(String.format("No model entry found with name: <%s>", name)));
    }


    @Override
    public Class getEntityClass( String modelName ) {
        Class entity = entities.get( modelName );
        if(entity == null) {
            MetaModel metaModel = findByName( modelName );
            entity = mapEntity( metaModel );
        }
        return entity;
    }

    private Class mapEntity( MetaModel metaModel ) {

        Map<String, Class<?>> fields = new HashMap<>();
        metaModel.getMetaAttributes().forEach( a -> fields.put( a.getName(), a.getType().getClazz() ) );
        try {
            Class clazz = PojoGenerator.generate( MetaModel.class.getPackage().getName() + "." + metaModel.getName(), fields );
            entities.put( metaModel.getName(), clazz );
            return clazz;
        } catch ( NotFoundException | CannotCompileException e ) {
            throw new RuntimeException( "Could not create model", e );
        }
    }

}
