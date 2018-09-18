package com.haz4j.test.app.annotation;


import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;
import org.springframework.stereotype.Service;

@Service
@AutoJsonRpcServiceImpl
public class AnnotationApiImpl implements AnnotationApi {

    @Override
    public AnnotationDto save(AnnotationDto annotationDto1/*, AnnotationDto annotationDto2*/) {
        return annotationDto1;
    }
}