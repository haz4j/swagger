package com.haz4j.swagger;

import com.haz4j.swagger.structure.ClassStruct;
import com.haz4j.swagger.structure.MethodStruct;
import com.haz4j.swagger.structure.ParameterStruct;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApiMapper {


    public static Map<ClassStruct, List<MethodStruct>> toStruct(Map<Class, Map<Method, List<Parameter>>> apiMap) {
        Map<ClassStruct, List<MethodStruct>> structMap = new HashMap<>();

        for (Map.Entry<Class, Map<Method, List<Parameter>>> entry : apiMap.entrySet()) {
            structMap.put(toStruct(entry.getKey()), toStructMethod(entry.getValue()));
        }
        return structMap;
    }

    private static List<MethodStruct> toStructMethod(Map<Method, List<Parameter>> methodMap) {
        List<MethodStruct> structList = new ArrayList<>();
        for (Map.Entry<Method, List<Parameter>> entry : methodMap.entrySet()) {
            structList.add(toStruct(entry.getKey()));
        }
        return structList;
    }

    private static MethodStruct toStruct(Method method) {
        MethodStruct struct = new MethodStruct();
        struct.setDescription(ReflectionUtils.getDescription(method));
        struct.setName(method.getName());
        struct.setParameters(toStruct(method.getParameters()));
        struct.setSignature(ReflectionUtils.getSignature(method));
        return struct;
    }

    private static List<ParameterStruct> toStruct(Parameter[] parameters) {
        ArrayList<ParameterStruct> parameterStructs = new ArrayList<>();
        for (Parameter parameter : parameters) {
            parameterStructs.add(toStruct(parameter));
        }
        return parameterStructs;
    }

    private static ParameterStruct toStruct(Parameter parameter) {
        ParameterStruct struct = new ParameterStruct();
        struct.setType(parameter.getType());

        if (parameter.getParameterizedType().getClass().isAssignableFrom(ParameterizedType.class) ||
                parameter.getParameterizedType().getClass().isAssignableFrom(ParameterizedTypeImpl.class)) {
            struct.setParameterizedType((ParameterizedType) parameter.getParameterizedType());
        }
        struct.setPropertyName(ReflectionUtils.getJsonRpcParam(parameter));
        return struct;
    }

    public static ClassStruct toStruct(Class api){
        ClassStruct struct = new ClassStruct();
        struct.setTag(ReflectionUtils.getTag(api));
        struct.setPath(ReflectionUtils.getPath(api));
        return struct;
    }
}
