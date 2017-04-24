package br.com.xyinc.baas.service;


import br.com.xyinc.baas.domain.MetaModel;

import java.util.List;


public interface MetaModelService {

    MetaModel create(MetaModel metaModel);

    MetaModel delete(String id);

    List<MetaModel> findAll();

    MetaModel findById(String id);

    MetaModel findByName( String name );

    Class getEntity(String modelName);

}
