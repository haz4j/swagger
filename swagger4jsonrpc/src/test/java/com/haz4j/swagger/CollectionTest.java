package com.haz4j.swagger;

import com.haz4j.swagger.structure.ApiStruct;
import com.haz4j.swagger.test.app.collection.CollectionApi;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class CollectionTest {

    private JsonGenerator generator = new JsonGenerator();

    public static String HOST_NAME = "localhost:8082/api";

    public static ApiStruct API = ApiMapper.toStruct(Arrays.asList(CollectionApi.class));

    @Test
    public void saveTest() {
        ApiStruct apiStruct = API
                .filter("save");

        String json = generator.createJson(HOST_NAME, apiStruct);
        Assert.assertThat(json, Matchers.is("{\"com/haz4j/swagger4jsonrpc\":\"2.0\",\"host\":\"" +
                HOST_NAME +
                "\",\"basePath\":\"/\",\"tags\":[{\"name\":\"collections\",\"description\":\"Api collections\"}],\"paths\":{\"/v1/collection#0\":{\"post\":{\"tags\":[\"collections\"],\"summary\":\"save\",\"description\":\"Save entity\",\"operationId\":\"/v1/collection#0\",\"consumes\":[\"application/json\"],\"produces\":[\"application/json\"],\"parameters\":[{\"in\":\"body\",\"name\":\"JsonRpc\",\"description\":\"JsonRpc\",\"required\":true,\"schema\":{\"type\":\"object\",\"properties\":{\"id\":{\"type\":\"integer\",\"format\":\"int64\"},\"jsonrpc\":{\"type\":\"string\",\"description\":\"jsonrpc version\",\"default\":\"2.0\"},\"method\":{\"type\":\"string\",\"example\":\"save\"},\"params\":{\"type\":\"object\",\"properties\":{\"collection_dto\":{\"$ref\":\"#/definitions/collection_dto\"},\"collection_dtos\":{\"type\":\"array\",\"items\":{\"$ref\":\"#/definitions/collection_dto\"}}}}}}}],\"responses\":{}}}},\"definitions\":{\"entry\":{\"type\":\"object\",\"properties\":{\"id\":{\"type\":\"integer\",\"format\":\"int64\"},\"value\":{\"type\":\"string\",\"default\":\"string\"}}},\"collection_dto\":{\"type\":\"object\",\"properties\":{\"name\":{\"type\":\"string\",\"default\":\"string\"},\"value\":{\"type\":\"string\",\"default\":\"string\"},\"strings\":{\"type\":\"array\",\"items\":{\"type\":\"string\",\"default\":\"string\"}},\"stringsOfStrings\":{\"type\":\"array\",\"items\":{\"type\":\"array\",\"items\":{\"type\":\"string\",\"default\":\"string\"}}},\"integersOfIntegersOfIntegers\":{\"type\":\"array\",\"items\":{\"type\":\"array\",\"items\":{\"type\":\"array\",\"items\":{\"type\":\"integer\",\"format\":\"int64\"}}}},\"stringsMap\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"3\":{\"type\":\"string\",\"default\":\"string\"}}}},\"littleCompositeMap\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"3\":{\"type\":\"array\",\"items\":{\"type\":\"string\",\"default\":\"string\"}}}}},\"compositeMap\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"3\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"1\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"3\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"1\":{\"type\":\"string\",\"default\":\"string\"}}}}}}}}}}}}},\"entry\":{\"$ref\":\"#/definitions/entry\"},\"entries\":{\"type\":\"array\",\"items\":{\"$ref\":\"#/definitions/entry\"}},\"entriesOfEntries\":{\"type\":\"array\",\"items\":{\"type\":\"array\",\"items\":{\"$ref\":\"#/definitions/entry\"}}}}}}}"));
    }
}