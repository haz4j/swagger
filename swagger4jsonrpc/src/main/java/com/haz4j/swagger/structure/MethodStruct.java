package com.haz4j.swagger.structure;

import com.haz4j.swagger.TypeWrapper;
import lombok.Data;

import java.util.*;

@Data
public class MethodStruct {
    private String description;
    private String name;
    private SortedSet<ParameterStruct> parameters = new TreeSet<>(Comparator.comparing(ParameterStruct::getPropertyName));
    private SortedSet<TypeWrapper> signature = new TreeSet<>(Comparator.comparing(TypeWrapper::getName));

}
