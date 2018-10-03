package com.haz4j.swagger;

import lombok.Getter;

import java.util.Collections;
import java.util.List;

@Getter
public class TypeWrapper {

    private final String name;

    private final List<TypeWrapper> childs;

    public TypeWrapper(String name, List<TypeWrapper> childs) {
        this.name = name;
        this.childs = Collections.unmodifiableList(childs);
    }
}