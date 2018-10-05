package com.haz4j.swagger.test.app.map;


import com.googlecode.jsonrpc4j.JsonRpcError;
import com.googlecode.jsonrpc4j.JsonRpcErrors;
import com.googlecode.jsonrpc4j.JsonRpcParam;
import com.googlecode.jsonrpc4j.JsonRpcService;
import com.haz4j.swagger.annotation.Api;
import com.haz4j.swagger.annotation.ApiOperation;


@JsonRpcService("/v1/map")
@Api(tags = "maps", value = "Api maps")
public interface MapApi {

    @JsonRpcErrors({
            @JsonRpcError(exception = IllegalArgumentException.class, code = -187)
    })
    @ApiOperation(value = "Save entity")
    MapDto save(@JsonRpcParam(value = "map_dto") MapDto mapDto/*,
                @JsonRpcParam(value = "simple_map_dto") Map<String, String> simpleMap,
                @JsonRpcParam(value = "complex_map_dto") Map<String, List<String>> complexMap*/
    ) ;

}