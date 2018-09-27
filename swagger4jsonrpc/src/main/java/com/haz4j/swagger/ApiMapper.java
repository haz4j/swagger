package com.haz4j.swagger;

import com.haz4j.swagger.structure.ClassStruct;
import com.haz4j.swagger.structure.MethodStruct;
import com.haz4j.swagger.structure.ParameterStruct;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
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
        MethodStruct methodStruct = new MethodStruct();
        methodStruct.setDescription(ReflectionUtils.getDescription(method));
        methodStruct.setName(method.getName());
        methodStruct.setParameters(method.getParameters());
        methodStruct.setSignature(ReflectionUtils.getSignature(method));
        return methodStruct;
    }

    public static ClassStruct toStruct(Class api){
        ClassStruct classStruct = new ClassStruct();
        classStruct.setTag(ReflectionUtils.getTag(api));
        classStruct.setPath(ReflectionUtils.getPath(api));
        return classStruct;
    }
}
