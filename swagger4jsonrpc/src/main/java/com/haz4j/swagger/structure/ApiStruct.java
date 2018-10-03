package com.haz4j.swagger.structure;

import lombok.Data;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Data
public class ApiStruct {

    private List<ClassStruct> structs = new ArrayList<>();

    public ApiStruct(Collection<ClassStruct> structs) {
        this.structs = new ArrayList<>(structs);
    }

    public ApiStruct() {
    }

    public ApiStruct filter(String methodName) {
        Predicate<MethodStruct> methodStructPredicate = m -> m.getName().equals(methodName);
        List<ClassStruct> filtered = structs.stream()
                .filter(cs -> cs.getMethods().stream().anyMatch(
                        methodStructPredicate
                ))
                .map(
                        cs -> {
                            List<MethodStruct> filteredMethods = cs.getMethods()
                                    .stream()
                                    .filter(methodStructPredicate)
                                    .collect(Collectors.toList());

                            ClassStruct newClassStruct = new ClassStruct(cs);
                            newClassStruct.setMethods(filteredMethods);
                            return newClassStruct;
                        }
                )
                .collect(Collectors.toList());
        return new ApiStruct(filtered);
    }
}