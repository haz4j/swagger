package com.haz4j.swagger;

import com.haz4j.swagger.structure.ApiStruct;
import com.haz4j.swagger.test.app.generic.GenericApi;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class GenericTest {

    private JsonGenerator generator = new JsonGenerator();

    public static String HOST_NAME = "localhost:8082/api";

    public static ApiStruct API = ApiMapper.toStruct(Arrays.asList(GenericApi.class));

    @Test
    public void genericTest() {
        ApiStruct apiStruct = API.filter("generic");
        String json = generator.createJson(HOST_NAME, apiStruct);

        Assert.assertThat(json, Matchers.is("{\"com/haz4j/swagger4jsonrpc\":\"2.0\",\"host\":\"" +
                HOST_NAME +
                "\",\"basePath\":\"/\",\"tags\":[{\"name\":\"generics\",\"description\":\"Api generics\"}],\"paths\":{\"/v1/generic#0\":{\"post\":{\"tags\":[\"generics\"],\"summary\":\"generic\",\"description\":\"\",\"operationId\":\"/v1/generic#0\",\"consumes\":[\"application/json\"],\"produces\":[\"application/json\"],\"parameters\":[{\"in\":\"body\",\"name\":\"JsonRpc\",\"description\":\"JsonRpc\",\"required\":true,\"schema\":{\"type\":\"object\",\"properties\":{\"id\":{\"type\":\"integer\",\"format\":\"int64\"},\"jsonrpc\":{\"type\":\"string\",\"description\":\"jsonrpc version\",\"default\":\"2.0\"},\"method\":{\"type\":\"string\",\"example\":\"generic\"},\"params\":{\"type\":\"object\",\"properties\":{\"generic_dto\":{\"$ref\":\"#/definitions/generic_dto_entry3_entry1_entry2\"}}}}}}],\"responses\":{}}}},\"definitions\":{\"entry1\":{\"type\":\"object\",\"properties\":{\"id\":{\"type\":\"integer\",\"format\":\"int64\"},\"name\":{\"type\":\"string\",\"default\":\"string\"}}},\"generic_dto_entry3_entry1_entry2\":{\"type\":\"object\",\"properties\":{\"name\":{\"type\":\"string\",\"default\":\"string\"},\"value\":{\"type\":\"string\",\"default\":\"string\"},\"entry1\":{\"$ref\":\"#/definitions/entry3\"},\"entry2\":{\"$ref\":\"#/definitions/entry2\"},\"entry3\":{\"$ref\":\"#/definitions/entry1\"}}},\"entry2\":{\"type\":\"object\",\"properties\":{\"id\":{\"type\":\"integer\",\"format\":\"int64\"},\"value\":{\"type\":\"string\",\"default\":\"string\"}}},\"entry3\":{\"type\":\"object\",\"properties\":{\"offset\":{\"type\":\"integer\",\"format\":\"int64\"},\"limit\":{\"type\":\"integer\",\"format\":\"int64\"}}}}}"));
    }

    @Test
    public void listOfGenericsTest() {
        ApiStruct apiStruct = API.filter("listOfGenerics");
        String json = generator.createJson(HOST_NAME, apiStruct);

        Assert.assertThat(json, Matchers.is("{\"com/haz4j/swagger4jsonrpc\":\"2.0\",\"host\":\"" +
                HOST_NAME +
                "\",\"basePath\":\"/\",\"tags\":[{\"name\":\"generics\",\"description\":\"Api generics\"}],\"paths\":{\"/v1/generic#0\":{\"post\":{\"tags\":[\"generics\"],\"summary\":\"listOfGenerics\",\"description\":\"\",\"operationId\":\"/v1/generic#0\",\"consumes\":[\"application/json\"],\"produces\":[\"application/json\"],\"parameters\":[{\"in\":\"body\",\"name\":\"JsonRpc\",\"description\":\"JsonRpc\",\"required\":true,\"schema\":{\"type\":\"object\",\"properties\":{\"id\":{\"type\":\"integer\",\"format\":\"int64\"},\"jsonrpc\":{\"type\":\"string\",\"description\":\"jsonrpc version\",\"default\":\"2.0\"},\"method\":{\"type\":\"string\",\"example\":\"listOfGenerics\"},\"params\":{\"type\":\"object\",\"properties\":{\"generic_dto\":{\"type\":\"array\",\"items\":{\"$ref\":\"#/definitions/generic_dto_entry3_entry1_entry2\"}}}}}}}],\"responses\":{}}}},\"definitions\":{\"entry1\":{\"type\":\"object\",\"properties\":{\"id\":{\"type\":\"integer\",\"format\":\"int64\"},\"name\":{\"type\":\"string\",\"default\":\"string\"}}},\"generic_dto_entry3_entry1_entry2\":{\"type\":\"object\",\"properties\":{\"name\":{\"type\":\"string\",\"default\":\"string\"},\"value\":{\"type\":\"string\",\"default\":\"string\"},\"entry1\":{\"$ref\":\"#/definitions/entry3\"},\"entry2\":{\"$ref\":\"#/definitions/entry2\"},\"entry3\":{\"$ref\":\"#/definitions/entry1\"}}},\"entry2\":{\"type\":\"object\",\"properties\":{\"id\":{\"type\":\"integer\",\"format\":\"int64\"},\"value\":{\"type\":\"string\",\"default\":\"string\"}}},\"entry3\":{\"type\":\"object\",\"properties\":{\"offset\":{\"type\":\"integer\",\"format\":\"int64\"},\"limit\":{\"type\":\"integer\",\"format\":\"int64\"}}}}}"));
    }

    @Test
    public void genericAndListTest() {
        ApiStruct apiStruct = API.filter("genericAndList");
        String json = generator.createJson(HOST_NAME, apiStruct);

        Assert.assertThat(json, Matchers.is("{\"com/haz4j/swagger4jsonrpc\":\"2.0\",\"host\":\"" +
                HOST_NAME +
                "\",\"basePath\":\"/\",\"tags\":[{\"name\":\"generics\",\"description\":\"Api generics\"}],\"paths\":{\"/v1/generic#0\":{\"post\":{\"tags\":[\"generics\"],\"summary\":\"genericAndList\",\"description\":\"\",\"operationId\":\"/v1/generic#0\",\"consumes\":[\"application/json\"],\"produces\":[\"application/json\"],\"parameters\":[{\"in\":\"body\",\"name\":\"JsonRpc\",\"description\":\"JsonRpc\",\"required\":true,\"schema\":{\"type\":\"object\",\"properties\":{\"id\":{\"type\":\"integer\",\"format\":\"int64\"},\"jsonrpc\":{\"type\":\"string\",\"description\":\"jsonrpc version\",\"default\":\"2.0\"},\"method\":{\"type\":\"string\",\"example\":\"genericAndList\"},\"params\":{\"type\":\"object\",\"properties\":{\"generic_dto\":{\"$ref\":\"#/definitions/generic_dto_entry3_entry1_entry2\"},\"generic_dtos\":{\"type\":\"array\",\"items\":{\"$ref\":\"#/definitions/generic_dto_entry1_entry3_entry2\"}}}}}}}],\"responses\":{}}}},\"definitions\":{\"entry1\":{\"type\":\"object\",\"properties\":{\"id\":{\"type\":\"integer\",\"format\":\"int64\"},\"name\":{\"type\":\"string\",\"default\":\"string\"}}},\"generic_dto_entry3_entry1_entry2\":{\"type\":\"object\",\"properties\":{\"name\":{\"type\":\"string\",\"default\":\"string\"},\"value\":{\"type\":\"string\",\"default\":\"string\"},\"entry1\":{\"$ref\":\"#/definitions/entry3\"},\"entry2\":{\"$ref\":\"#/definitions/entry2\"},\"entry3\":{\"$ref\":\"#/definitions/entry1\"}}},\"generic_dto_entry1_entry3_entry2\":{\"type\":\"object\",\"properties\":{\"name\":{\"type\":\"string\",\"default\":\"string\"},\"value\":{\"type\":\"string\",\"default\":\"string\"},\"entry1\":{\"$ref\":\"#/definitions/entry1\"},\"entry2\":{\"$ref\":\"#/definitions/entry2\"},\"entry3\":{\"$ref\":\"#/definitions/entry3\"}}},\"entry2\":{\"type\":\"object\",\"properties\":{\"id\":{\"type\":\"integer\",\"format\":\"int64\"},\"value\":{\"type\":\"string\",\"default\":\"string\"}}},\"entry3\":{\"type\":\"object\",\"properties\":{\"offset\":{\"type\":\"integer\",\"format\":\"int64\"},\"limit\":{\"type\":\"integer\",\"format\":\"int64\"}}}}}"));
    }

    @Test
    public void mapOfGenericTest() {
        ApiStruct apiStruct = API.filter("mapOfGeneric");
        String json = generator.createJson(HOST_NAME, apiStruct);

        Assert.assertThat(json, Matchers.is("{\"com/haz4j/swagger4jsonrpc\":\"2.0\",\"host\":\"" +
                HOST_NAME +
                "\",\"basePath\":\"/\",\"tags\":[{\"name\":\"generics\",\"description\":\"Api generics\"}],\"paths\":{\"/v1/generic#0\":{\"post\":{\"tags\":[\"generics\"],\"summary\":\"mapOfGeneric\",\"description\":\"\",\"operationId\":\"/v1/generic#0\",\"consumes\":[\"application/json\"],\"produces\":[\"application/json\"],\"parameters\":[{\"in\":\"body\",\"name\":\"JsonRpc\",\"description\":\"JsonRpc\",\"required\":true,\"schema\":{\"type\":\"object\",\"properties\":{\"id\":{\"type\":\"integer\",\"format\":\"int64\"},\"jsonrpc\":{\"type\":\"string\",\"description\":\"jsonrpc version\",\"default\":\"2.0\"},\"method\":{\"type\":\"string\",\"example\":\"mapOfGeneric\"},\"params\":{\"type\":\"object\",\"properties\":{\"generic_dto\":{\"type\":\"object\",\"properties\":{\"3\":{\"$ref\":\"#/definitions/generic_dto_entry1_entry1_entry2\"}}}}}}}}],\"responses\":{}}}},\"definitions\":{\"entry1\":{\"type\":\"object\",\"properties\":{\"id\":{\"type\":\"integer\",\"format\":\"int64\"},\"name\":{\"type\":\"string\",\"default\":\"string\"}}},\"generic_dto_entry1_entry1_entry2\":{\"type\":\"object\",\"properties\":{\"name\":{\"type\":\"string\",\"default\":\"string\"},\"value\":{\"type\":\"string\",\"default\":\"string\"},\"entry1\":{\"$ref\":\"#/definitions/entry1\"},\"entry2\":{\"$ref\":\"#/definitions/entry2\"},\"entry3\":{\"$ref\":\"#/definitions/entry1\"}}},\"entry2\":{\"type\":\"object\",\"properties\":{\"id\":{\"type\":\"integer\",\"format\":\"int64\"},\"value\":{\"type\":\"string\",\"default\":\"string\"}}}}}"));
    }

    @Test
    public void listAndMapOfgenericTest() {
        ApiStruct apiStruct = API.filter("listAndMapOfgeneric");
        String json = generator.createJson(HOST_NAME, apiStruct);

        Assert.assertThat(json, Matchers.is("{\"com/haz4j/swagger4jsonrpc\":\"2.0\",\"host\":\"" +
                HOST_NAME +
                "\",\"basePath\":\"/\",\"tags\":[{\"name\":\"generics\",\"description\":\"Api generics\"}],\"paths\":{\"/v1/generic#0\":{\"post\":{\"tags\":[\"generics\"],\"summary\":\"listAndMapOfgeneric\",\"description\":\"\",\"operationId\":\"/v1/generic#0\",\"consumes\":[\"application/json\"],\"produces\":[\"application/json\"],\"parameters\":[{\"in\":\"body\",\"name\":\"JsonRpc\",\"description\":\"JsonRpc\",\"required\":true,\"schema\":{\"type\":\"object\",\"properties\":{\"id\":{\"type\":\"integer\",\"format\":\"int64\"},\"jsonrpc\":{\"type\":\"string\",\"description\":\"jsonrpc version\",\"default\":\"2.0\"},\"method\":{\"type\":\"string\",\"example\":\"listAndMapOfgeneric\"},\"params\":{\"type\":\"object\",\"properties\":{\"generic_dto\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"3\":{\"type\":\"object\",\"properties\":{\"3\":{\"type\":\"array\",\"items\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"3\":{\"$ref\":\"#/definitions/generic_dto_entry2_entry1_entry3\"}}}}}}}}}}}}}}}],\"responses\":{}}}},\"definitions\":{\"entry1\":{\"type\":\"object\",\"properties\":{\"id\":{\"type\":\"integer\",\"format\":\"int64\"},\"name\":{\"type\":\"string\",\"default\":\"string\"}}},\"generic_dto_entry2_entry1_entry3\":{\"type\":\"object\",\"properties\":{\"name\":{\"type\":\"string\",\"default\":\"string\"},\"value\":{\"type\":\"string\",\"default\":\"string\"},\"entry1\":{\"$ref\":\"#/definitions/entry2\"},\"entry2\":{\"$ref\":\"#/definitions/entry3\"},\"entry3\":{\"$ref\":\"#/definitions/entry1\"}}},\"entry2\":{\"type\":\"object\",\"properties\":{\"id\":{\"type\":\"integer\",\"format\":\"int64\"},\"value\":{\"type\":\"string\",\"default\":\"string\"}}},\"entry3\":{\"type\":\"object\",\"properties\":{\"offset\":{\"type\":\"integer\",\"format\":\"int64\"},\"limit\":{\"type\":\"integer\",\"format\":\"int64\"}}}}}"));
    }

    @Test
    public void arrayOfGenericTest() {
        ApiStruct apiStruct = API.filter("arrayOfGeneric");
        String json = generator.createJson(HOST_NAME, apiStruct);

        Assert.assertThat(json, Matchers.is("{\"com/haz4j/swagger4jsonrpc\":\"2.0\",\"host\":\"" +
                HOST_NAME +
                "\",\"basePath\":\"/\",\"tags\":[{\"name\":\"generics\",\"description\":\"Api generics\"}],\"paths\":{\"/v1/generic#0\":{\"post\":{\"tags\":[\"generics\"],\"summary\":\"arrayOfGeneric\",\"description\":\"\",\"operationId\":\"/v1/generic#0\",\"consumes\":[\"application/json\"],\"produces\":[\"application/json\"],\"parameters\":[{\"in\":\"body\",\"name\":\"JsonRpc\",\"description\":\"JsonRpc\",\"required\":true,\"schema\":{\"type\":\"object\",\"properties\":{\"id\":{\"type\":\"integer\",\"format\":\"int64\"},\"jsonrpc\":{\"type\":\"string\",\"description\":\"jsonrpc version\",\"default\":\"2.0\"},\"method\":{\"type\":\"string\",\"example\":\"arrayOfGeneric\"},\"params\":{\"type\":\"object\",\"properties\":{\"generic_dto\":{\"type\":\"array\",\"items\":{\"$ref\":\"#/definitions/generic_dto_entry2_entry1_entry3\"}}}}}}}],\"responses\":{}}}},\"definitions\":{\"entry1\":{\"type\":\"object\",\"properties\":{\"id\":{\"type\":\"integer\",\"format\":\"int64\"},\"name\":{\"type\":\"string\",\"default\":\"string\"}}},\"generic_dto_entry2_entry1_entry3\":{\"type\":\"object\",\"properties\":{\"name\":{\"type\":\"string\",\"default\":\"string\"},\"value\":{\"type\":\"string\",\"default\":\"string\"},\"entry1\":{\"$ref\":\"#/definitions/entry2\"},\"entry2\":{\"$ref\":\"#/definitions/entry3\"},\"entry3\":{\"$ref\":\"#/definitions/entry1\"}}},\"entry2\":{\"type\":\"object\",\"properties\":{\"id\":{\"type\":\"integer\",\"format\":\"int64\"},\"value\":{\"type\":\"string\",\"default\":\"string\"}}},\"entry3\":{\"type\":\"object\",\"properties\":{\"offset\":{\"type\":\"integer\",\"format\":\"int64\"},\"limit\":{\"type\":\"integer\",\"format\":\"int64\"}}}}}"));
    }

    @Test
    public void severalGenericsTest() {
        ApiStruct apiStruct = API.filter("severalGenerics");
        String json = generator.createJson(HOST_NAME, apiStruct);

        Assert.assertThat(json, Matchers.is("{\"com/haz4j/swagger4jsonrpc\":\"2.0\",\"host\":\"" +
                HOST_NAME +
                "\",\"basePath\":\"/\",\"tags\":[{\"name\":\"generics\",\"description\":\"Api generics\"}],\"paths\":{\"/v1/generic#0\":{\"post\":{\"tags\":[\"generics\"],\"summary\":\"severalGenerics\",\"description\":\"\",\"operationId\":\"/v1/generic#0\",\"consumes\":[\"application/json\"],\"produces\":[\"application/json\"],\"parameters\":[{\"in\":\"body\",\"name\":\"JsonRpc\",\"description\":\"JsonRpc\",\"required\":true,\"schema\":{\"type\":\"object\",\"properties\":{\"id\":{\"type\":\"integer\",\"format\":\"int64\"},\"jsonrpc\":{\"type\":\"string\",\"description\":\"jsonrpc version\",\"default\":\"2.0\"},\"method\":{\"type\":\"string\",\"example\":\"severalGenerics\"},\"params\":{\"type\":\"object\",\"properties\":{\"generic_dto_1\":{\"$ref\":\"#/definitions/generic_dto_entry3_entry1_entry2\"},\"generic_dto_2\":{\"$ref\":\"#/definitions/generic_dto1_entry3\"},\"generic_dto_3\":{\"$ref\":\"#/definitions/generic_dto2_entry2_entry1\"}}}}}}],\"responses\":{}}}},\"definitions\":{\"entry1\":{\"type\":\"object\",\"properties\":{\"id\":{\"type\":\"integer\",\"format\":\"int64\"},\"name\":{\"type\":\"string\",\"default\":\"string\"}}},\"generic_dto_entry3_entry1_entry2\":{\"type\":\"object\",\"properties\":{\"name\":{\"type\":\"string\",\"default\":\"string\"},\"value\":{\"type\":\"string\",\"default\":\"string\"},\"entry1\":{\"$ref\":\"#/definitions/entry3\"},\"entry2\":{\"$ref\":\"#/definitions/entry2\"},\"entry3\":{\"$ref\":\"#/definitions/entry1\"}}},\"generic_dto2_entry2_entry1\":{\"type\":\"object\",\"properties\":{\"first\":{\"$ref\":\"#/definitions/entry2\"},\"second\":{\"$ref\":\"#/definitions/entry1\"},\"third\":{\"type\":\"integer\",\"format\":\"int64\"}}},\"generic_dto1_entry3\":{\"type\":\"object\",\"properties\":{\"first\":{\"$ref\":\"#/definitions/entry3\"},\"second\":{\"type\":\"integer\",\"format\":\"int64\"}}},\"entry2\":{\"type\":\"object\",\"properties\":{\"id\":{\"type\":\"integer\",\"format\":\"int64\"},\"value\":{\"type\":\"string\",\"default\":\"string\"}}},\"entry3\":{\"type\":\"object\",\"properties\":{\"offset\":{\"type\":\"integer\",\"format\":\"int64\"},\"limit\":{\"type\":\"integer\",\"format\":\"int64\"}}}}}"));
    }

}