package com.haz4j.test.app.generic;


import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@AutoJsonRpcServiceImpl
public class GenericApiImpl implements GenericApi {

    @Override
    public GenericDto save1(GenericDto<Entry1, Entry2, Entry3> genericDto) {
        return genericDto;
    }

    @Override
    public List<GenericDto<Entry1, Entry2, Entry3>> save2(List<GenericDto<Entry1, Entry2, Entry3>> genericDto) {
        return genericDto;
    }

    @Override
    public List<GenericDto<Entry3, Entry2, Entry1>> save3(GenericDto<Entry1, Entry2, Entry3> genericDto, List<GenericDto<Entry3, Entry2, Entry1>> genericDtos) {
        return genericDtos;
    }

    @Override
    public Map<String, GenericDto<Entry1, Entry2, Entry1>> save4(Map<String, GenericDto<Entry1, Entry2, Entry1>> genericDto) {
        return genericDto;
    }

    @Override
    public List<Map<String, Map<String, List<List<Map<String, GenericDto<Entry1, Entry3, Entry2>>>>>>> save5(List<Map<String, Map<String, List<List<Map<String, GenericDto<Entry1, Entry3, Entry2>>>>>>> genericDto) {
        return genericDto;
    }

    //TODO: добавить
    /*
    @Override
    public GenericDto save1(GenericDto1<Entry1, Entry2, Entry3> genericDto1,

    GenericDto2<Entry3> genericDto2,

    GenericDto3<Entry2, Entry1> genericDto3

    ) {
        return genericDto;
    }
    //TODO: добавить вложенные gererics

     */



}