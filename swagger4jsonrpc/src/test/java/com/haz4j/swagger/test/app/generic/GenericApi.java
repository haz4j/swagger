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
    GenericDto generic(@JsonRpcParam(value = "generic_dto") GenericDto<Entry1, Entry2, Entry3> genericDto);

    List<GenericDto<Entry1, Entry2, Entry3>> listOfGenerics(@JsonRpcParam(value = "generic_dto") List<GenericDto<Entry1, Entry2, Entry3>> genericDto);

    List<GenericDto<Entry3, Entry2, Entry1>> genericAndList(
            @JsonRpcParam(value = "generic_dto") GenericDto<Entry1, Entry2, Entry3> genericDto,
            @JsonRpcParam(value = "generic_dtos") List<GenericDto<Entry3, Entry2, Entry1>> genericDtos
    );

    Map<String, GenericDto<Entry1, Entry2, Entry1>> mapOfGeneric(
            @JsonRpcParam(value = "generic_dto") Map<String, GenericDto<Entry1, Entry2, Entry1>> genericDto
    );

    List<Map<String, Map<String, List<List<Map<String, GenericDto<Entry1, Entry3, Entry2>>>>>>> listAndMapOfgeneric(
            @JsonRpcParam(value = "generic_dto") List<Map<String, Map<String, List<List<Map<String, GenericDto<Entry1, Entry3, Entry2>>>>>>> genericDto
    );

    GenericDto<Entry1, Entry3, Entry2>[] arrayOfGeneric(
            @JsonRpcParam(value = "generic_dto") GenericDto<Entry1, Entry3, Entry2>[] genericDto
    );

//    Entry5 save7(
//            @JsonRpcParam(value = "entry") Entry5 entry
//    );
}