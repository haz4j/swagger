package com.haz4j.test.app.map;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class MapDto {

    private String name;

    private String value;

    private Map<Integer, Integer> simpleInternalMap;

    private Map<Integer, List<Integer>> complexInternalMap;

    //TODO: add raw map

}
