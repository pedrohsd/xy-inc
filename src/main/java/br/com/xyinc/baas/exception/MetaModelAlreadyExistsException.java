package br.com.xyinc.baas.exception;


public class MetaModelAlreadyExistsException extends RuntimeException {

    public MetaModelAlreadyExistsException(String name) {
        super(String.format("Model with name %s already exists", name));
    }

}
