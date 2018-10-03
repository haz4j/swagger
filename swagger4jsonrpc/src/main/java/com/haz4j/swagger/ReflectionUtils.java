package com.haz4j.swagger;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.googlecode.jsonrpc4j.JsonRpcParam;
import com.googlecode.jsonrpc4j.JsonRpcService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.TypeUtils;
import org.apache.commons.lang3.tuple.Pair;
import sun.reflect.generics.parser.SignatureParser;
import sun.reflect.generics.repository.ClassRepository;
import sun.reflect.generics.tree.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.TypeVariable;
import java.util.*;

@Slf4j
public class ReflectionUtils {

    private static Field methodSignature;
    private static Field classTypeSignaturePath;
    private static Field arrayTypeSignatureComponentType;
    private static Field fieldSignature;

    static {
        init();
    }

    @SneakyThrows
    public static void init(){
        methodSignature = Method.class.getDeclaredField("signature");
        classTypeSignaturePath = ClassTypeSignature.class.getDeclaredField("path");
        arrayTypeSignatureComponentType = ArrayTypeSignature.class.getDeclaredField("componentType");
        fieldSignature = Field.class.getDeclaredField("signature");
        methodSignature.setAccessible(true);
        classTypeSignaturePath.setAccessible(true);
        arrayTypeSignatureComponentType.setAccessible(true);
        fieldSignature.setAccessible(true); //TODO: по-хорошему нужно за собой закрывать доступ
    }

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
    public static Class getRealType(Field field, Map<String, String> genericTypeArgs) {
        // signature = TR;
        String signature = ReflectionUtils.getSignature(field);
        if (signature == null) {
            return null;
        }

        if (genericTypeArgs != null && genericTypeArgs.get(signature) != null) {
            String className = genericTypeArgs.get(signature);
            Class<?> realClass = Class.forName(className);
            return TypeUtils.getRawType(realClass, null);
        }
        return null;
    }

    @SneakyThrows
    public static String getSignature(Field field) {
        Field f = Field.class.getDeclaredField("signature");
        f.setAccessible(true);
        String signature = (String) f.get(field); //for "R" it will be "TR;"
        if (signature != null) {
            return signature.substring(1, signature.length() - 1);
        } else {
            return null;
        }
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
    public static List<TypeWrapper> getTypeWrappers(Method method) {

        //TODO: перенести все это выше
        String signature = (String) methodSignature.get(method);
        if (signature == null) {
            return new ArrayList<>();
        }
        MethodTypeSignature methodTypeSignature = SignatureParser.make().parseMethodSig(signature);

        TypeSignature[] parameterTypes = methodTypeSignature.getParameterTypes();
        List<TypeWrapper> allSignatures = new ArrayList<>();

        for (TypeSignature parameterType : parameterTypes) {
            allSignatures.add(toTypeWrapper(parameterType));
        }

        return allSignatures;
    }

    @SneakyThrows
    private static TypeWrapper toTypeWrapper(TypeSignature parameterType) {
        TypeWrapper typeWrapper = null;

        if (ArrayTypeSignature.class.isAssignableFrom(parameterType.getClass())) {
            ClassTypeSignature classTypeSignature = (ClassTypeSignature) arrayTypeSignatureComponentType.get(parameterType);

            //this is array, thus we call the same method with element as a parameter
            return toTypeWrapper(classTypeSignature);
        }

        ArrayList paths = (ArrayList) classTypeSignaturePath.get(parameterType);

        for (Object o : paths) {
            SimpleClassTypeSignature signature = (SimpleClassTypeSignature) o;
            TypeArgument[] typeArguments = signature.getTypeArguments();

            List<TypeWrapper> childs = new ArrayList<>();
            for (TypeArgument typeArgument : typeArguments) {
                TypeSignature typeSignature = (TypeSignature) typeArgument;
                TypeWrapper child = toTypeWrapper(typeSignature);
                childs.add(child);
            }
            typeWrapper = new TypeWrapper(signature.getName(), childs);
        }

        return typeWrapper;
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

    @SneakyThrows
    public static List<TypeVariable<?>> getTypeParams(Class<?> type) {
        Field f = Class.class.getDeclaredField("genericInfo");
        f.setAccessible(true);
        ClassRepository classRepository = (ClassRepository) f.get(type);
        if (classRepository == null) {
            return new ArrayList<>();
        }

        TypeVariable<?>[] typeParameters = classRepository.getTypeParameters();
        return Arrays.asList(typeParameters);
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
