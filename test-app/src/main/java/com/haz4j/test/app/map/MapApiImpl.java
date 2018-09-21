package com.haz4j.test.app.map;


import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@AutoJsonRpcServiceImpl
public class MapApiImpl implements MapApi {

    @Override
    public MapDto save(MapDto mapDto/*, Map<String, String> simpleMap, Map<String, List<String>> complexMap*/) {
        return mapDto;
    }
}