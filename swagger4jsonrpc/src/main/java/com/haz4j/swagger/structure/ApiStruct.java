package com.haz4j.swagger.structure;

import lombok.Getter;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Getter
public class ApiStruct {

    private final List<ClassStruct> structs;

    public ApiStruct(List<ClassStruct> structs) {
        this.structs = Collections.unmodifiableList(structs);
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

                            return new ClassStruct(cs.getTag(), cs.getPath(), filteredMethods);
                        }
                )
                .collect(Collectors.toList());
        return new ApiStruct(filtered);
    }
}