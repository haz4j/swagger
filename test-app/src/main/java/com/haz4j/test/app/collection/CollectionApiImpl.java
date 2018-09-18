package com.haz4j.test.app.collection;


import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@AutoJsonRpcServiceImpl
public class CollectionApiImpl implements CollectionApi {

    @Override
    public CollectionDto save(CollectionDto collectionDto, Collection<CollectionDto> collectionDtos) {
        return collectionDto;
    }
}