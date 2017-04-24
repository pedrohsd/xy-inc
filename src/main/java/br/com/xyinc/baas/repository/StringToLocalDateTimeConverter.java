package br.com.xyinc.baas.repository;


import br.com.xyinc.baas.BaasApplication;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StringToLocalDateTimeConverter implements Converter<String, LocalDateTime> {

    @Override
    public LocalDateTime convert( String s ) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern( BaasApplication.DATE_FORMAT );
        return LocalDateTime.parse( s, formatter );
    }
}
