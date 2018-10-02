package com.haz4j.swagger;

import com.haz4j.swagger.structure.*;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
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
        struct.getParameters().addAll(toStruct(method.getParameters()));
        struct.getSignature().addAll(ReflectionUtils.getTypeWrappers(method));
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
            struct.setParameterizedType(toStruct((ParameterizedType) parameter.getParameterizedType()));
        }
        struct.setPropertyName(ReflectionUtils.getJsonRpcParam(parameter));
        return struct;
    }

    public static ParameterizedTypeStruct toStruct(ParameterizedType parameterizedType) {
        ParameterizedTypeStruct struct = new ParameterizedTypeStruct();
        struct.setRawType(parameterizedType.getRawType());
        struct.setActualTypeArguments(parameterizedType.getActualTypeArguments());

        return struct;
    }

    public static ClassStruct toStruct(Class api){
        ClassStruct struct = new ClassStruct();
        struct.setTag(ReflectionUtils.getTag(api));
        struct.setPath(ReflectionUtils.getPath(api));
        return struct;
    }
}
