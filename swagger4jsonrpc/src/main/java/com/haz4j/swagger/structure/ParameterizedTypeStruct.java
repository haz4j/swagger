package com.haz4j.swagger.structure;

import lombok.Data;

import java.lang.reflect.Type;

@Data
public class ParameterizedTypeStruct {

    private Type rawType;

    private Type[] actualTypeArguments; //TODO: to list

}
