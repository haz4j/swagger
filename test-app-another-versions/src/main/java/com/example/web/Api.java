package com.example.web;

import com.googlecode.jsonrpc4j.JsonRpcError;
import com.googlecode.jsonrpc4j.JsonRpcErrors;
import com.googlecode.jsonrpc4j.JsonRpcParam;
import com.googlecode.jsonrpc4j.JsonRpcService;
import io.swagger.annotations.ApiOperation;

@JsonRpcService("/v1/api")
@io.swagger.annotations.Api(tags = "api", value = "api")
public interface Api {

    @JsonRpcErrors({
            @JsonRpcError(exception = IllegalArgumentException.class, code = -187)
    })
    @ApiOperation(value = "Save entity")
    Integer save(@JsonRpcParam(value = "value") Integer value);

}