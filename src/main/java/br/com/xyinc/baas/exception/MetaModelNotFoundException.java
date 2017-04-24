package br.com.xyinc.baas.exception;


public class MetaModelNotFoundException extends RuntimeException {

    public MetaModelNotFoundException(String msg) {
        super(msg);
    }
}