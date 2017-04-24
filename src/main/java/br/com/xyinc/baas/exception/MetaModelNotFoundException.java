package br.com.xyinc.baas.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus( value = HttpStatus.NOT_FOUND )
public class MetaModelNotFoundException extends RuntimeException {

    public MetaModelNotFoundException(String msg) {
        super(msg);
    }
}