package com.haz4j.swagger.structure;

import lombok.Data;

import java.util.*;
import java.util.function.Predicate;
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

    public ApiStruct filter(String methodName) {
        Predicate<MethodStruct> methodStructPredicate = m -> m.getName().equals(methodName);
        List<ClassStruct> filtered = structs.stream()
                .filter(cs -> cs.getMethods().stream().anyMatch(
                        methodStructPredicate
                ))
                .map(
                        cs -> {
                            SortedSet<MethodStruct> filteredMethods = cs.getMethods()
                                    .stream()
                                    .filter(methodStructPredicate)
                                    .collect(
                                        Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(MethodStruct::getName)))
                            );
                            cs.setMethods(filteredMethods);
                            return cs;
                        }
                )
                .collect(Collectors.toList());
        return new ApiStruct(filtered);
    }
}
