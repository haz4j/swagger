package com.haz4j.test.app.generic;

import lombok.Data;

@Data
public class GenericDto <T, V, R> {

    private String name;

    private String value;

    private R entry1;

    private V entry2;

    private T entry3;

}