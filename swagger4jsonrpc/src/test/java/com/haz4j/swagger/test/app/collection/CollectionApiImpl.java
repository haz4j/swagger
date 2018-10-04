package com.haz4j.swagger.test.app.collection;


import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Service
@AutoJsonRpcServiceImpl
public class CollectionApiImpl implements CollectionApi {

    @Override
    public CollectionDto save(CollectionDto collectionDto, Collection<CollectionDto> collectionDtos) {
        return collectionDto;
    }

    public List<CollectionDto> listAll(){
        CollectionDto dto = new CollectionDto();
        dto.setName("name");
        dto.setValue("value");
        return Arrays.asList(dto);
    }
}