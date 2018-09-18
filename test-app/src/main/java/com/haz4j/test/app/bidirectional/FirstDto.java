package com.haz4j.test.app.bidirectional;

import lombok.Data;

import java.util.List;

@Data
public class FirstDto {

    private String name;

    private String value;

    private SecondDto secondDto;

    private List<SecondDto> secondDtos;

}
