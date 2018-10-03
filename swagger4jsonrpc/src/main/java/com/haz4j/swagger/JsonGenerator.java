package com.haz4j.swagger;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.haz4j.swagger.structure.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.TypeUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.reflect.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


//TODO: избавиться от null
//сделать @NotNull

//TODO: проверить все ли параметры попали в логи

@Component
@Slf4j
public class JsonGenerator {

    private ObjectMapper mapper = new ObjectMapper();

    private Map<String, JsonNode> definitions;

    public String createJson(String hostName, ApiStruct apis) {

        definitions = new HashMap<>();

        ArrayNode tagsArray = mapper.createArrayNode();
        ObjectNode document = document(hostName);
        ObjectNode paths = mapper.createObjectNode();
        ObjectNode definitionsNode = mapper.createObjectNode();

        apis.getStructs().forEach((api) -> {

            log.debug("api = " + api);

            ObjectNode tagNode = mapper.createObjectNode();
            Pair<String, String> tag = api.getTag();
            tagNode.put("name", tag.getLeft());
            tagNode.put("description", tag.getRight());
            tagsArray.add(tagNode);

            String path = api.getPath();

            int i = 0;

            for (MethodStruct method : api.getMethods()) {
                log.debug("method = " + method);

                ObjectNode apiNode = mapper.createObjectNode();

                apiNode.set("post", createMethod(tag.getLeft(), method, path + "#" + i));

                //each url can have more than one post method, thus each path has name path#i
                paths.set(path + "#" + i, apiNode);

                i++;
            }
        });

        document.putPOJO("tags", tagsArray);
        document.set("paths", paths);
        definitions.forEach((definitionName, node) -> definitionsNode.set(definitionName, node));
        document.set("definitions", definitionsNode);

        try {
//            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(document);
            return mapper.writeValueAsString(document);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private ObjectNode createMethod(String tagName, MethodStruct method, String operationId) {
        log.debug("createMethod - " + method);
        ObjectNode objectNode = mapper.createObjectNode();

        String description = method.getDescription();

        ArrayNode tagsNode = mapper.createArrayNode();
        tagsNode.add(tagName);

        objectNode.set("tags", tagsNode);
        objectNode.put("summary", method.getName());
        objectNode.put("description", description);
        objectNode.put("operationId", operationId);

        ArrayNode applicationTypes = mapper.createArrayNode();
        applicationTypes.add("application/json");

        objectNode.set("consumes", applicationTypes);
        objectNode.set("produces", applicationTypes);

        ObjectNode parametersObj = mapper.createObjectNode();
        parametersObj.put("in", "body");
        parametersObj.put("name", "JsonRpc");
        parametersObj.put("description", "JsonRpc");
        parametersObj.put("required", true);

        ObjectNode schemaNode = createMethodScheme(method);
        parametersObj.set("schema", schemaNode);

        ArrayNode parametersArr = mapper.createArrayNode();
        parametersArr.add(parametersObj);

        objectNode.set("parameters", parametersArr);
        objectNode.set("responses", mapper.createObjectNode());

        return objectNode;
    }


    //generate method scheme with links to entities definitions
    private ObjectNode createMethodScheme(MethodStruct method) {
        log.debug("createMethodScheme - " + method);

        ObjectNode definitionNode = mapper.createObjectNode();
        definitionNode.put("type", "object");

        ObjectNode propertiesNode = mapper.createObjectNode();

        ObjectNode idNode = mapper.createObjectNode();
        idNode.put("type", "integer");
        idNode.put("format", "int64");
        propertiesNode.set("id", idNode);

        ObjectNode jsonRpcNode = mapper.createObjectNode();
        jsonRpcNode.put("type", "string");
        jsonRpcNode.put("description", "jsonrpc version");
        jsonRpcNode.put("default", "2.0");
        propertiesNode.set("jsonrpc", jsonRpcNode);

        ObjectNode methodNode = mapper.createObjectNode();
        methodNode.put("type", "string");
        methodNode.put("example", method.getName());
        propertiesNode.set("method", methodNode);

        if ((method.getParameters().size() > 0)) {
            ObjectNode schemaNode = createEntityRef(method);
            propertiesNode.set("params", schemaNode);
        }

        definitionNode.set("properties", propertiesNode);
        return definitionNode;
    }

    private ObjectNode createEntityRef(MethodStruct method) {
        log.debug("createEntityRef - " + method);

        if ((method.getParameters().size() == 0)) {
            return null;
        }

        ObjectNode definitionNode = mapper.createObjectNode();
        definitionNode.put("type", "object");
        ObjectNode propertiesNode = mapper.createObjectNode();

        List<TypeWrapper> typeWrappers = method.getSignature();

        for (int i = 0; i < method.getParameters().size(); i++) {
            Parameter parameter = method.getParameters().get(i);
            TypeWrapper typeWrapper = null;

            if (typeWrappers.size() > i) {
                typeWrapper = new ArrayList<>(typeWrappers).get(i);
            }

            JsonNode paramFromMethodParameter = createParamFromMethodParameter(parameter, typeWrapper);
            String propertyName = ReflectionUtils.getJsonRpcParam(parameter);
            propertiesNode.set(propertyName, paramFromMethodParameter);
        }

        definitionNode.set("properties", propertiesNode);
        return definitionNode;
    }

    private JsonNode createParamFromMethodParameter(Parameter parameter, TypeWrapper typeWrapper) {
        log.debug("createParamFromMethodParameter - " + parameter);

        Class<?> type = parameter.getType();

        if (Collection.class.isAssignableFrom(type)) {

            ParameterizedType parameterizedType = (ParameterizedType) parameter.getParameterizedType();  //этo

            Type typeOfCollectionElement = parameterizedType.getActualTypeArguments()[0];

            TypeWrapper childTypeWrapper = null;
            if (typeWrapper != null) {
                childTypeWrapper = typeWrapper.getTypeWrappers().get(0);
            }

            return createArrayNode(typeOfCollectionElement, null, childTypeWrapper);

        } else if (type.isArray()) {

            return createArrayNode(type.getComponentType(), null, typeWrapper);

        } else if (Map.class.isAssignableFrom(type)) {

            ParameterizedType parameterizedType = (ParameterizedType) parameter.getParameterizedType();

            Class keyClass = (Class) parameterizedType.getActualTypeArguments()[0];
            Type valueClass = parameterizedType.getActualTypeArguments()[1];
            String defaultValue = defaultValueOf(keyClass);

            TypeWrapper childTypeWrapper = null;
            if (typeWrapper != null && typeWrapper.getTypeWrappers().size() > 1) {
                childTypeWrapper = typeWrapper.getTypeWrappers().get(1);
            }

            return createNodeForMap(valueClass, defaultValue, childTypeWrapper);

        } else {

            if (parameter.getParameterizedType().getClass().isAssignableFrom(ParameterizedType.class) ||
                    parameter.getParameterizedType().getClass().isAssignableFrom(ParameterizedTypeImpl.class)) {
                ParameterizedType parameterizedType = (ParameterizedType) parameter.getParameterizedType();

                Type rawType = parameterizedType.getRawType();

                List<TypeVariable<?>> typeParams = ReflectionUtils.getTypeParams(type);
                Map<String, String> typesMap = toTypesMap(typeParams, typeWrapper);

                return createPropertyFor((Class) rawType, typesMap);
            } else {
                return createPropertyFor(type, null);
            }

            //TODO: а вот сюда тоже по уму должен typeWrapper передаваться
        }
    }

    private Map<String, String> toTypesMap(List<TypeVariable<?>> typeParams, TypeWrapper typeWrapper) {
        if (typeWrapper == null) {
            return new HashMap<>();
        }

        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < typeWrapper.getTypeWrappers().size(); i++) {
            map.put(typeParams.get(i).getName(), typeWrapper.getTypeWrappers().get(i).getName());
        }
        return map;
    }

    private void createEntityDefinition(Class<?> entityClass, Map<String, String> genericTypeArgs, Map<TypeVariable<?>, Type> typeArguments) {
        log.debug("createEntityDefinition: entityClass - " + entityClass + ", typeArguments - " + typeArguments);

        String refName = getRefName(entityClass, genericTypeArgs, typeArguments);

        if (definitions.get(refName) != null) {
            return;
        }

        //we put empty node to collection to prevent infinite recursion:
        //next time we try to create the same object the condition above will return true
        definitions.put(refName, mapper.createObjectNode());

        ObjectNode entityNode = mapper.createObjectNode();
        entityNode.put("type", "object");
        ObjectNode properties = mapper.createObjectNode();
        List<Field> fields = FieldUtils.getAllFieldsList(entityClass);

        for (Field field : fields) {
            log.debug("field " + field);

            if (field.getAnnotation(JsonIgnore.class) != null) {
                continue;
            }

            String fieldName = ReflectionUtils.getJsonProperty(field);

            TypeWrapper typeWrapper = null;  //TODO: странно

            Class<?> type = field.getType();

            ObjectNode node = null;

            if (Collection.class.isAssignableFrom(type)) {

                ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType(); //это

                Type typeOfCollectionElement = parameterizedType.getActualTypeArguments()[0];

                TypeWrapper childTypeWrapper = null;

                if (typeWrapper != null) {
                    childTypeWrapper = typeWrapper.getTypeWrappers().get(0);
                }

                node = createArrayNode(typeOfCollectionElement, null, childTypeWrapper);

            } else if (type.isArray()) {
                node = createArrayNode(type.getComponentType(), null, null);
            } else if (Map.class.isAssignableFrom(type)) {
                ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();

                Class keyClass = (Class) parameterizedType.getActualTypeArguments()[0];
                Type valueClass = parameterizedType.getActualTypeArguments()[1];
                String defaultValue = defaultValueOf(keyClass);

                TypeWrapper childTypeWrapper = null;
                if (typeWrapper != null && typeWrapper.getTypeWrappers().size() > 1) {
                    childTypeWrapper = typeWrapper.getTypeWrappers().get(1);
                }

                node = createNodeForMap(valueClass, defaultValue, childTypeWrapper);

            } else {

                Class realType = ReflectionUtils.getRealType(field, genericTypeArgs);
                if (realType != null) {
                    node = createPropertyFor(realType, null);
                } else {
                    node = createPropertyFor(type, null);
                }

            }

            properties.set(fieldName, node);
        }



        entityNode.set("properties", properties);
        definitions.put(refName, entityNode);
    }

    private ObjectNode createNodeForCollection(Class<?> type, Type[] typeArgumentsArray, TypeWrapper typeWrapper) {
        log.debug("createNodeForCollection: type - " + type + ", typeArgumentsArray - " + typeArgumentsArray);

        ObjectNode arrayNode = mapper.createObjectNode();
        arrayNode.put("type", "array");

        ObjectNode items = createNodeForElement(type, typeArgumentsArray, typeWrapper);
        arrayNode.set("items", items);
        return arrayNode;
    }

    private ObjectNode createNodeForMap(String defaultValue, Class<?> type, Type[] typeArgumentsArray, TypeWrapper typeWrapper) {
        log.debug("createNodeForMap: defaultValue - " + defaultValue + ", type - " + type);

        ObjectNode mapNode = mapper.createObjectNode();
        mapNode.put("type", "object");

        ObjectNode valueNode = createNodeForElement(type, typeArgumentsArray, typeWrapper);

        ObjectNode propertiesNode = mapper.createObjectNode();
        propertiesNode.set(defaultValue, valueNode);
        mapNode.set("properties", propertiesNode);
        return mapNode;
    }

    private ObjectNode createNodeForElement(Class<?> type, Type[] typeArgumentsArray, TypeWrapper typeWrapper) {
        ObjectNode valueNode;
        if (Collection.class.isAssignableFrom(type)) {
            TypeWrapper childTypeWrapper = null;
            if (typeWrapper != null) {
                childTypeWrapper = typeWrapper.getTypeWrappers().get(0);
            }
            valueNode = createArrayNode(typeArgumentsArray[0], typeArgumentsArray, childTypeWrapper);
        } else if (Map.class.isAssignableFrom(type)) {

            TypeWrapper childTypeWrapper = null;
            if (typeWrapper != null && typeWrapper.getTypeWrappers().size() > 1) {
                childTypeWrapper = typeWrapper.getTypeWrappers().get(1);
            }

            Type keyClass = typeArgumentsArray[0];
            Type valueClass = typeArgumentsArray[1];
            String nextDefaultValue = defaultValueOf((Class) keyClass);


            valueNode = createNodeForMap(valueClass, nextDefaultValue, childTypeWrapper);

        } else {
            List<TypeVariable<?>> typeParams = ReflectionUtils.getTypeParams(type);

            Map<String, String> typesMap = toTypesMap(typeParams, typeWrapper);

            valueNode = createPropertyFor(type, typesMap);
        }
        return valueNode;
    }

    private ObjectNode createNodeForMap(Type valueClass, String nextDefaultValue, TypeWrapper typeWrapper) {
        ObjectNode items;
        if (valueClass.getClass().isAssignableFrom(ParameterizedType.class) ||
                valueClass.getClass().isAssignableFrom(ParameterizedTypeImpl.class)) {
            Type rawType = ((ParameterizedType) valueClass).getRawType();
            Type[] actualTypeArguments = ((ParameterizedType) valueClass).getActualTypeArguments();

            items = createNodeForMap(nextDefaultValue, (Class<?>) rawType, actualTypeArguments, typeWrapper);
        } else {
            items = createNodeForMap(nextDefaultValue, (Class<?>) valueClass, null, typeWrapper);
        }
        return items;
    }

    //type - element of collection
    private ObjectNode createArrayNode(Type type, Type[] typeArgumentsArray, TypeWrapper typeWrapper) {
        log.debug("createArrayNode: type - " + type);

        ObjectNode arrayNode;
        if (Class.class.isAssignableFrom(type.getClass())) {
            //string
            arrayNode = createNodeForCollection((Class) type, typeArgumentsArray, typeWrapper);
        } else {
            //collection or map
            Class<?> rawType = TypeUtils.getRawType(type, null);
            //TODO: проверить тип теперь кастингом
            arrayNode = createNodeForCollection(rawType, ((ParameterizedTypeImpl) type).getActualTypeArguments(), typeWrapper);
        }
        return arrayNode;
    }

    private String defaultValueOf(Class clazz) {
        if (ClassUtils.isPrimitiveOrWrapper(clazz)) {
            return "1";
        }
        if (clazz.isAssignableFrom(String.class)) {
            return "3";
        }
        if (clazz.isAssignableFrom(LocalDate.class)) { //TODO: придумать как это сделать
            return "2017-03-28 14:50:09";
        }

        throw new RuntimeException("Cannot find default value for class" + clazz);
    }

    @SuppressWarnings("unchecked")
    private ObjectNode createPropertyFor(Class<?> type, Map<String, String> genericTypeArgs) {

        log.debug("createPropertyFor: type - " + type + ", genericTypeArgs - " + genericTypeArgs);

        if (ClassUtils.isPrimitiveOrWrapper(type)) {
            return propertyOfLongNode();
        }
        if (String.class.isAssignableFrom(type)) {
            return propertyOfStringNode();
        }
        if (LocalDate.class.isAssignableFrom(type)) {
            return propertyOfDateNode();
        }
        if (type.isEnum()) {
            return propertyOfEnumNode((Class<? extends Enum>) type);
        }
        ObjectNode property = mapper.createObjectNode();
        createEntityDefinition(type, genericTypeArgs, null);
        property.put("$ref", "#/definitions/" + getRefName(type, genericTypeArgs, null));
        return property;
    }

    private ObjectNode propertyOfLongNode() {
        ObjectNode property = mapper.createObjectNode();
        property.put("type", "integer");
        property.put("format", "int64");
        return property;
    }

    private ObjectNode propertyOfStringNode() {
        ObjectNode property = mapper.createObjectNode();
        property.put("type", "string");
        property.put("default", "string");
        return property;
    }

    private ObjectNode propertyOfDateNode() {
        ObjectNode property = mapper.createObjectNode();
        property.put("type", "string");
        property.put("default", "2017-03-28 14:50:09");
        return property;
    }

    private ObjectNode propertyOfEnumNode(Class<? extends Enum> type) {
        ObjectNode property = mapper.createObjectNode();
        property.put("type", "string");
        ArrayNode enumValues = mapper.createArrayNode();
        List<?> enumConstants = enum2list(type);
        for (Object enumConstant : enumConstants) {
            enumValues.add(enumConstant.toString());
        }
        property.set("enum", enumValues);
        return property;
    }

    private ObjectNode document(String hostName) {
        ObjectNode document = mapper.createObjectNode();
        document.put("com/haz4j/swagger4jsonrpc", "2.0");
        document.put("host", hostName);
        document.put("basePath", "/");
        return document;
    }

    List<Enum> enum2list(Class<? extends Enum> cls) {
        return Arrays.asList(cls.getEnumConstants());
    }

    private static String toUnderscore(String s) {
        String regex = "([a-z])([A-Z]+)";
        String replacement = "$1_$2";
        return (s.replaceAll(regex, replacement).toLowerCase());
    }

    private static String getRefName(Class<?> entityClass, Map<String, String> actualTypeArguments, Map<TypeVariable<?>, Type> typeArguments) {
        log.debug("getRefName: entityClass - " + entityClass + ", typeArguments - " + typeArguments);

        String refName = toUnderscore(entityClass.getSimpleName());

        if (!MapUtils.isEmpty(actualTypeArguments)) {
            refName = refName + "_" + actualTypeArguments
                    .values()
                    .stream()
                    .map(s -> {
                        List<String> strings = Arrays.asList(s.split("\\."));
                        return strings.get(strings.size() - 1);
                    })
                    .map(s -> toUnderscore(toSimpleName(s)))
                    .collect(Collectors.joining("_"));
        }

        if (!MapUtils.isEmpty(typeArguments)) {
            refName = refName + "_" + typeArguments
                    .values()
                    .stream()
                    .map(Type::getTypeName)
                    .map(s -> toUnderscore(toSimpleName(s)))
                    .collect(Collectors.joining("_"));
        }

        return refName;
    }

    private static String toSimpleName(String s) {
        if (s.contains(".")) {
            String[] split = s.split("\\.");
            return split[split.length - 1];
        }

        return s;
    }
}