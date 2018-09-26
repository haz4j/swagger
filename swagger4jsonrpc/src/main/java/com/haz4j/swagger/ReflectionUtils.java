package com.haz4j.swagger;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.googlecode.jsonrpc4j.JsonRpcParam;
import com.googlecode.jsonrpc4j.JsonRpcService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
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

    @SneakyThrows
    public static Class getRealType(Field field, Map<String, String> actualTypeArguments, Map<TypeVariable<?>, Type> typeArguments) {
        log.debug("getRealType: typeArguments - " + typeArguments + ", field - " + field);

        if (field.toGenericString().contains(" ") && !MapUtils.isEmpty(typeArguments)) {
            String realTypeName = field.toGenericString().split(" ")[1];
            return (Class) getFromMap(typeArguments, realTypeName);
        }

        String signature = ReflectionUtils.getSignature(field);
        if (signature == null) {
            return null;
        }

        String className = actualTypeArguments.get(signature);
        return Class.forName(className);
    }

    @SneakyThrows
    public static String getSignature(Field field) {
        Field f = Field.class.getDeclaredField("signature");
        f.setAccessible(true);
        String signature = (String) f.get(field); //for "R" it will be "TR;"
        if (signature != null) {
            return signature.substring(1, signature.length()-1);
        } else{
            return null;
        }

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

    @SneakyThrows
    public static List<String> getSignature(Method method) {
        Field f = Method.class.getDeclaredField("signature");
        f.setAccessible(true);
        String signature = (String) f.get(method);
        if (signature == null) {
            return new ArrayList<>();
        }
        return Arrays.stream(signature.substring(signature.indexOf("<")+1, signature.lastIndexOf(">")-1)
                .split(";"))
                .map(s -> s.replaceAll("/", "."))
                .map(s -> s.substring(1, s.length()))
                .collect(Collectors.toList());
    }

    public static String getPath(Class api) {
        log.debug("getPath: api" + api);

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
