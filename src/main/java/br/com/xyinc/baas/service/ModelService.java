package br.com.xyinc.baas.service;


import java.util.List;


public interface ModelService {

    void save( String name, Object model );

    <T> List<T> findAll( String name );

    <T> T findById( String name, String id );

}
