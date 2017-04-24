package br.com.xyinc.baas.domain;


import static br.com.xyinc.baas.util.PreCondition.*;


public class MetaAttribute {

    public static final int MAX_LENGTH_NAME = 120;

    private String name;

    private AttributeType type;


    public MetaAttribute(String name, AttributeType type) {
        validate(name, type);
        this.name = name;
        this.type = type;
    }


    public String getName() {

        return name;
    }

    public AttributeType getType() {

        return type;
    }

    private void validate(String name, AttributeType type) {
        notNull(name, "Attribute name cannot be null");
        notEmpty(name, "Attribute name cannot be empty");
        isTrue(name.length() <= MAX_LENGTH_NAME,
            "Attribute name cannot be longer than %d characters",
            MAX_LENGTH_NAME
        );
        notNull( type, "Attribute type cannot be null" );
    }


    @Override public String toString() {

        return "MetaAttribute{" + "name='" + name + '\'' + ", type=" + type + '}';
    }
}
