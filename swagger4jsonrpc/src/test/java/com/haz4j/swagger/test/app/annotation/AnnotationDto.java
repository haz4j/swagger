package com.haz4j.swagger.test.app.annotation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AnnotationDto {

    private String propertyName1;

    @JsonProperty
    private String propertyName2;

    @JsonProperty(value = "property_from_annotation")
    private String propertyName3;

    @JsonIgnore
    private String ignoredProperty;
}
