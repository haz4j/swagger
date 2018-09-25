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

    private Map<Integer, Map<String, Long>> moreComplexInternalMap;

    private Map<Integer, Map<String, Map<String, Long>>> moreAndMoreComplexInternalMap;

    private Map<Integer, List<Map<String, Map<Double, Map<Integer, List<Entry>>>>>> veryComplexInternalMap;

    //TODO: add raw map

    //TODO: add object map

}
