package br.com.xyinc.baas.repository;


import java.util.List;


public interface ModelRepository {

    void save( String name, Object model );

    void delete( String name, Object model );

    <T> List<T> findAll( String name, Class<T> clazz );

    <T> T findById( String name, String id, Class<T> clazz );

}
