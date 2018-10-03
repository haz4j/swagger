package com.haz4j.swagger.structure;

import com.haz4j.swagger.TypeWrapper;
import lombok.Getter;

import java.lang.reflect.Parameter;
import java.util.Collections;
import java.util.List;

@Getter
public class MethodStruct {
    private final String description;
    private final String name;
    private final List<Parameter> parameters;
    private final List<TypeWrapper> signature;

    public MethodStruct(String description, String name, List<Parameter> parameters, List<TypeWrapper> signature) {
        this.description = description;
        this.name = name;
        this.parameters = Collections.unmodifiableList(parameters);
        this.signature = Collections.unmodifiableList(signature);
    }
}
