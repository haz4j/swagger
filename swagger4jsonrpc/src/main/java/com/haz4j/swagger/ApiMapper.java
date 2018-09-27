package com.haz4j.swagger;

import com.haz4j.swagger.structure.ClassStruct;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApiMapper {


    public static Map<ClassStruct, Map<Method, List<Parameter>>> toStruct(Map<Class, Map<Method, List<Parameter>>> apiMap) {
        Map<ClassStruct, Map<Method, List<Parameter>>> structMap = new HashMap<>();

        for (Map.Entry<Class, Map<Method, List<Parameter>>> classMapEntry : apiMap.entrySet()) {
            structMap.put(toStruct(classMapEntry.getKey()), classMapEntry.getValue());
        }
        return structMap;
    }

    public static ClassStruct toStruct(Class api){
        ClassStruct classStruct = new ClassStruct();
        classStruct.setTag(ReflectionUtils.getTag(api));
        classStruct.setPath(ReflectionUtils.getPath(api));
        return classStruct;
    }
}
