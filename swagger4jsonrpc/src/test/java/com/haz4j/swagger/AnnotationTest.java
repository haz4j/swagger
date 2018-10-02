package com.haz4j.swagger;

import com.haz4j.swagger.structure.ApiStruct;
import com.haz4j.swagger.test.app.annotation.AnnotationApi;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class AnnotationTest {

    private JsonGenerator generator = new JsonGenerator();

    public static String HOST_NAME = "localhost:8082/api";

    public static ApiStruct API = ApiMapper.toStruct(Arrays.asList(AnnotationApi.class));

    @Test
    public void saveTest() {

        ApiStruct apiStruct = API
                .filter("save");

        String json = generator.createJson(HOST_NAME, apiStruct);

        Assert.assertThat(json, Matchers.is("{\"com/haz4j/swagger4jsonrpc\":\"2.0\",\"host\":\"" +
                HOST_NAME +
                "\",\"basePath\":\"/\",\"tags\":[{\"name\":\"annotation\",\"description\":\"Api annotations\"}],\"paths\":{\"/v1/annotation#0\":{\"post\":{\"tags\":[\"annotation\"],\"summary\":\"save\",\"description\":\"Save entity\",\"operationId\":\"/v1/annotation#0\",\"consumes\":[\"application/json\"],\"produces\":[\"application/json\"],\"parameters\":[{\"in\":\"body\",\"name\":\"JsonRpc\",\"description\":\"JsonRpc\",\"required\":true,\"schema\":{\"type\":\"object\",\"properties\":{\"id\":{\"type\":\"integer\",\"format\":\"int64\"},\"jsonrpc\":{\"type\":\"string\",\"description\":\"jsonrpc version\",\"default\":\"2.0\"},\"method\":{\"type\":\"string\",\"example\":\"save\"},\"params\":{\"type\":\"object\",\"properties\":{\"annotation_dto_from_annotation\":{\"$ref\":\"#/definitions/annotation_dto\"}}}}}}],\"responses\":{}}}},\"definitions\":{\"annotation_dto\":{\"type\":\"object\",\"properties\":{\"propertyName1\":{\"type\":\"string\",\"default\":\"string\"},\"propertyName2\":{\"type\":\"string\",\"default\":\"string\"},\"property_from_annotation\":{\"type\":\"string\",\"default\":\"string\"}}}}}"));
    }
}
