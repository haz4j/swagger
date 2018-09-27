package com.haz4j.swagger.structure;

import com.haz4j.swagger.TypeWrapper;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MethodStruct {
    private String description;
    private String name;
    private List<ParameterStruct> parameters = new ArrayList<>();
    private List<TypeWrapper> signature = new ArrayList<>();
}
