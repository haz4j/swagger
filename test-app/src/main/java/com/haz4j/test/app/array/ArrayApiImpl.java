package com.haz4j.test.app.array;


import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;
import org.springframework.stereotype.Service;

@Service
@AutoJsonRpcServiceImpl
public class ArrayApiImpl implements ArrayApi {

    @Override
    public ArrayDto save(ArrayDto arrayDto, ArrayDto arrayDtos[]) {
        return arrayDto;
    }
}