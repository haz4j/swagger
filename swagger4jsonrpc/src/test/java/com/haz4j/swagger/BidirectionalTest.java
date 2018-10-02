package com.haz4j.swagger;

import com.haz4j.swagger.structure.ApiStruct;
import com.haz4j.swagger.test.app.bidirectional.BidirectionalApi;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class BidirectionalTest {

    private JsonGenerator generator = new JsonGenerator();

    public static String HOST_NAME = "localhost:8082/api";

    public static ApiStruct API = ApiMapper.toStruct(Arrays.asList(BidirectionalApi.class));

    @Test
    public void saveTest() {
        ApiStruct apiStruct = API
                .filter("save");

        String json = generator.createJson(HOST_NAME, apiStruct);
        Assert.assertThat(json, Matchers.is("{\"com/haz4j/swagger4jsonrpc\":\"2.0\",\"host\":\"" +
                HOST_NAME +
                "\",\"basePath\":\"/\",\"tags\":[{\"name\":\"bidirectional\",\"description\":\"Api bidirectional\"}],\"paths\":{\"/v1/bidirectional#0\":{\"post\":{\"tags\":[\"bidirectional\"],\"summary\":\"save\",\"description\":\"Save entity\",\"operationId\":\"/v1/bidirectional#0\",\"consumes\":[\"application/json\"],\"produces\":[\"application/json\"],\"parameters\":[{\"in\":\"body\",\"name\":\"JsonRpc\",\"description\":\"JsonRpc\",\"required\":true,\"schema\":{\"type\":\"object\",\"properties\":{\"id\":{\"type\":\"integer\",\"format\":\"int64\"},\"jsonrpc\":{\"type\":\"string\",\"description\":\"jsonrpc version\",\"default\":\"2.0\"},\"method\":{\"type\":\"string\",\"example\":\"save\"},\"params\":{\"type\":\"object\",\"properties\":{\"first_dto\":{\"$ref\":\"#/definitions/first_dto\"},\"first_dtos\":{\"type\":\"array\",\"items\":{\"$ref\":\"#/definitions/first_dto\"}}}}}}}],\"responses\":{}}}},\"definitions\":{\"first_dto\":{\"type\":\"object\",\"properties\":{\"name\":{\"type\":\"string\",\"default\":\"string\"},\"value\":{\"type\":\"string\",\"default\":\"string\"},\"secondDto\":{\"$ref\":\"#/definitions/second_dto\"},\"secondDtos\":{\"type\":\"array\",\"items\":{\"$ref\":\"#/definitions/second_dto\"}}}},\"second_dto\":{\"type\":\"object\",\"properties\":{\"name\":{\"type\":\"string\",\"default\":\"string\"},\"value\":{\"type\":\"string\",\"default\":\"string\"},\"firstDto\":{\"$ref\":\"#/definitions/first_dto\"},\"firstDtos\":{\"type\":\"array\",\"items\":{\"$ref\":\"#/definitions/first_dto\"}}}}}}"));
    }
}