package br.com.xyinc.baas.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus( value = HttpStatus.CONFLICT )
public class MetaModelAlreadyExistsException extends RuntimeException {

    public MetaModelAlreadyExistsException(String name) {
        super(String.format("Model with name %s already exists", name));
    }

}
