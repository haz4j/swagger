package com.haz4j.swagger.structure;

import com.haz4j.swagger.TypeWrapper;
import lombok.Data;

import java.lang.reflect.Parameter;
import java.util.*;

@Data
public class MethodStruct {
    private String description;
    private String name;
    private List<Parameter> parameters = new ArrayList<>();
    private List<TypeWrapper> signature = new ArrayList<>();

}
