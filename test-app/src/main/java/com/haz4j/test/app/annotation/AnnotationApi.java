package com.haz4j.test.app.annotation;

import com.googlecode.jsonrpc4j.JsonRpcParam;
import com.googlecode.jsonrpc4j.JsonRpcService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@JsonRpcService("/v1/annotation")
@Api(tags = "annotation", value = "Api annotations")
public interface AnnotationApi {

    @ApiOperation(value = "Save entity")
    AnnotationDto save(
            @JsonRpcParam(value = "annotation_dto_from_annotation") AnnotationDto annotationDto1
            /*, AnnotationDto annotationDto2*/);

}