package com.haz4j.swagger.test.app.generic;


import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@AutoJsonRpcServiceImpl
public class GenericApiImpl implements GenericApi {

    @Override
    public GenericDto generic(GenericDto<Entry1, Entry2, Entry3> genericDto) {
        return genericDto;
    }

    @Override
    public List<GenericDto<Entry1, Entry2, Entry3>> listOfGenerics(List<GenericDto<Entry1, Entry2, Entry3>> genericDto) {
        return genericDto;
    }

    @Override
    public List<GenericDto<Entry3, Entry2, Entry1>> genericAndList(GenericDto<Entry1, Entry2, Entry3> genericDto, List<GenericDto<Entry3, Entry2, Entry1>> genericDtos) {
        return genericDtos;
    }

    @Override
    public Map<String, GenericDto<Entry1, Entry2, Entry1>> mapOfGeneric(Map<String, GenericDto<Entry1, Entry2, Entry1>> genericDto) {
        return genericDto;
    }

    @Override
    public List<Map<String, Map<String, List<List<Map<String, GenericDto<Entry1, Entry3, Entry2>>>>>>> listAndMapOfgeneric(List<Map<String, Map<String, List<List<Map<String, GenericDto<Entry1, Entry3, Entry2>>>>>>> genericDto) {
        return genericDto;
    }

    @Override
    public GenericDto<Entry1, Entry3, Entry2>[] arrayOfGeneric(GenericDto<Entry1, Entry3, Entry2>[] genericDto) {
        return genericDto;
    }

    @Override
    public GenericDto severalGenerics(
            GenericDto<Entry1, Entry2, Entry3> genericDto1,
            GenericDto1<Entry3> genericDto2,
            GenericDto2<Entry2, Entry1> genericDto3
    ) {
        return genericDto1;
    }
}