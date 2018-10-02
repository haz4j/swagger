package com.haz4j.swagger.test.app.bidirectional;

import lombok.Data;

import java.util.List;

@Data
public class SecondDto {

    private String name;

    private String value;

    private FirstDto firstDto;

    private List<FirstDto> firstDtos;

}
