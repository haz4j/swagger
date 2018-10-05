package com.haz4j.test.app.generic;

import com.googlecode.jsonrpc4j.JsonRpcParam;
import com.googlecode.jsonrpc4j.JsonRpcService;
import com.haz4j.swagger.annotation.Api;


import java.util.List;
import java.util.Map;

@JsonRpcService("/v1/generic")
@Api(tags = "generics", value = "Api generics")
public interface GenericApi {

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

    GenericDto severalGenerics(
            @JsonRpcParam(value = "generic_dto_1") GenericDto<Entry1, Entry2, Entry3> genericDto1,
            @JsonRpcParam(value = "generic_dto_2") GenericDto1<Entry3> genericDto2,
            @JsonRpcParam(value = "generic_dto_3") GenericDto2<Entry2, Entry1> genericDto3
    );

}