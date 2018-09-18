package com.haz4j.swagger;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.googlecode.jsonrpc4j.JsonRpcParam;
import com.googlecode.jsonrpc4j.JsonRpcService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.lang.reflect.*;
import java.util.Map;
import java.util.Optional;

public class ReflectionUtils {

    public static String getJsonRpcParam(Parameter parameter) {
        JsonRpcParam[] jsonRpcParams = parameter.getAnnotationsByType(JsonRpcParam.class);
        if (jsonRpcParams != null
                && jsonRpcParams.length > 0
                && jsonRpcParams[0] != null
                && !StringUtils.isEmpty(jsonRpcParams[0].value())
                ) {
            return jsonRpcParams[0].value();
        } else {
            return parameter.getName();
        }
    }

    public static String getJsonProperty(Field field) {
        JsonProperty[] jsonProperties = field.getAnnotationsByType(JsonProperty.class);
        if (jsonProperties != null
                && jsonProperties.length > 0
                && jsonProperties[0] != null
                && !StringUtils.isEmpty(jsonProperties[0].value())) {
            return jsonProperties[0].value();
        }
        return field.getName();
    }

    public static Type getRealType(Map<TypeVariable<?>, Type> typeArguments, Field field) {
        Type realType = null;

        if (field.toGenericString().contains(" ") && !MapUtils.isEmpty(typeArguments)){
            String realTypeName = field.toGenericString().split(" ")[1];
            realType = getFromMap(typeArguments, realTypeName);
        }
        return realType;
    }

    private static Type getFromMap(Map<TypeVariable<?>, Type> typeArguments, String realTypeName) {
        Type realType = null;
        for (TypeVariable<?> key : typeArguments.keySet()) {
            if (key.getName().equals(realTypeName)){
                realType = typeArguments.get(key);
            }
        }
        return realType;
    }


    public static String getDescription(Method method) {
        String description = "";

        ApiOperation apiOperation = method.getAnnotation(ApiOperation.class);
        if (apiOperation != null) {
            description = apiOperation.value();
        }
        return description;
    }

    public static String getPath(Class api) {
        JsonRpcService jsonRpcService = (JsonRpcService) api.getAnnotation(JsonRpcService.class);
        return Optional.ofNullable(jsonRpcService)
                .map(JsonRpcService::value)
                .map(path -> path.replace("//", "/"))
                .orElseThrow(() -> new RuntimeException("Class " + api + " is annotated with @Api " +
                        "and should be annotated @JsonRpcService(value) with non-empty value"));
    }

    public static Pair<String, String> getTag(Class api) {
        Api apiAnnotation = (Api) api.getAnnotation(Api.class);
        if (apiAnnotation != null && apiAnnotation.tags().length > 0) {
            String tagName = apiAnnotation.tags()[0];
            String tagValue = apiAnnotation.value();
            return Pair.of(tagName, tagValue);
        }
        return Pair.of("default", "");
    }
}
