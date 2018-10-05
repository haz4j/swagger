package com.haz4j.test.app.collection;


import com.googlecode.jsonrpc4j.JsonRpcError;
import com.googlecode.jsonrpc4j.JsonRpcErrors;
import com.googlecode.jsonrpc4j.JsonRpcParam;
import com.googlecode.jsonrpc4j.JsonRpcService;
import com.haz4j.swagger.annotation.Api;
import com.haz4j.swagger.annotation.ApiOperation;


import java.util.Collection;
import java.util.List;


@JsonRpcService("/v1/collection")
@Api(tags = "collections", value = "Api collections")
public interface CollectionApi {

    @JsonRpcErrors({
            @JsonRpcError(exception = IllegalArgumentException.class, code = -187)
    })
    @ApiOperation(value = "Save entity")
    CollectionDto save(@JsonRpcParam(value = "collection_dto") CollectionDto collectionDto, @JsonRpcParam(value = "collection_dtos") Collection<CollectionDto> collectionDtos);

    List<CollectionDto> listAll();

}
