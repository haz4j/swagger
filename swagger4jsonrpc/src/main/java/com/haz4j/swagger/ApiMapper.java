package com.haz4j.swagger;

import com.haz4j.swagger.structure.ApiStruct;
import com.haz4j.swagger.structure.ClassStruct;
import com.haz4j.swagger.structure.MethodStruct;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ApiMapper {

    public static ApiStruct toStruct(List<Class> classes) {
        ApiStruct api = new ApiStruct();
        for (Class aClass : classes) {
            ClassStruct classStruct = toStruct(aClass);
            List<Method> methods = Arrays.asList(aClass.getMethods());
            classStruct.getMethods().addAll(toStructMethod(methods));
            api.getStructs().add(classStruct);
        }
        return api;
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
        MethodStruct struct = new MethodStruct();
        struct.setDescription(ReflectionUtils.getDescription(method));
        struct.setName(method.getName());
        struct.setParameters(Arrays.asList(method.getParameters()));
        struct.getSignature().addAll(ReflectionUtils.getTypeWrappers(method));
        return struct;
    }

    public static ClassStruct toStruct(Class api){
        ClassStruct struct = new ClassStruct();
        struct.setTag(ReflectionUtils.getTag(api));
        struct.setPath(ReflectionUtils.getPath(api));
        return struct;
    }
}
