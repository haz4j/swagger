package com.example.web;

import com.googlecode.jsonrpc4j.JsonRpcParam;
import com.googlecode.jsonrpc4j.JsonRpcService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@JsonRpcService("/v1/api")
@Api(tags = "api", value = "api")
public interface ApiInterface {

    @ApiOperation(value = "Save entity")
    Integer save(@JsonRpcParam(value = "value") Integer value);

}