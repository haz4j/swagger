package com.haz4j.test.app.generic;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Data
public class GenericDto <T, V, R> {

//    private String name;
//
//    private String value;

    private R entry1;

    private V entry2;

    private T entry3;

}