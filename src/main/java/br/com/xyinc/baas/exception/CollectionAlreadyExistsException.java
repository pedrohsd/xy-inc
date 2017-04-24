package br.com.xyinc.baas.exception;


public class CollectionAlreadyExistsException extends RuntimeException {

    public CollectionAlreadyExistsException(String name) {
        super(String.format( "Collection with name %s already exists" ));
    }

}
