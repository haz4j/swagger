package com.haz4j.swagger.test.app.bidirectional;


import com.googlecode.jsonrpc4j.JsonRpcError;
import com.googlecode.jsonrpc4j.JsonRpcErrors;
import com.googlecode.jsonrpc4j.JsonRpcParam;
import com.googlecode.jsonrpc4j.JsonRpcService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.Collection;


@JsonRpcService("/v1/bidirectional")
@Api(tags = "bidirectional", value = "Api bidirectional")
public interface BidirectionalApi {

    @JsonRpcErrors({
            @JsonRpcError(exception = IllegalArgumentException.class, code = -187)
    })
    @ApiOperation(value = "Save entity")
    FirstDto save(@JsonRpcParam(value = "first_dto") FirstDto firstDto, @JsonRpcParam(value = "first_dtos") Collection<FirstDto> firstDtos);

}