package com.haz4j.swagger.structure;

import lombok.Data;

import java.lang.reflect.ParameterizedType;

@Data
public class ParameterStruct {

    private Class<?> type;

    private ParameterizedType parameterizedType;

    private String propertyName;

}
