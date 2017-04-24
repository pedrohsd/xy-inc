package br.com.xyinc.baas.rest.dto;


import br.com.xyinc.baas.domain.AttributeType;
import br.com.xyinc.baas.domain.MetaAttribute;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class MetaAttributeDTO {

    @NotEmpty
    @Size(max = MetaAttribute.MAX_LENGTH_NAME)
    private String name;

    @NotNull
    private Object value;

    @NotNull
    private AttributeType type;

    public MetaAttributeDTO() {}

    public MetaAttributeDTO( MetaAttribute metaAttribute ) {
        this.name = metaAttribute.getName();
        this.type = metaAttribute.getType();
    }


    public String getName() {

        return name;
    }


    public void setName( String name ) {

        this.name = name;
    }


    public Object getValue() {

        return value;
    }


    public void setValue( Object value ) {

        this.value = value;
    }


    public AttributeType getType() {

        return type;
    }


    public void setType( AttributeType type ) {

        this.type = type;
    }


    @Override public String toString() {

        return "MetaAttributeDTO{" + "name='" + name + '\'' + ", value=" + value + ", type=" + type + '}';
    }
}
