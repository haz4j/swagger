package com.haz4j.swagger.structure;

import lombok.Data;

import java.util.*;
import java.util.stream.Collectors;

@Data
public class ApiStruct {

    private Comparator<ClassStruct> comparator = Comparator.comparing(ClassStruct::getPath);
    private SortedSet<ClassStruct> structs = new TreeSet<>(comparator);

    public ApiStruct(Collection<ClassStruct> structs) {
        TreeSet<ClassStruct> newStr = new TreeSet<>(comparator);
        newStr.addAll(structs);
        this.structs = newStr;
    }

    public ApiStruct() {

    }

    public ApiStruct filter(String methodName){
        List<ClassStruct> filtered = structs.stream()
                .filter(cs -> cs.getMethods().stream().anyMatch(m -> m.getName().contains(methodName)))
                .collect(Collectors.toList());
        return new ApiStruct(filtered);
    }

}
