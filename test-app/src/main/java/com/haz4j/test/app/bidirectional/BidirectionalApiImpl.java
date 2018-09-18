package com.haz4j.test.app.bidirectional;


import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@AutoJsonRpcServiceImpl
public class BidirectionalApiImpl implements BidirectionalApi {

    @Override
    public FirstDto save(FirstDto firstDto, Collection<FirstDto> firstDtos) {
        return firstDto;
    }
}