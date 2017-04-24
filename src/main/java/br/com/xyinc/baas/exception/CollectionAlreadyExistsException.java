package br.com.xyinc.baas.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus( value = HttpStatus.CONFLICT )
public class CollectionAlreadyExistsException extends RuntimeException {

    public CollectionAlreadyExistsException(String name) {
        super(String.format( "Collection with name %s already exists" ));
    }

}
