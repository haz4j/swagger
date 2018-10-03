package com.haz4j.swagger;

import com.haz4j.swagger.structure.ApiStruct;
import com.haz4j.swagger.structure.ClassStruct;
import com.haz4j.swagger.structure.MethodStruct;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

//TODO: to streams
public class ApiMapper {

    public static ApiStruct toStruct(List<Class> classes) {
        List<ClassStruct> structs = classes.stream()
                .map(clazz -> toStruct(clazz))
                .collect(Collectors.toList());
        return new ApiStruct(structs);
    }

    private static List<MethodStruct> toStructMethod(List<Method> methods) {
        List<MethodStruct> structList = new ArrayList<>();
        for (Method method : methods) {
            MethodStruct methodStruct = toStruct(method);
            structList.add(methodStruct);
        }
        return structList;
    }

    private static MethodStruct toStruct(Method method) {
        return new MethodStruct(
                ReflectionUtils.getDescription(method),
                method.getName(),
                Arrays.asList(method.getParameters()),
                ReflectionUtils.getTypeWrappers(method)
        );
    }

    public static ClassStruct toStruct(Class api){
        return new ClassStruct(
                ReflectionUtils.getTag(api),
                ReflectionUtils.getPath(api),
                toStructMethod(Arrays.asList(api.getMethods())));
    }
}
