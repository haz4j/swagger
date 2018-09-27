package com.haz4j.swagger.structure;

import lombok.Data;

@Data
public class ParameterStruct {

    private Class<?> type;

    private ParameterizedTypeStruct parameterizedType;

    private String propertyName;

}
