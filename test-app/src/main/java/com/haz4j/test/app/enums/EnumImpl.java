package com.haz4j.test.app.enums;


import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;
import org.springframework.stereotype.Service;

@Service
@AutoJsonRpcServiceImpl
public class EnumImpl implements EnumApi {

    @Override
    public EnumDto save(EnumDto enumDto) {
        return enumDto;
    }
}