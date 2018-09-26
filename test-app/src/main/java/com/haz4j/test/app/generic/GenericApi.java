package com.haz4j.test.app.generic;

import com.googlecode.jsonrpc4j.JsonRpcError;
import com.googlecode.jsonrpc4j.JsonRpcErrors;
import com.googlecode.jsonrpc4j.JsonRpcParam;
import com.googlecode.jsonrpc4j.JsonRpcService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;
import java.util.Map;

@JsonRpcService("/v1/generic")
@Api(tags = "generics", value = "Api generics")
public interface GenericApi {

    //TODO: write test with complex generics
    //TODO: и сделать, чтобы параметры были не только в методе, а и в сущности и вспомнить какие были дженерики в другом проекте
    GenericDto save1(@JsonRpcParam(value = "generic_dto") GenericDto<Entry1, Entry2, Entry3> genericDto);

//    List<GenericDto<Entry>> save2(@JsonRpcParam(value = "generic_dto") List<GenericDto<Entry>> genericDto);
//
//    Map<String, GenericDto<Entry>> save3(@JsonRpcParam(value = "generic_dto") Map<String, GenericDto<Entry>> genericDto);
//
//    List<Map<String, Map<String, List<List<Map<String, GenericDto<Entry>>>>>>> save4(@JsonRpcParam(value = "generic_dto") List<Map<String, Map<String, List<List<Map<String, GenericDto<Entry>>>>>>> genericDto);
}