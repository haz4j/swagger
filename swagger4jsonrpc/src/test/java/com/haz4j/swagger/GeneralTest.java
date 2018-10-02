package com.haz4j.swagger;

import com.haz4j.swagger.array.ArrayApi;
import com.haz4j.swagger.structure.ApiStruct;
import org.junit.Test;

import java.util.Arrays;

public class GeneralTest {

    private JsonGenerator generator = new JsonGenerator();

    @Test
    public void simpleTest(){
        ApiStruct apiStruct = ApiMapper.toStruct(Arrays.asList(ArrayApi.class))
                .filter("save");

        String json = generator.createJson("host_placeholder", apiStruct);
        System.out.println();

    }

}
