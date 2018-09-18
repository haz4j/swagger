package com.haz4j.test.app.collection;


import com.googlecode.jsonrpc4j.JsonRpcError;
import com.googlecode.jsonrpc4j.JsonRpcErrors;
import com.googlecode.jsonrpc4j.JsonRpcParam;
import com.googlecode.jsonrpc4j.JsonRpcService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.Collection;


@JsonRpcService("/v1/collection")
@Api(tags = "collections", value = "Api collections")
public interface CollectionApi {

    @JsonRpcErrors({
            @JsonRpcError(exception = IllegalArgumentException.class, code = -187)
    })
    @ApiOperation(value = "Save entity")
    CollectionDto save(@JsonRpcParam(value = "array_dto") CollectionDto collectionDto, @JsonRpcParam(value = "array_dtos") Collection<CollectionDto> collectionDtos);

}