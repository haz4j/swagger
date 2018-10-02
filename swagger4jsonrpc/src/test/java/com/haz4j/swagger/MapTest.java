package com.haz4j.swagger;

import com.haz4j.swagger.structure.ApiStruct;
import com.haz4j.swagger.test.app.map.MapApi;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class MapTest {

    private JsonGenerator generator = new JsonGenerator();

    public static String HOST_NAME = "localhost:8082/api";

    public static ApiStruct API = ApiMapper.toStruct(Arrays.asList(MapApi.class));

    @Test
    public void saveTest() {
        ApiStruct apiStruct = API
                .filter("save");

        String json = generator.createJson(HOST_NAME, apiStruct);
        Assert.assertThat(json, Matchers.is("{\"com/haz4j/swagger4jsonrpc\":\"2.0\",\"host\":\"" +
                HOST_NAME +
                "\",\"basePath\":\"/\",\"tags\":[{\"name\":\"maps\",\"description\":\"Api maps\"}],\"paths\":{\"/v1/map#0\":{\"post\":{\"tags\":[\"maps\"],\"summary\":\"save\",\"description\":\"Save entity\",\"operationId\":\"/v1/map#0\",\"consumes\":[\"application/json\"],\"produces\":[\"application/json\"],\"parameters\":[{\"in\":\"body\",\"name\":\"JsonRpc\",\"description\":\"JsonRpc\",\"required\":true,\"schema\":{\"type\":\"object\",\"properties\":{\"id\":{\"type\":\"integer\",\"format\":\"int64\"},\"jsonrpc\":{\"type\":\"string\",\"description\":\"jsonrpc version\",\"default\":\"2.0\"},\"method\":{\"type\":\"string\",\"example\":\"save\"},\"params\":{\"type\":\"object\",\"properties\":{\"map_dto\":{\"$ref\":\"#/definitions/map_dto\"}}}}}}],\"responses\":{}}}},\"definitions\":{\"entry\":{\"type\":\"object\",\"properties\":{\"id\":{\"type\":\"integer\",\"format\":\"int64\"},\"value\":{\"type\":\"string\",\"default\":\"string\"}}},\"map_dto\":{\"type\":\"object\",\"properties\":{\"name\":{\"type\":\"string\",\"default\":\"string\"},\"value\":{\"type\":\"string\",\"default\":\"string\"},\"simpleInternalMap\":{\"type\":\"object\",\"properties\":{\"1\":{\"type\":\"integer\",\"format\":\"int64\"}}},\"complexInternalMap\":{\"type\":\"object\",\"properties\":{\"1\":{\"type\":\"array\",\"items\":{\"type\":\"integer\",\"format\":\"int64\"}}}},\"moreComplexInternalMap\":{\"type\":\"object\",\"properties\":{\"1\":{\"type\":\"object\",\"properties\":{\"3\":{\"type\":\"integer\",\"format\":\"int64\"}}}}},\"moreAndMoreComplexInternalMap\":{\"type\":\"object\",\"properties\":{\"1\":{\"type\":\"object\",\"properties\":{\"3\":{\"type\":\"object\",\"properties\":{\"3\":{\"type\":\"integer\",\"format\":\"int64\"}}}}}}},\"veryComplexInternalMap\":{\"type\":\"object\",\"properties\":{\"1\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"3\":{\"type\":\"object\",\"properties\":{\"1\":{\"type\":\"object\",\"properties\":{\"1\":{\"type\":\"array\",\"items\":{\"$ref\":\"#/definitions/entry\"}}}}}}}}}}}}}}}"));
    }
}