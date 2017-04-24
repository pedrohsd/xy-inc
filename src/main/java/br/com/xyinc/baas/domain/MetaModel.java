package br.com.xyinc.baas.domain;


import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.Id;

import java.util.List;

import static br.com.xyinc.baas.util.PreCondition.*;


public class MetaModel {

    public static final int MAX_LENGTH_NAME = 120;

    @Id
    @JsonInclude
    private String id;

    private String name;

    private List<MetaAttribute> metaAttributes;


    public MetaModel( String id, String name, List<MetaAttribute> metaAttributes ) {
        validate( name, metaAttributes );
        this.id = id;
        this.name = name;
        this.metaAttributes = metaAttributes;
    }


    public String getId() {

        return id;
    }


    public String getName() {

        return name;
    }


    public List<MetaAttribute> getMetaAttributes() {

        return metaAttributes;
    }

    private void validate(String name, List<MetaAttribute>  description) {
        notNull(name, "Name cannot be null");
        notEmpty(name, "Name cannot be empty");
        isTrue(name.length() <= MAX_LENGTH_NAME,
            "Name cannot be longer than %d characters",
            MAX_LENGTH_NAME
        );
    }


    @Override
    public String toString() {
        return "MetaModel{" + "id='" + id + '\'' + ", name='" + name + '\'' + ", metaAttributes=" + metaAttributes + '}';
    }
}
