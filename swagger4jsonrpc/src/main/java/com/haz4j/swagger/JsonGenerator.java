package com.haz4j.swagger;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
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

@Component
@Slf4j
public class JsonGenerator {

    private ObjectMapper mapper = new ObjectMapper();

    private Map<String, JsonNode> definitions;

    public String createJson(String hostName, Map<Class, Map<Method, List<Parameter>>> apiMap) {

        definitions = new HashMap<>();

        ArrayNode tagsArray = mapper.createArrayNode();
        ObjectNode document = document(hostName);
        ObjectNode paths = mapper.createObjectNode();
        ObjectNode definitionsNode = mapper.createObjectNode();

        apiMap.forEach((api, methods) -> {

            log.debug("api = " + api);

            ObjectNode tagNode = mapper.createObjectNode();
            Pair<String, String> tag = ReflectionUtils.getTag(api);
            tagNode.put("name", tag.getLeft());
            tagNode.put("description", tag.getRight());
            tagsArray.add(tagNode);

            String path = ReflectionUtils.getPath(api);

            int i = 0;

            for (Map.Entry<Method, List<Parameter>> entry : methods.entrySet()) {
                Method method = entry.getKey();
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

    private ObjectNode createMethod(String tagName, Method method, String operationId) {
        log.debug("createMethod - " + method);
        ObjectNode objectNode = mapper.createObjectNode();

        String description = ReflectionUtils.getDescription(method);

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
    private ObjectNode createMethodScheme(Method method) {
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

        if ((method.getParameters().length > 0)) {
            ObjectNode schemaNode = createEntityRef(method);
            propertiesNode.set("params", schemaNode);
        }

        definitionNode.set("properties", propertiesNode);
        return definitionNode;
    }

    private ObjectNode createEntityRef(Method method) {
        log.debug("createEntityRef - " + method);

        if ((method.getParameters().length == 0)) {
            return null;
        }

        ObjectNode definitionNode = mapper.createObjectNode();
        definitionNode.put("type", "object");
        ObjectNode propertiesNode = mapper.createObjectNode();

        for (Parameter parameter : method.getParameters()) {
            JsonNode paramFromMethodParameter = createParamFromMethodParameter(parameter);
            String propertyName = ReflectionUtils.getJsonRpcParam(parameter);
            propertiesNode.set(propertyName, paramFromMethodParameter);
        }

        definitionNode.set("properties", propertiesNode);
        return definitionNode;
    }

    private JsonNode createParamFromMethodParameter(Parameter parameter) {
        log.debug("createParamFromMethodParameter - " + parameter);

        Class<?> type = parameter.getType();

        if (Collection.class.isAssignableFrom(type)) {

            ParameterizedType parameterizedType = (ParameterizedType) parameter.getParameterizedType();
            return validateAndCreateNodeForCollection(type, parameterizedType);

        } else if (type.isArray()) {

            return createArrayNode(type.getComponentType(), null);

        } else if (Map.class.isAssignableFrom(type)) {

            ParameterizedType parameterizedType = (ParameterizedType) parameter.getParameterizedType();
            return validateAndCreateNodeForMap(type, parameterizedType);

        } else {

            return createPropertyFor(type, null);

        }
    }

    //type - collection
    private ObjectNode validateAndCreateNodeForCollection(Class<?> type, ParameterizedType parameterizedType) {
        log.debug("validateAndCreateNodeForCollection: type - " + type + ", parameterizedType - " + parameterizedType);

        validateTypeArgsLength(type, parameterizedType, 1);
        Type typeOfCollectionElement = parameterizedType.getActualTypeArguments()[0];

        return createArrayNode(typeOfCollectionElement, null);
    }

    private ObjectNode validateAndCreateNodeForMap(Class<?> type, ParameterizedType parameterizedType) {
        log.debug("validateAndCreateNodeForMap: type - " + type + ", parameterizedType - " + parameterizedType);

        validateTypeArgsLength(type, parameterizedType, 2);
        Class keyClass = (Class) parameterizedType.getActualTypeArguments()[0];
        Type valueClass = parameterizedType.getActualTypeArguments()[1];
        String defaultValue = defaultValueOf(keyClass);

        //TODO: разобраться, почему нужно отдельно сравнивать с ParameterizedTypeImpl
        if (valueClass.getClass().isAssignableFrom(ParameterizedType.class) ||
                valueClass.getClass().isAssignableFrom(ParameterizedTypeImpl.class)) {
            Type rawType = ((ParameterizedType) valueClass).getRawType();
            Type[] actualTypeArguments = ((ParameterizedType) valueClass).getActualTypeArguments();

            return createNodeForMap(defaultValue, rawType, actualTypeArguments);
        } else {
            return createNodeForMap(defaultValue, valueClass, null);
        }
    }

    private void validateTypeArgsLength(Class<?> type, ParameterizedType parameterizedType, int length) {
        log.debug("validateTypeArgsLength: type - " + type + ", parameterizedType - " + parameterizedType + ", length - " + length);

        if (parameterizedType == null || parameterizedType.getActualTypeArguments() == null || parameterizedType.getActualTypeArguments().length != length) {
            throw new RuntimeException("Can't find actual type for field" + type);
        }
    }

    private void createEntityDefinition(Class<?> entityClass, Map<TypeVariable<?>, Type> typeArguments) {
        log.debug("createEntityDefinition: entityClass - " + entityClass + ", typeArguments - " + typeArguments);

        String refName = getRefName(entityClass, typeArguments);

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

            Class<?> type = field.getType();
            if (Collection.class.isAssignableFrom(type)) {
                ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
                ObjectNode arrayNode = validateAndCreateNodeForCollection(type, parameterizedType);
                properties.set(fieldName, arrayNode);
            } else if (type.isArray()) {
                ObjectNode arrayNode = createArrayNode(type.getComponentType(), null);
                properties.set(fieldName, arrayNode);
            } else if (Map.class.isAssignableFrom(type)) {
                ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
                ObjectNode mapNode = validateAndCreateNodeForMap(type, parameterizedType);
                properties.set(fieldName, mapNode);
            } else {
                //field class has no suitable api and I don't wont to use reflection here
                //so we will parse string like "private T ru.nic.rates.core.domain.entity.EntityFromFile.entity"
                //to check if this field has generic
                Type realType = ReflectionUtils.getRealType(typeArguments, field);

                ObjectNode property;
                if (realType != null) {
                    Class<?> realTypeClass = TypeUtils.getRawType(realType, null);
                    property = createPropertyFor(realTypeClass, null);
                } else {
                    property = createPropertyFor(type, null);
                }

                properties.set(fieldName, property);
            }
        }
        entityNode.set("properties", properties);
        definitions.put(refName, entityNode);
    }

    private ObjectNode createNodeForCollection(Class<?> type, Type[] typeArgumentsArray, Map<TypeVariable<?>, Type> typeArgumentsMap) {
        //TODO: проверить все ли параметры попали в логи
        log.debug("createNodeForCollection: type - " + type + ", typeArgumentsMap - " + typeArgumentsMap);

        ObjectNode arrayNode = mapper.createObjectNode();
        arrayNode.put("type", "array");

        ObjectNode items;
        List<Type> allTypes = combineTypes(typeArgumentsArray, typeArgumentsMap);
        if (Collection.class.isAssignableFrom(type) && allTypes.size() > 0) {
            //TODO: merge with validateAndCreateNodeForCollection
            items = createArrayNode(allTypes.get(0), typeArgumentsArray); //TODO: вот тут хз, может null передавать нужно, я не знаю

        } else if (Map.class.isAssignableFrom(type)) {

            Type keyClass = allTypes.get(0); //TODO: merge with validateAndCreateNodeForMap
            Type valueClass = allTypes.get(1); //TODO: merge with validateAndCreateNodeForMap
            //TODO: это полный пздц и это нужно переписать

            String defaultValue = defaultValueOf((Class) keyClass);

            if (valueClass.getClass().isAssignableFrom(ParameterizedType.class) ||
                    valueClass.getClass().isAssignableFrom(ParameterizedTypeImpl.class)) {
                Type rawType = ((ParameterizedType) valueClass).getRawType();
                Type[] actualTypeArguments = ((ParameterizedType) valueClass).getActualTypeArguments();

                items = createNodeForMap(defaultValue, rawType, actualTypeArguments);
            } else {
                items = createNodeForMap(defaultValue, allTypes.get(1), null);
            }

        } else {
            items = createPropertyFor(type, typeArgumentsMap); //TODO: вот тут хз, может null передавать нужно, я не знаю
        }
        arrayNode.set("items", items);
        return arrayNode;
    }

    //TODO: в необходимости этого сомневаюсь
    private List<Type> combineTypes(Type[] typeArguments, Map<TypeVariable<?>, Type> genericTypeArguments) {
        List<Type> allTypes = new ArrayList<>();
        if (genericTypeArguments != null && genericTypeArguments.values() != null && genericTypeArguments.values().size() > 0) {
            allTypes.addAll(genericTypeArguments.values());
        }

        if(typeArguments != null && typeArguments.length > 0){
            allTypes.addAll(Arrays.asList(typeArguments));
        }
        return allTypes;
    }

    private ObjectNode createNodeForMap(String defaultValue, Type type, Type[] typeArgumentsArray) {
        log.debug("createNodeForMap: defaultValue - " + defaultValue + ", type - " + type);

        ObjectNode mapNode = mapper.createObjectNode();
        mapNode.put("type", "object");

        ObjectNode valueNode;

        if (type.getClass().isAssignableFrom(ParameterizedType.class) ||
                type.getClass().isAssignableFrom(ParameterizedTypeImpl.class)) {
            Type rawType = ((ParameterizedType) type).getRawType();
            Type[] actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
            System.out.println();
        }

        List<Type> allTypes = typeArgumentsArray == null ? new ArrayList<>() : Arrays.asList(typeArgumentsArray);

        if (Collection.class.isAssignableFrom((Class) type)/* && typeArguments != null*/) { //TODO: add check
            //TODO: merge with validateAndCreateNodeForCollection
            valueNode = createArrayNode(allTypes.get(0), typeArgumentsArray); //TODO: вот тут хз, может null передавать нужно, я не знаю
        } else if (Map.class.isAssignableFrom((Class) type)) {

            Type keyClass = allTypes.get(0);
            Type valueClass = allTypes.get(1);
            String nextDefaultValue = defaultValueOf((Class) keyClass);

            if (valueClass.getClass().isAssignableFrom(ParameterizedType.class) ||
                    valueClass.getClass().isAssignableFrom(ParameterizedTypeImpl.class)) {
                Type rawType = ((ParameterizedType) valueClass).getRawType();
                Type[] actualTypeArguments = ((ParameterizedType) valueClass).getActualTypeArguments();

                valueNode =  createNodeForMap(nextDefaultValue, rawType, actualTypeArguments);
            } else {
                valueNode = createNodeForMap(nextDefaultValue, allTypes.get(1), null);
            }

        } else {
            valueNode = createPropertyFor((Class) type, null);  //TODO: вот тут хз, может null передавать нужно, я не знаю
        }

        ObjectNode propertiesNode = mapper.createObjectNode();
        propertiesNode.set(defaultValue, valueNode);
        mapNode.set("properties", propertiesNode);
        return mapNode;
    }

    //type - element of collection
    private ObjectNode createArrayNode(Type type, Type[] typeArgumentsArray) {
        //TODO: разобраться, а мы прокидываем typeArgumentsArray туда-сюда постоянно?
        //TODO: а тест на map то я написал?
        log.debug("createArrayNode: type - " + type);

        ObjectNode arrayNode;
        if (Class.class.isAssignableFrom(type.getClass())) {
            //string
            //TODO: самое тупое, что можно взять аргументы вот таким способом
//            Map<TypeVariable<?>, Type> innerTypeArguments = TypeUtils.getTypeArguments((ParameterizedType) type);

            arrayNode = createNodeForCollection((Class) type, typeArgumentsArray, null);
        } else {
            //collection or map
            Class<?> rawType = TypeUtils.getRawType(type, null);
//            TODO: раскомментировать и подумать
//            if (typeArgumentsArray != null && typeArgumentsArray.length > 0) {
//                throw new RuntimeException("may be we should merge args");
//            }
            //TODO: проверить тип теперь кастингом
            arrayNode = createNodeForCollection(rawType, ((ParameterizedTypeImpl) type).getActualTypeArguments(), null);
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
    private ObjectNode createPropertyFor(Class<?> type, Map<TypeVariable<?>, Type> typeArguments) {
        log.debug("createPropertyFor: type - " + type + ", typeArguments - " + typeArguments);

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
            return propertyOfEnumNode((Class<? extends Enum>)type);
        }
        ObjectNode property = mapper.createObjectNode();
        createEntityDefinition(type, typeArguments);
        property.put("$ref", "#/definitions/" + getRefName(type, typeArguments));
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

    private static String getRefName(Class<?> entityClass, Map<TypeVariable<?>, Type> typeArguments) {
        log.debug("getRefName: entityClass - " + entityClass + ", typeArguments - " + typeArguments);

        String refName = toUnderscore(entityClass.getSimpleName());
        if (!MapUtils.isEmpty(typeArguments)) {
            refName = refName +"_"+ typeArguments
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