package com.haz4j.swagger.test.app.generic;

import com.googlecode.jsonrpc4j.JsonRpcParam;
import com.googlecode.jsonrpc4j.JsonRpcService;
import io.swagger.annotations.Api;

import java.util.List;
import java.util.Map;

@JsonRpcService("/v1/generic")
@Api(tags = "generics", value = "Api generics")
public interface GenericApi {

    //TODO: и сделать, чтобы параметры были не только в методе, а и в сущности и вспомнить какие были дженерики в другом проекте
    GenericDto save1(@JsonRpcParam(value = "generic_dto") GenericDto<Entry1, Entry2, Entry3> genericDto);

    List<GenericDto<Entry1, Entry2, Entry3>> save2(@JsonRpcParam(value = "generic_dto") List<GenericDto<Entry1, Entry2, Entry3>> genericDto);

    List<GenericDto<Entry3, Entry2, Entry1>> save3(
            @JsonRpcParam(value = "generic_dto") GenericDto<Entry1, Entry2, Entry3> genericDto,
            @JsonRpcParam(value = "generic_dtos") List<GenericDto<Entry3, Entry2, Entry1>> genericDtos
    );

    Map<String, GenericDto<Entry1, Entry2, Entry1>> save4(
            @JsonRpcParam(value = "generic_dto") Map<String, GenericDto<Entry1, Entry2, Entry1>> genericDto
    );

    List<Map<String, Map<String, List<List<Map<String, GenericDto<Entry1, Entry3, Entry2>>>>>>> save5(
            @JsonRpcParam(value = "generic_dto") List<Map<String, Map<String, List<List<Map<String, GenericDto<Entry1, Entry3, Entry2>>>>>>> genericDto
    );

    GenericDto<Entry1, Entry3, Entry2>[] save6(
            @JsonRpcParam(value = "generic_dto") GenericDto<Entry1, Entry3, Entry2>[] genericDto
    );

//    Entry5 save7(
//            @JsonRpcParam(value = "entry") Entry5 entry
//    );
}