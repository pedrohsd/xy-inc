package br.com.xyinc.baas.repository;


import br.com.xyinc.baas.domain.MetaModel;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;


public interface MetaModelRepository extends Repository<MetaModel, String> {

    void delete(MetaModel deleted);

    List<MetaModel> findAll();

    Optional<MetaModel> findOne(String id);

    Optional<MetaModel> findByName(String name);

    MetaModel save(MetaModel saved);

}
