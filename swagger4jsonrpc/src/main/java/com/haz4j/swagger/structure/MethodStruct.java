package com.haz4j.swagger.structure;

import com.haz4j.swagger.TypeWrapper;
import lombok.Data;

import java.lang.reflect.Parameter;
import java.util.List;

@Data
public class MethodStruct {
    private String description;
    private String name;
    private Parameter[] parameters; //TODO: выпилить
    private List<TypeWrapper> signature;
}
