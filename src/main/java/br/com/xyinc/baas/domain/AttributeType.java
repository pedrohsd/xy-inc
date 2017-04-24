package br.com.xyinc.baas.domain;


import java.time.LocalDateTime;


public enum AttributeType {

    INTEGER(Long.class), DECIMAL(Double.class), STRING(String.class), DATE(LocalDateTime.class);

    private Class clazz;


    AttributeType(Class clazz) {
        this.clazz = clazz;
    }


    public Class getClazz() {
        return clazz;
    }
}
