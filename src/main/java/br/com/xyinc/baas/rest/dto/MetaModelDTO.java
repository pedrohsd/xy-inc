package br.com.xyinc.baas.rest.dto;


import br.com.xyinc.baas.domain.MetaAttribute;
import br.com.xyinc.baas.domain.MetaModel;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;


public class MetaModelDTO {

    private String id;

    @NotEmpty
    @Size(max = MetaModel.MAX_LENGTH_NAME)
    private String name;

    @NotNull
    private List<MetaAttributeDTO> metaAttributes;

    public MetaModelDTO() {}

    public MetaModelDTO( MetaModel metaModel ) {
        this.id = metaModel.getId();
        this.name = metaModel.getName();
        this.metaAttributes = metaModel.getMetaAttributes().stream()
            .map( m -> new MetaAttributeDTO( m ) )
            .collect( Collectors.toList());
    }

    public MetaModel unwrap() {
        List<MetaAttribute> metaAttributes = this.metaAttributes.stream()
            .map( m -> new MetaAttribute( m.getName(), m.getType() ) )
            .collect( Collectors.toList() );
        return new MetaModel( this.id, this.name, metaAttributes );
    }


    public String getId() {

        return id;
    }


    public void setId( String id ) {

        this.id = id;
    }


    public String getName() {

        return name;
    }


    public void setName( String name ) {

        this.name = name;
    }


    public List<MetaAttributeDTO> getMetaAttributes() {

        return metaAttributes;
    }


    public void setMetaAttributes( List<MetaAttributeDTO> metaAttributes ) {

        this.metaAttributes = metaAttributes;
    }


    @Override public String toString() {

        return "MetaModelDTO{" + "name='" + name + '\'' + ", metaAttributes=" + metaAttributes + '}';
    }
}
