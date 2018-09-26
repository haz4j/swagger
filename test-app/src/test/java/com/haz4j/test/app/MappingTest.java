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
        assertThat(json, is(("{\"com/haz4j/swagger4jsonrpc\":\"2.0\",\"host\":\"localhost\",\"basePath\":\"/\",\"tags\":[{\"name\":\"collections\",\"description\":\"Api collections\"}],\"paths\":{\"/v1/collection#0\":{\"post\":{\"tags\":[\"collections\"],\"summary\":\"save\",\"description\":\"Save entity\",\"operationId\":\"/v1/collection#0\",\"consumes\":[\"application/json\"],\"produces\":[\"application/json\"],\"parameters\":[{\"in\":\"body\",\"name\":\"JsonRpc\",\"description\":\"JsonRpc\",\"required\":true,\"schema\":{\"type\":\"object\",\"properties\":{\"id\":{\"type\":\"integer\",\"format\":\"int64\"},\"jsonrpc\":{\"type\":\"string\",\"description\":\"jsonrpc version\",\"default\":\"2.0\"},\"method\":{\"type\":\"string\",\"example\":\"save\"},\"params\":{\"type\":\"object\",\"properties\":{\"collection_dto\":{\"$ref\":\"#/definitions/collection_dto\"},\"collection_dtos\":{\"type\":\"array\",\"items\":{\"$ref\":\"#/definitions/collection_dto\"}}}}}}}],\"responses\":{}}}},\"definitions\":{\"entry\":{\"type\":\"object\",\"properties\":{\"id\":{\"type\":\"integer\",\"format\":\"int64\"},\"value\":{\"type\":\"string\",\"default\":\"string\"}}},\"collection_dto\":{\"type\":\"object\",\"properties\":{\"name\":{\"type\":\"string\",\"default\":\"string\"},\"value\":{\"type\":\"string\",\"default\":\"string\"},\"strings\":{\"type\":\"array\",\"items\":{\"type\":\"string\",\"default\":\"string\"}},\"stringsOfStrings\":{\"type\":\"array\",\"items\":{\"type\":\"array\",\"items\":{\"type\":\"string\",\"default\":\"string\"}}},\"integersOfIntegersOfIntegers\":{\"type\":\"array\",\"items\":{\"type\":\"array\",\"items\":{\"type\":\"array\",\"items\":{\"type\":\"integer\",\"format\":\"int64\"}}}},\"stringsMap\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"3\":{\"type\":\"string\",\"default\":\"string\"}}}},\"littleCompositeMap\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"3\":{\"type\":\"array\",\"items\":{\"type\":\"string\",\"default\":\"string\"}}}}},\"compositeMap\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"3\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"1\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"3\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"1\":{\"type\":\"string\",\"default\":\"string\"}}}}}}}}}}}}},\"entry\":{\"$ref\":\"#/definitions/entry\"},\"entries\":{\"type\":\"array\",\"items\":{\"$ref\":\"#/definitions/entry\"}},\"entriesOfEntries\":{\"type\":\"array\",\"items\":{\"type\":\"array\",\"items\":{\"$ref\":\"#/definitions/entry\"}}}}}}}")));
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

    @Test
    public void mapTest() {
        docket = Docket.builder()
                .host("localhost")
                .pathMapping("map")
                .build();

        String json = swaggerListener.generateJson(Arrays.asList(docket));
        assertThat(json, is(("{\"com/haz4j/swagger4jsonrpc\":\"2.0\",\"host\":\"localhost\",\"basePath\":\"/\",\"tags\":[{\"name\":\"maps\",\"description\":\"Api maps\"}],\"paths\":{\"/v1/map#0\":{\"post\":{\"tags\":[\"maps\"],\"summary\":\"save\",\"description\":\"Save entity\",\"operationId\":\"/v1/map#0\",\"consumes\":[\"application/json\"],\"produces\":[\"application/json\"],\"parameters\":[{\"in\":\"body\",\"name\":\"JsonRpc\",\"description\":\"JsonRpc\",\"required\":true,\"schema\":{\"type\":\"object\",\"properties\":{\"id\":{\"type\":\"integer\",\"format\":\"int64\"},\"jsonrpc\":{\"type\":\"string\",\"description\":\"jsonrpc version\",\"default\":\"2.0\"},\"method\":{\"type\":\"string\",\"example\":\"save\"},\"params\":{\"type\":\"object\",\"properties\":{\"map_dto\":{\"$ref\":\"#/definitions/map_dto\"}}}}}}],\"responses\":{}}}},\"definitions\":{\"entry\":{\"type\":\"object\",\"properties\":{\"id\":{\"type\":\"integer\",\"format\":\"int64\"},\"value\":{\"type\":\"string\",\"default\":\"string\"}}},\"map_dto\":{\"type\":\"object\",\"properties\":{\"name\":{\"type\":\"string\",\"default\":\"string\"},\"value\":{\"type\":\"string\",\"default\":\"string\"},\"simpleInternalMap\":{\"type\":\"object\",\"properties\":{\"1\":{\"type\":\"integer\",\"format\":\"int64\"}}},\"complexInternalMap\":{\"type\":\"object\",\"properties\":{\"1\":{\"type\":\"array\",\"items\":{\"type\":\"integer\",\"format\":\"int64\"}}}},\"moreComplexInternalMap\":{\"type\":\"object\",\"properties\":{\"1\":{\"type\":\"object\",\"properties\":{\"3\":{\"type\":\"integer\",\"format\":\"int64\"}}}}},\"moreAndMoreComplexInternalMap\":{\"type\":\"object\",\"properties\":{\"1\":{\"type\":\"object\",\"properties\":{\"3\":{\"type\":\"object\",\"properties\":{\"3\":{\"type\":\"integer\",\"format\":\"int64\"}}}}}}},\"veryComplexInternalMap\":{\"type\":\"object\",\"properties\":{\"1\":{\"type\":\"array\",\"items\":{\"type\":\"object\",\"properties\":{\"3\":{\"type\":\"object\",\"properties\":{\"1\":{\"type\":\"object\",\"properties\":{\"1\":{\"type\":\"array\",\"items\":{\"$ref\":\"#/definitions/entry\"}}}}}}}}}}}}}}}")));
    }

    @Test
    public void genericTest() {
        docket = Docket.builder()
                .host("localhost")
                .pathMapping("generic")
                .build();

        String json = swaggerListener.generateJson(Arrays.asList(docket));
        assertThat(json, is(("{\"com/haz4j/swagger4jsonrpc\":\"2.0\",\"host\":\"localhost\",\"basePath\":\"/\",\"tags\":[{\"name\":\"generics\",\"description\":\"Api generics\"}],\"paths\":{\"/v1/generic#0\":{\"post\":{\"tags\":[\"generics\"],\"summary\":\"save3\",\"description\":\"\",\"operationId\":\"/v1/generic#0\",\"consumes\":[\"application/json\"],\"produces\":[\"application/json\"],\"parameters\":[{\"in\":\"body\",\"name\":\"JsonRpc\",\"description\":\"JsonRpc\",\"required\":true,\"schema\":{\"type\":\"object\",\"properties\":{\"id\":{\"type\":\"integer\",\"format\":\"int64\"},\"jsonrpc\":{\"type\":\"string\",\"description\":\"jsonrpc version\",\"default\":\"2.0\"},\"method\":{\"type\":\"string\",\"example\":\"save3\"},\"params\":{\"type\":\"object\",\"properties\":{\"generic_dto\":{\"$ref\":\"#/definitions/generic_dto_entry3_entry1_entry2\"},\"generic_dtos\":{\"type\":\"array\",\"items\":{\"$ref\":\"#/definitions/generic_dto_entry1_entry3_entry2\"}}}}}}}],\"responses\":{}}},\"/v1/generic#1\":{\"post\":{\"tags\":[\"generics\"],\"summary\":\"save2\",\"description\":\"\",\"operationId\":\"/v1/generic#1\",\"consumes\":[\"application/json\"],\"produces\":[\"application/json\"],\"parameters\":[{\"in\":\"body\",\"name\":\"JsonRpc\",\"description\":\"JsonRpc\",\"required\":true,\"schema\":{\"type\":\"object\",\"properties\":{\"id\":{\"type\":\"integer\",\"format\":\"int64\"},\"jsonrpc\":{\"type\":\"string\",\"description\":\"jsonrpc version\",\"default\":\"2.0\"},\"method\":{\"type\":\"string\",\"example\":\"save2\"},\"params\":{\"type\":\"object\",\"properties\":{\"generic_dto\":{\"type\":\"array\",\"items\":{\"$ref\":\"#/definitions/generic_dto_entry3_entry1_entry2\"}}}}}}}],\"responses\":{}}},\"/v1/generic#2\":{\"post\":{\"tags\":[\"generics\"],\"summary\":\"save1\",\"description\":\"\",\"operationId\":\"/v1/generic#2\",\"consumes\":[\"application/json\"],\"produces\":[\"application/json\"],\"parameters\":[{\"in\":\"body\",\"name\":\"JsonRpc\",\"description\":\"JsonRpc\",\"required\":true,\"schema\":{\"type\":\"object\",\"properties\":{\"id\":{\"type\":\"integer\",\"format\":\"int64\"},\"jsonrpc\":{\"type\":\"string\",\"description\":\"jsonrpc version\",\"default\":\"2.0\"},\"method\":{\"type\":\"string\",\"example\":\"save1\"},\"params\":{\"type\":\"object\",\"properties\":{\"generic_dto\":{\"$ref\":\"#/definitions/generic_dto_entry3_entry1_entry2\"}}}}}}],\"responses\":{}}}},\"definitions\":{\"entry1\":{\"type\":\"object\",\"properties\":{\"id\":{\"type\":\"integer\",\"format\":\"int64\"},\"name\":{\"type\":\"string\",\"default\":\"string\"}}},\"generic_dto_entry3_entry1_entry2\":{\"type\":\"object\",\"properties\":{\"name\":{\"type\":\"string\",\"default\":\"string\"},\"value\":{\"type\":\"string\",\"default\":\"string\"},\"entry1\":{\"$ref\":\"#/definitions/entry3\"},\"entry2\":{\"$ref\":\"#/definitions/entry2\"},\"entry3\":{\"$ref\":\"#/definitions/entry1\"}}},\"generic_dto_entry1_entry3_entry2\":{\"type\":\"object\",\"properties\":{\"name\":{\"type\":\"string\",\"default\":\"string\"},\"value\":{\"type\":\"string\",\"default\":\"string\"},\"entry1\":{\"$ref\":\"#/definitions/entry1\"},\"entry2\":{\"$ref\":\"#/definitions/entry2\"},\"entry3\":{\"$ref\":\"#/definitions/entry3\"}}},\"entry2\":{\"type\":\"object\",\"properties\":{\"id\":{\"type\":\"integer\",\"format\":\"int64\"},\"value\":{\"type\":\"string\",\"default\":\"string\"}}},\"entry3\":{\"type\":\"object\",\"properties\":{\"offset\":{\"type\":\"integer\",\"format\":\"int64\"},\"limit\":{\"type\":\"integer\",\"format\":\"int64\"}}}}}")));
    }

}