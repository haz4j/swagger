package com.haz4j.test.app;

import com.haz4j.swagger.Docket;
import com.haz4j.swagger.SwaggerListener;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MappingTest {

    @Autowired
    private SwaggerListener swaggerListener;

    private Docket docket = null;

    @Test
    public void arrayTest() {
        docket = Docket.builder()
                .host("localhost")
                .pathMapping("array")
                .build();

        String json = swaggerListener.generateJson(Arrays.asList(docket));
        assertThat(json, is(("{\"com/haz4j/swagger4jsonrpc\":\"2.0\",\"host\":\"localhost\",\"basePath\":\"/\",\"tags\":[{\"name\":\"arrays\",\"description\":\"Api arrays\"}],\"paths\":{\"/v1/array#0\":{\"post\":{\"tags\":[\"arrays\"],\"summary\":\"save\",\"description\":\"Save entity\",\"operationId\":\"/v1/array#0\",\"consumes\":[\"application/json\"],\"produces\":[\"application/json\"],\"parameters\":[{\"in\":\"body\",\"name\":\"JsonRpc\",\"description\":\"JsonRpc\",\"required\":true,\"schema\":{\"type\":\"object\",\"properties\":{\"id\":{\"type\":\"integer\",\"format\":\"int64\"},\"jsonrpc\":{\"type\":\"string\",\"description\":\"jsonrpc version\",\"default\":\"2.0\"},\"method\":{\"type\":\"string\",\"example\":\"save\"},\"params\":{\"type\":\"object\",\"properties\":{\"array_dto\":{\"$ref\":\"#/definitions/array_dto\"},\"array_dtos\":{\"type\":\"array\",\"items\":{\"$ref\":\"#/definitions/array_dto\"}}}}}}}],\"responses\":{}}}},\"definitions\":{\"array_dto\":{\"type\":\"object\",\"properties\":{\"name\":{\"type\":\"string\",\"default\":\"string\"},\"value\":{\"type\":\"string\",\"default\":\"string\"}}}}}")));
    }

    @Test
    public void collectionTest() {
        docket = Docket.builder()
                .host("localhost")
                .pathMapping("collection")
                .build();

        String json = swaggerListener.generateJson(Arrays.asList(docket));
        assertThat(json, is(("{\"com/haz4j/swagger4jsonrpc\":\"2.0\",\"host\":\"localhost\",\"basePath\":\"/\",\"tags\":[{\"name\":\"collections\",\"description\":\"Api collections\"}],\"paths\":{\"/v1/collection#0\":{\"post\":{\"tags\":[\"collections\"],\"summary\":\"save\",\"description\":\"Save entity\",\"operationId\":\"/v1/collection#0\",\"consumes\":[\"application/json\"],\"produces\":[\"application/json\"],\"parameters\":[{\"in\":\"body\",\"name\":\"JsonRpc\",\"description\":\"JsonRpc\",\"required\":true,\"schema\":{\"type\":\"object\",\"properties\":{\"id\":{\"type\":\"integer\",\"format\":\"int64\"},\"jsonrpc\":{\"type\":\"string\",\"description\":\"jsonrpc version\",\"default\":\"2.0\"},\"method\":{\"type\":\"string\",\"example\":\"save\"},\"params\":{\"type\":\"object\",\"properties\":{\"array_dto\":{\"$ref\":\"#/definitions/collection_dto\"},\"array_dtos\":{\"type\":\"array\",\"items\":{\"$ref\":\"#/definitions/collection_dto\"}}}}}}}],\"responses\":{}}}},\"definitions\":{\"collection_dto\":{\"type\":\"object\",\"properties\":{\"name\":{\"type\":\"string\",\"default\":\"string\"},\"value\":{\"type\":\"string\",\"default\":\"string\"}}}}}")));
    }

    @Test
    public void bidirectionalTest() {
        docket = Docket.builder()
                .host("localhost")
                .pathMapping("bidirectional")
                .build();

        String json = swaggerListener.generateJson(Arrays.asList(docket));
        assertThat(json, is(("{\"com/haz4j/swagger4jsonrpc\":\"2.0\",\"host\":\"localhost\",\"basePath\":\"/\",\"tags\":[{\"name\":\"bidirectional\",\"description\":\"Api bidirectional\"}],\"paths\":{\"/v1/bidirectional#0\":{\"post\":{\"tags\":[\"bidirectional\"],\"summary\":\"save\",\"description\":\"Save entity\",\"operationId\":\"/v1/bidirectional#0\",\"consumes\":[\"application/json\"],\"produces\":[\"application/json\"],\"parameters\":[{\"in\":\"body\",\"name\":\"JsonRpc\",\"description\":\"JsonRpc\",\"required\":true,\"schema\":{\"type\":\"object\",\"properties\":{\"id\":{\"type\":\"integer\",\"format\":\"int64\"},\"jsonrpc\":{\"type\":\"string\",\"description\":\"jsonrpc version\",\"default\":\"2.0\"},\"method\":{\"type\":\"string\",\"example\":\"save\"},\"params\":{\"type\":\"object\",\"properties\":{\"first_dto\":{\"$ref\":\"#/definitions/first_dto\"},\"first_dtos\":{\"type\":\"array\",\"items\":{\"$ref\":\"#/definitions/first_dto\"}}}}}}}],\"responses\":{}}}},\"definitions\":{\"first_dto\":{\"type\":\"object\",\"properties\":{\"name\":{\"type\":\"string\",\"default\":\"string\"},\"value\":{\"type\":\"string\",\"default\":\"string\"},\"secondDto\":{\"$ref\":\"#/definitions/second_dto\"},\"secondDtos\":{\"type\":\"array\",\"items\":{\"$ref\":\"#/definitions/second_dto\"}}}},\"second_dto\":{\"type\":\"object\",\"properties\":{\"name\":{\"type\":\"string\",\"default\":\"string\"},\"value\":{\"type\":\"string\",\"default\":\"string\"},\"firstDto\":{\"$ref\":\"#/definitions/first_dto\"},\"firstDtos\":{\"type\":\"array\",\"items\":{\"$ref\":\"#/definitions/first_dto\"}}}}}}")));
    }


    @Test
    public void annotationTest() {
        docket = Docket.builder()
                .host("localhost")
                .pathMapping("annotation")
                .build();

        String json = swaggerListener.generateJson(Arrays.asList(docket));
        assertThat(json, is(("{\"com/haz4j/swagger4jsonrpc\":\"2.0\",\"host\":\"localhost\",\"basePath\":\"/\",\"tags\":[{\"name\":\"annotation\",\"description\":\"Api annotations\"}],\"paths\":{\"/v1/annotation#0\":{\"post\":{\"tags\":[\"annotation\"],\"summary\":\"save\",\"description\":\"Save entity\",\"operationId\":\"/v1/annotation#0\",\"consumes\":[\"application/json\"],\"produces\":[\"application/json\"],\"parameters\":[{\"in\":\"body\",\"name\":\"JsonRpc\",\"description\":\"JsonRpc\",\"required\":true,\"schema\":{\"type\":\"object\",\"properties\":{\"id\":{\"type\":\"integer\",\"format\":\"int64\"},\"jsonrpc\":{\"type\":\"string\",\"description\":\"jsonrpc version\",\"default\":\"2.0\"},\"method\":{\"type\":\"string\",\"example\":\"save\"},\"params\":{\"type\":\"object\",\"properties\":{\"annotation_dto_from_annotation\":{\"$ref\":\"#/definitions/annotation_dto\"}}}}}}],\"responses\":{}}}},\"definitions\":{\"annotation_dto\":{\"type\":\"object\",\"properties\":{\"propertyName1\":{\"type\":\"string\",\"default\":\"string\"},\"propertyName2\":{\"type\":\"string\",\"default\":\"string\"},\"property_from_annotation\":{\"type\":\"string\",\"default\":\"string\"}}}}}")));
    }
    @Test
    public void enumTest() {
        docket = Docket.builder()
                .host("localhost")
                .pathMapping("enums")
                .build();

        String json = swaggerListener.generateJson(Arrays.asList(docket));
        assertThat(json, is(("{\"com/haz4j/swagger4jsonrpc\":\"2.0\",\"host\":\"localhost\",\"basePath\":\"/\",\"tags\":[{\"name\":\"enums\",\"description\":\"Api enums\"}],\"paths\":{\"/v1/enums#0\":{\"post\":{\"tags\":[\"enums\"],\"summary\":\"save\",\"description\":\"Save entity\",\"operationId\":\"/v1/enums#0\",\"consumes\":[\"application/json\"],\"produces\":[\"application/json\"],\"parameters\":[{\"in\":\"body\",\"name\":\"JsonRpc\",\"description\":\"JsonRpc\",\"required\":true,\"schema\":{\"type\":\"object\",\"properties\":{\"id\":{\"type\":\"integer\",\"format\":\"int64\"},\"jsonrpc\":{\"type\":\"string\",\"description\":\"jsonrpc version\",\"default\":\"2.0\"},\"method\":{\"type\":\"string\",\"example\":\"save\"},\"params\":{\"type\":\"object\",\"properties\":{\"enum_dto\":{\"type\":\"string\",\"enum\":[\"FIRST_VALUE\",\"SECOND_VALUE\"]}}}}}}],\"responses\":{}}}},\"definitions\":{}}")));
    }


}