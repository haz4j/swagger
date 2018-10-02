package com.haz4j.swagger.test.app.array;


import com.googlecode.jsonrpc4j.JsonRpcError;
import com.googlecode.jsonrpc4j.JsonRpcErrors;
import com.googlecode.jsonrpc4j.JsonRpcParam;
import com.googlecode.jsonrpc4j.JsonRpcService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@JsonRpcService("/v1/array")
@Api(tags = "arrays", value = "Api arrays")
public interface ArrayApi {

    @JsonRpcErrors({
            @JsonRpcError(exception = IllegalArgumentException.class, code = -187)
    })
    @ApiOperation(value = "Save entity")
    ArrayDto save(@JsonRpcParam(value = "array_dto") ArrayDto arrayDto, @JsonRpcParam(value = "array_dtos") ArrayDto[] arrayDtos);

}